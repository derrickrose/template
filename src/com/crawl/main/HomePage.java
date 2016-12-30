package com.crawl.main;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HomePage {
   private Document document;

   public HomePage(Document document) {
      super();
      this.document = document;
   }

   public ArrayList<String> getAllCategorys() {
      ArrayList<String> urls = new ArrayList<String>();
      Elements lists = document.select("dd.nav-categories>ul>li>h2>a[href*=miniinthebox]");
      for (Element url : lists) {
         urls.add(url.attr("href"));
      }
      return urls;

   }

}
