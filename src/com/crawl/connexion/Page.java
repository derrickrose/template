package com.crawl.connexion;

import org.jsoup.nodes.Document;

public class Page {
   private int status;
   private Document document;

   public int getStatus() {
      return status;
   }

   public Document getDocument() {
      return document;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public void setDocument(Document document) {
      this.document = document;
   }

}
