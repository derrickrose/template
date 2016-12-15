package com.crawl.action;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;

import com.crawl.connexion.Connexion;
import com.crawl.main.TreatPage;

public class DoAction {

   public static void main(String[] args) throws ClientProtocolException, IOException {
      Document document = new Connexion().getPage("http://www.miniinthebox.com/fr/accessorios-pour-iphone_c4861");
      new TreatPage(document);
   }

}
