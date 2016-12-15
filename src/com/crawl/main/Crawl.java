package com.crawl.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.crawl.domain.Product;

public class Crawl {
   Product product;

   public Crawl(Product product) {
      super();
      this.product = product;
   }

   public void saveProduct() {
      // 1 : Ouverture unite de travail JPA
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("products-crawl");
      EntityManager em = emf.createEntityManager();

      // 2 : Ouverture transaction
      EntityTransaction tx = em.getTransaction();
      // 3 : Instanciation Objet metier
      try {
         tx.begin();
         em.persist(product);
         tx.commit();
      } catch (Exception e) {
         if (tx != null) {
            tx.rollback();
         }
         System.out.println("EXCEPTION -- > " + e.getMessage());
      } finally {

         if (em != null) {
            em.close();
            emf.close();
         }
      }
   }
   //
   // public static void main(String[] args) {
   //
   // // 1 : Ouverture unite de travail JPA
   // EntityManagerFactory emf = Persistence.createEntityManagerFactory("products-crawl");
   // EntityManager em = emf.createEntityManager();
   //
   // // 2 : Ouverture transaction
   // EntityTransaction tx = em.getTransaction();
   // // 3 : Instanciation Objet metier
   // Product product = new Product("productId", "parent_id", "name", "link", "image", "description", "motclef", 10.4f, 2.5f, 2, "brand", "category", 0, "color_name",
   // "size_name");
   // try {
   // tx.begin();
   // em.persist(product);
   // tx.commit();
   // } catch (Exception e) {
   // if (tx != null) {
   // tx.rollback();
   // }
   // System.out.println("EXCEPTION -- > " + e.getMessage());
   // e.printStackTrace();
   // } finally {
   //
   // if (em != null) {
   // em.close();
   // emf.close();
   // }
   // }
   // }

}
