package com.julius745.connect.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Connector {

    public static HttpsURLConnection connect(String urlAddress){
      try
      {
          URL url = new URL(urlAddress);
          HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

          con.setRequestMethod("POST");
          con.setConnectTimeout(20000);
          con.setReadTimeout(20000);
          con.setDoInput(true);
          con.setDoOutput(true);

          return con;

      } catch (MalformedURLException e){
          e.printStackTrace();
      } catch (IOException e){
          e.printStackTrace();
      }

      return null;
    }
}
