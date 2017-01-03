package com.crawl.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

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
         if (tx != null && tx.isActive()) {
            tx.rollback();
         }
      } finally {

         if (em != null) {
            em.close();
            emf.close();
         }
      }
   }

   public List<Product> getProduct(String url) {
      // 1 : Ouverture unite de travail JPA
      String urltemp = (StringUtils.contains(url, "?")) ? StringUtils.substringBeforeLast(url, "?") : url;
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("products-crawl");
      EntityManager em = emf.createEntityManager();
      Query query = em.createQuery("SELECT produit From Product produit  WHERE produit.link=:url");
      List<Product> products = query.setParameter("url", urltemp).getResultList();
      em.close();
      emf.close();
      return products;
   }

   /*
    * public static void main(String[] arg0) { List<Product> lists = new
    * Crawl(null).getProduct("http://www.miniinthebox.com/fr/v1-4-hdmi-displayport-m-m-noir-plaque-or-1-8-m_p1000014.html"); for (Product p : lists) {
    * System.out.println("OK :" + p.getName()); } }
    */

}
