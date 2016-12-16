package com.crawl.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;

import com.crawl.connexion.Connexion;
import com.crawl.main.TreatPage;

public class DoAction {
   private String url;

   public DoAction(String url) {
      super();
      this.url = url;
   }

   public void startCrawl() throws ClientProtocolException, IOException, InterruptedException {
      Document document = new Connexion().getPage(url);
      new TreatPage(document);
   }

}
