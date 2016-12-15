package com.crawl.main;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.crawl.domain.Product;

public class OfferPage {
   private Document document;

   public OfferPage(Document document) {
      super();
      this.document = document;
   }

   public void getInformation() {
      Product p = new Product();
      String strImage = getImagePath(document);
      System.out.println("Image :" + strImage);
      p.setImage(strImage);
      String title = getTitle(document);
      System.out.println("Title :" + title);
      p.setName(title);
      String strProductPath = getProductPath(document);
      System.out.println("Product Path :" + strProductPath);
      p.setLink(strProductPath);
      float price = getPrice(document);
      System.out.println("Price :" + price);
      p.setPrice(price);
      p.setShippingDelay(4);
      p.setShippingCost(0);
      p.setBrand("");
      p.setCategory("");
      p.setColor_name("");
      p.setSize_name("");
      p.setMotclef("");
      String strDescription = getDescription(document);
      System.out.println("Description :" + strDescription);
      p.setDescription("");
      p.setQuantity(0);
      p.setParent_id("");
      String productId = getProductId(document);
      System.out.println("Product Id :" + productId);
      p.setProductId(productId);
      new Crawl(p).saveProduct();
   }

   private float getPrice(Element element) {
      final Element priceElement = findElement(element, "strong[itemprop=price]");
      String strPrice = fromElementText(priceElement);
      if (StringUtils.isNotBlank(strPrice)) {
         strPrice = cleanPrice(strPrice);
         if (NumberUtils.isNumber(strPrice)) {
            return Float.parseFloat(strPrice);
         }
      }
      return -1f;

   }

   private String cleanPrice(final String strPrice) {
      return strPrice.replaceAll("[^\\d.]", "");
   }

   private String getImagePath(final Element element) {
      final Element imageElement = findElement(element, "div.magnify>a>img");
      String imagePath = fromAbsoluteUrl(imageElement, "src");
      return imagePath;
   }

   private String getProductPath(final Element element) {
      final Element pathElement = findElement(element, "link[rel=canonical]");
      String productPath = fromAbsoluteUrl(pathElement, "href");
      return productPath;
   }

   private String getTitle(final Element element) {
      final Element titleElement = findElement(element, "div.prod-info-title>h1");
      String title = fromElementText(titleElement);
      return title;
   }

   private String getDescription(final Element element) {
      final Element titleElement = findElement(element, "meta[name=description]");
      String title = fromElementText(titleElement);
      return title;
   }

   private String getProductId(final Element element) {
      final Element skuElement = findElement(element, "span.item-id");
      String productId = fromElementText(skuElement);
      if (StringUtils.isNotBlank(productId)) {
         productId = productId.replace("#", "");
      }
      return productId;
   }

   private Element findElement(final Element element, final String cssSelector) {
      return element.select(cssSelector).first();
   }

   private String fromElementText(final Element element) {
      if (element != null) {
         String text = element.text();
         text = StringEscapeUtils.unescapeHtml4(text);
         return StringUtils.trim(text);
      }
      return null;
   }

   private String fromAbsoluteUrl(final Element element, final String attr) {
      if (element != null) {
         String text = element.absUrl(attr);
         return StringUtils.trim(text);
      }
      return null;
   }

   private String fromAttribute(final Element element, final String attr) {
      if (element != null) {
         String text = element.attr(attr);
         return StringUtils.trim(text);
      }
      return null;
   }

}
