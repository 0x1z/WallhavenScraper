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
        Long n = 2L;
        for (Long i = 1L; i <= n; i++) {
//            System.out.println("https://wallhaven.cc/toplist?page=" + i);
            Document html1 = Jsoup.connect("https://wallhaven.cc/toplist?page=" + i).userAgent("Mozilla").get();


            Elements eles = html1.select("a[href]");
            for (Element link : eles) {

                String filStr = link.attr("href");
                if (filStr.contains("/w/")) {
                    Document html2 = Jsoup.connect(filStr).userAgent("Mozilla").get();
                    Elements elesinner = html2.getElementsByTag("img");
                    for (Element linkinner : elesinner) {
                        String finalLink = linkinner.attr("src");
                        if (!finalLink.contains("logo") && !finalLink.contains("avatar")) {
                            System.out.println(finalLink);
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
        }
    }
}