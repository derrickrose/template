package com.crawl.main;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CategoryPage {

   private Document document;

   public CategoryPage(Document document) {
      super();
      this.document = document;
   }

   public ArrayList<String> getAllListing() {
      ArrayList<String> urls = new ArrayList<String>();
      Elements lists = document.select("div.container-flash-banner+div a");
      for (Element url : lists) {
         urls.add(url.attr("href"));
      }
      return urls;

   }

}
