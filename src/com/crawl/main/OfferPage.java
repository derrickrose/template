package com.crawl.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
      String strCategory = getCategory(document);
      System.out.println("Category :" + strCategory);
      p.setCategory(strCategory);
      p.setColor_name("");
      p.setSize_name("");
      p.setMotclef("");
      String strDescription = getDescription(document);
      System.out.println("Description :" + strDescription);
      p.setDescription(strDescription);
      p.setQuantity(0);
      p.setParent_id("");
      String productId = getProductId(document);
      System.out.println("Product Id :" + productId);
      p.setProductId(productId);
      // Treat Variante
      treatVarianteColor(p);

      new Crawl(p).saveProduct();
   }

   private void treatVarianteColor(Product product) {// treat color variante
      Elements colorlist = document.select("div.attributes-value-show:has(h5:contains(Couleur))>span");
      if (colorlist.size() > 0) {
         for (Element offerVariante : colorlist) {
            Product variantProduct = cloneProduct(product);
            String colorName = fromAttribute(offerVariante, "data-color");
            if (StringUtils.isBlank(colorName)) {
               colorName = fromElementText(offerVariante);
            }
            System.out.println("Variante color Name:" + colorName);
            String selecteurImage = fromAttribute(offerVariante, "image-id");// img variante:li#img_29582469>a>img
            selecteurImage = "li#img_" + selecteurImage + ">a>img";
            String strImage = document.select(selecteurImage).attr("data-normal");
            if (StringUtils.isBlank(strImage)) strImage = product.getImage();
            System.out.println("Variante color Image:" + strImage);
            String variantId = fromAttribute(offerVariante, "data-id");
            variantId = variantId.replace("attr_", "").trim();
            System.out.println("Variante color Id:" + variantId);
            variantProduct.setName(product.getName() + " " + colorName);
            variantProduct.setParent_id(product.getProductId());
            variantProduct.setProductId(variantId);
            variantProduct.setImage(strImage);
            variantProduct.setColor_name(colorName);
            // treat variante size
            treatVarianteSize(variantProduct);
            new Crawl(variantProduct).saveProduct();

         }
      } else {
         // for variante color empty
         treatVarianteSize(product);
      }
   }

   private void treatVarianteSize(Product product) {// treat size variante
      Elements sizelist = document.select("select#attr_4240987_39>option:not(:contains(Taille))");
      if (sizelist.size() > 0) {
         for (Element offerVariante : sizelist) {
            Product variantProduct = cloneProduct(product);
            String size_name = fromElementText(offerVariante);
            System.out.println("Variante Size Name:" + size_name);
            String variantId = fromAttribute(offerVariante, "value");
            variantId = product.getProductId() + "_" + variantId;
            System.out.println("Variante Size Id:" + variantId);
            variantProduct.setName(product.getName() + " " + size_name);
            variantProduct.setParent_id(product.getProductId());
            variantProduct.setProductId(variantId);
            variantProduct.setSize_name(size_name);
            new Crawl(variantProduct).saveProduct();

         }
      }
   }

   private String getCategory(Document doc) {
      String strCategory = "";

      Elements breadcrumb = doc.select("ul.breadcrumb-new>li:not([class=home])");
      for (Element bc : breadcrumb) {
         Element parent = findElement(bc, "dl>dt>a");
         if (parent != null) {
            strCategory += fromElementText(parent) + ">";
         } else {
            strCategory += fromElementText(bc);
         }
      }

      return strCategory;
   }

   private Product cloneProduct(Product product) {
      Product prod = new Product();
      prod.setBrand(product.getBrand());
      prod.setCategory(product.getCategory());
      prod.setColor_name(product.getColor_name());
      prod.setDescription(product.getDescription());
      prod.setImage(product.getImage());
      prod.setLink(product.getLink());
      prod.setMotclef(product.getMotclef());
      prod.setName(product.getName());
      prod.setParent_id(product.getParent_id());
      prod.setPrice(product.getPrice());
      prod.setProductId(product.getProductId());
      prod.setQuantity(product.getQuantity());
      prod.setShippingCost(product.getShippingCost());
      prod.setShippingDelay(product.getShippingDelay());
      prod.setSize_name(product.getSize_name());
      return prod;
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

   private String getBreadcrumb(final Element element) {
      final Element titleElement = findElement(element, "ul.breadcrumb-new>li");
      String title = fromElementText(titleElement);
      return title;
   }

   private String getDescription(final Element element) {
      final Element titleElement = findElement(element, "meta[name=description]");
      String title = fromAttribute(titleElement, "content");
      Pattern pPrice = Pattern.compile("(.?\\d+\\.\\d+)\\s(.*)");
      Matcher m = pPrice.matcher(title);
      if (m.find()) {
         return m.group(2);
      }
      return title;
   }

   private String getProductId(final Element element) {
      final Element skuElement = findElement(element, "input[name=products_id]");
      String productId = fromAttribute(skuElement, "value");
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
