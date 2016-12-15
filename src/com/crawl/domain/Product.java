package com.crawl.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

   @Id
   private String productId; // Identifiant formation (Cle primaire)
   private String parent_id;

   private String name;
   private String link;
   private String image;
   private String description;
   private String motclef;
   private float price;
   private float shippingCost;
   private int shippingDelay;
   private String brand;
   private String category;
   private int quantity;
   private String color_name;
   private String size_name;

   public String getProductId() {
      return productId;
   }

   public String getParent_id() {
      return parent_id;
   }

   public String getName() {
      return name;
   }

   public String getLink() {
      return link;
   }

   public String getImage() {
      return image;
   }

   public String getDescription() {
      return description;
   }

   public String getMotclef() {
      return motclef;
   }

   public float getPrice() {
      return price;
   }

   public float getShippingCost() {
      return shippingCost;
   }

   public int getShippingDelay() {
      return shippingDelay;
   }

   public String getBrand() {
      return brand;
   }

   public String getCategory() {
      return category;
   }

   public int getQuantity() {
      return quantity;
   }

   public String getColor_name() {
      return color_name;
   }

   public String getSize_name() {
      return size_name;
   }

   public void setProductId(String productId) {
      this.productId = productId;
   }

   public void setParent_id(String parent_id) {
      this.parent_id = parent_id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setLink(String link) {
      this.link = link;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setMotclef(String motclef) {
      this.motclef = motclef;
   }

   public void setPrice(float price) {
      this.price = price;
   }

   public void setShippingCost(float shippingCost) {
      this.shippingCost = shippingCost;
   }

   public void setShippingDelay(int shippingDelay) {
      this.shippingDelay = shippingDelay;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public void setColor_name(String color_name) {
      this.color_name = color_name;
   }

   public void setSize_name(String size_name) {
      this.size_name = size_name;
   }

   // constructor
   /*
    * public Product(String productId, String parent_id, String name, String link, String image, String description, String motclef, float price, float shippingCost, int
    * shippingDelay, String brand, String category, int quantity, String color_name, String size_name) { super(); this.productId = productId; this.parent_id = parent_id;
    * this.name = name; this.link = link; this.image = image; this.description = description; this.motclef = motclef; this.price = price; this.shippingCost =
    * shippingCost; this.shippingDelay = shippingDelay; this.brand = brand; this.category = category; this.quantity = quantity; this.color_name = color_name;
    * this.size_name = size_name; }
    */

}
