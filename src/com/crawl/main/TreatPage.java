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
   private ArrayList<String> urlCategory;

   public ArrayList<String> getUrlCategory() {
      return urlCategory;
   }

   public void setUrlCategory(ArrayList<String> urlCategory) {
      this.urlCategory = urlCategory;
   }

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
      if (isHomePage()) {
         System.out.println("=========> HOME PAGE <===============");
         setUrlCategory(new HomePage(document).getAllCategorys());
         for (String urls : getUrlCategory()) {
            System.out.println("Category to crawl :" + urls);
            Document docCategory = connect.getPage(urls);
            System.out.println("===========>Category treatment<=====================");
            setUrlListing(new CategoryPage(docCategory).getAllListing());
            for (String url : getUrlListing()) {
               System.out.println("===========>Listing Treatment<=====================");
               boolean continueCrawl = true;
               int indexPage = 0;
               while (continueCrawl) {
                  System.out.println("=========================> Page n°" + indexPage);
                  Document docListing = connect.getPage(url);
                  if (docListing == null) break;
                  ArrayList<String> offrelists = new ListingPage(docListing).getAllOfferUrl();
                  for (String offerLink : offrelists) {
                     List<Product> p = new Crawl(null).getProduct(offerLink);
                     if (p.size() > 0) {
                        System.out.println("========> DUPLICATE PRODUCT<==============");
                        continue;
                     } else {
                        System.out.println("Product is null:" + offerLink);
                     }
                     Document offerPageDocument = connect.getPage(offerLink);
                     if (offerPageDocument == null) continue;
                     new OfferPage(offerPageDocument).getInformation();
                  }
                  indexPage++;
                  // Thread.sleep(5000);
                  continueCrawl = hasNextPage(docListing);
                  url = getNextPageUrl(docListing);
               }

            }
         }

      } else if (isCategoryPage()) {// treat category page //dd.nav-categories>ul>li>h2>a[href*=miniinthebox]
         System.out.println("===========>Category treatment<=====================");
         setUrlListing(new CategoryPage(document).getAllListing());
         for (String url : getUrlListing()) {
            System.out.println("===========>Listing Treatment<=====================");
            boolean continueCrawl = true;
            int indexPage = 0;
            while (continueCrawl) {
               System.out.println("=========================> Page n°" + indexPage);
               Document docListing = connect.getPage(url);
               if (docListing == null) break;
               ArrayList<String> offrelists = new ListingPage(docListing).getAllOfferUrl();
               for (String offerLink : offrelists) {
                  List<Product> p = new Crawl(null).getProduct(offerLink);
                  if (p.size() > 0) {
                     System.out.println("========> DUPLICATE PRODUCT<==============");
                     continue;
                  } else {
                     System.out.println("Product is null:" + offerLink);
                  }
                  Document offerPageDocument = connect.getPage(offerLink);
                  if (offerPageDocument == null) continue;
                  new OfferPage(offerPageDocument).getInformation();
               }
               indexPage++;
               // Thread.sleep(5000);
               continueCrawl = hasNextPage(docListing);
               url = getNextPageUrl(docListing);
            }

         }
      } else if (isListingPage()) {// treat listing page
         String url = "";
         System.out.println("===========>Listing Treatment<=====================");
         boolean continueCrawl = true;
         int indexPage = 0;
         while (continueCrawl) {
            System.out.println("=========================> Page n°" + indexPage);
            Document docListing = (indexPage == 0) ? document : connect.getPage(url);
            if (docListing == null) break;
            ArrayList<String> offrelists = new ListingPage(docListing).getAllOfferUrl();
            for (String offerLink : offrelists) {
               List<Product> p = new Crawl(null).getProduct(offerLink);
               if (p.size() > 0) {
                  System.out.println("========> DUPLICATE PRODUCT<==============");
                  continue;
               } else {
                  System.out.println("Product is null:" + offerLink);
               }
               Document offerPageDocument = connect.getPage(offerLink);
               if (offerPageDocument == null) continue;
               new OfferPage(offerPageDocument).getInformation();
            }
            indexPage++;
            // Thread.sleep(5000);
            continueCrawl = hasNextPage(docListing);
            url = getNextPageUrl(docListing);
         }
      } else if (isOfferPage()) {// treat offer page
         new OfferPage(document).getInformation();
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

   private boolean isHomePage() {
      Element categoryPage = document.select("dl.nav-shop-all>dt>a:not(:has(i))").first();
      return categoryPage != null;
   }

}
