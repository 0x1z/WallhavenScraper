import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.io.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Document html1 = Jsoup.connect("https://wallhaven.cc/toplist").userAgent("Mozilla").get();
//        Document html1=Jsoup.connect("https://wallhaven.cc/w/7p27k3").get();
//        String Title = html1.title();
//        System.out.println(Title);
        //links
        Elements eles = html1.select("a[href]");
        for (Element link : eles) {
//            System.out.println(link.attr("href"));
//            System.out.println(link.text());
            String filStr = link.attr("href");
            if (filStr.contains("/w/")) {
//                System.out.println(filStr);
                Document html2 = Jsoup.connect(filStr).userAgent("Mozilla").get();
                Elements elesinner = html2.getElementsByTag("img");
                for (Element linkinner : elesinner) {
                  String finalLink = linkinner.attr("src");

                    if (!finalLink.contains("logo") && !finalLink.contains("avatar")) {
                        System.out.println(finalLink);
//                    System.out.println(linkinner.attr("src"));
//                    System.out.println(linkinner.text());
                    String dlUrl = finalLink;
                    Long Baseid = (long) (dlUrl.indexOf("wallhaven-"));
                    Long Startid = Baseid;
                    Long EndId = Baseid + 20;
                    String dlName = dlUrl.substring(Math.toIntExact(Startid), Math.toIntExact(EndId));
                    URL website = new URL(dlUrl);
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream(dlName);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                        Thread.sleep(1000);
                    }
                }

            }
        }

       /* Elements eles=html1.getElementsByTag("img");
        for(Element link:eles){
            System.out.println(link.attr("src"));
            System.out.println(link.text());

        }*/

//       heart of downloader
       /* String dlUrl = "https://w.wallhaven.cc/full/7p/wallhaven-7p39gy.png";
        Long Baseid= (long) (dlUrl.indexOf("wallhaven-"));
        Long Startid= Baseid;
        Long EndId= Baseid+20;
        String dlName=dlUrl.substring(Math.toIntExact(Startid), Math.toIntExact(EndId));
        URL website = new URL(dlUrl);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(dlName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
*/
    }
}