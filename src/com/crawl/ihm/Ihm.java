package com.crawl.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.crawl.action.DoAction;

public class Ihm {

   public static void main(String[] args) {
      new Ihm();

   }

   public Ihm() {
      super();
      JFrame fenetre = new JFrame();
      fenetre.setTitle("Crawl Action");
      fenetre.setSize(600, 100);
      fenetre.setLocationRelativeTo(null);
      fenetre.setResizable(false);
      fenetre.setUndecorated(false);
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel form = new JPanel();
      JLabel linklabel = new JLabel("LINK TO CRAWL:");
      JTextField urlField = new JTextField(40);
      form.add(linklabel);
      form.add(urlField);
      JPanel control = new JPanel();
      JButton crawlBtn = new JButton("Crawl");
      crawlBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String urltocrawl = urlField.getText();
            if (StringUtils.isNotBlank(urltocrawl)) {
               try {
                  new DoAction(urltocrawl).startCrawl();
               } catch (IOException e1) {
                  JOptionPane.showMessageDialog(null, "Error on the crawl");
               } catch (InterruptedException e1) {
                  JOptionPane.showMessageDialog(null, "Thread error");
               }
            } else {
               JOptionPane.showMessageDialog(null, "Please enter url to crawl");
            }

         }
      });
      JButton cancelBtn = new JButton("Cancel");
      cancelBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);

         }
      });
      control.add(crawlBtn);
      control.add(cancelBtn);

      fenetre.getContentPane().add(form, BorderLayout.NORTH);
      fenetre.getContentPane().add(control, BorderLayout.SOUTH);

      fenetre.setVisible(true);

   }

}
