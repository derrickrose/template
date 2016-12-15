package com.crawl.connexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Connexion {

   public Document getPage(String url) throws ClientProtocolException, IOException {
      DefaultHttpClient httpclient = new DefaultHttpClient();
      HttpGet httpGet = new HttpGet(url);
      httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      httpGet.setHeader("Host", "www.miniinthebox.com");
      httpGet.setHeader("Referer", "http://www.miniinthebox.com/fr/");
      httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0");
      httpGet.setHeader("Accept-Encoding", "gzip, deflate");
      HttpContext context = context(httpclient);
      HttpResponse response = httpclient.execute(httpGet, context);
      HttpEntity entity = response.getEntity();
      int status = response.getStatusLine().getStatusCode();
      System.out.println("Connection status : " + status);
      if (status == 200) {
         return Jsoup.parse(getContent(response, entity));
      }

      return null;
   }

   private String getContent(HttpResponse response, HttpEntity entity) throws IllegalStateException, IOException {
      Header contentEncodingHeader = response.getFirstHeader("Content-Encoding");
      if (contentEncodingHeader != null) {
         String encoding = contentEncodingHeader.getValue();
         if (StringUtils.isNotBlank(encoding) && encoding.contains("gzip")) return convertInputStreamToString(new GzipDecompressingEntity(entity).getContent());
      }
      return convertInputStreamToString(entity.getContent());
   }

   public String convertInputStreamToString(InputStream data) throws IOException {
      if (data == null) return null;
      StringWriter writer = new StringWriter();
      IOUtils.copy(data, writer, "utf-8");
      String response = writer.toString();
      return response;
   }

   private HttpContext context(DefaultHttpClient httpClient) {
      CookieStore cookieStore = httpClient.getCookieStore();
      HttpContext localContext = new BasicHttpContext();
      localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
      return localContext;
   }

}
