package com.crawl.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawl.connexion.Connexion;
import com.crawl.domain.Product;

public class TreatPage {

   private Document document;
   private ArrayList<String> urlListing;
   private ArrayList<String> urlOfferPage;

   public ArrayList<String> getUrlListing() {
      return urlListing;
   }

   public void setUrlListing(ArrayList<String> urlListing) {
      this.urlListing = urlListing;
   }

   public TreatPage(Document document) throws ClientProtocolException, IOException, InterruptedException {
      super();
      this.document = document;
      Connexion connect = new Connexion();
      if (isCategoryPage()) {
         System.out.println("===========>Category treatment<=====================");
         setUrlListing(new CategoryPage(document).getAllListing());
         for (String url : getUrlListing()) {
            System.out.println("===========>Listing Treatment<=====================");
            boolean continueCrawl = true;
            int indexPage = 0;
            while (continueCrawl) {
               System.out.println("=========================> Page n°" + indexPage);
               Document docListing = connect.getPage(url);
               ArrayList<String> offrelists = new ListingPage(docListing).getAllOfferUrl();
               for (String offerLink : offrelists) {
                  Document offerPageDocument = connect.getPage(offerLink);
                  new OfferPage(offerPageDocument).getInformation();
               }
               indexPage++;
               Thread.sleep(5000);
               continueCrawl = hasNextPage(docListing);
               url = getNextPageUrl(docListing);
            }

         }
      }
      if (isListingPage()) {
         String url = document.baseUri();
         System.out.println("Url listing : " + url);
         System.out.println("===========>Listing Treatment<=====================");
         boolean continueCrawl = true;
         int indexPage = 0;
         while (continueCrawl) {
            System.out.println("=========================> Page n°" + indexPage);
            Document docListing = (indexPage == 0) ? document : connect.getPage(url);
            ArrayList<String> offrelists = new ListingPage(docListing).getAllOfferUrl();
            for (String offerLink : offrelists) {
               Document offerPageDocument = connect.getPage(offerLink);
               new OfferPage(offerPageDocument).getInformation();
            }
            indexPage++;
            Thread.sleep(5000);
            continueCrawl = hasNextPage(docListing);
            url = getNextPageUrl(docListing);
         }
      }
   }

   public List<Product> getProducts(Elements productList) {
      List<Product> p = new ArrayList<Product>();
      return p;
   }

   private boolean hasNextPage(Document docListing) {
      Element categoryPage = docListing.select("li.next>a").first();
      return categoryPage != null;
   }

   private String getNextPageUrl(Document docListing) {
      Element nextPage = docListing.select("li.next>a").first();
      if (nextPage != null) {
         return nextPage.attr("href");
      }
      return null;
   }

   private boolean isCategoryPage() {
      Element categoryPage = document.select("div.flash-banner.prm").first();
      return categoryPage != null;
   }

   private boolean isListingPage() {
      Element categoryPage = document.select("dl.item-block>dt>a").first();
      return categoryPage != null;
   }

   private boolean isOfferPage() {
      Element categoryPage = document.select("body.product-info-new").first();
      return categoryPage != null;
   }

}
