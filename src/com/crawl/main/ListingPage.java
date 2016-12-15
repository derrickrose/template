package com.crawl.main;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListingPage {
   private Document document;

   public ListingPage(Document document) {
      super();
      this.document = document;
   }

   public ArrayList<String> getAllOfferUrl() {
      ArrayList<String> urls = new ArrayList<String>();
      Elements lists = document.select("dl.item-block>dt>a");
      for (Element url : lists) {
         System.out.println("Offer Url:" + url.attr("href"));
         urls.add(url.attr("href"));
      }
      return urls;

   }

}
