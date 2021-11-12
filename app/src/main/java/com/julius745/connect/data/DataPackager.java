package com.julius745.connect.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataPackager {
    String username, password;

    public DataPackager(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String packData(){
        JSONObject jo = new JSONObject();
        StringBuffer packedData = new StringBuffer();

        try{
            jo.put("username", username);
            jo.put("password", password);

            Boolean firstvalue = true;

            Iterator it = jo.keys();

            do {
                String key = it.next().toString();
                String value = jo.get(key).toString();

                if (firstvalue){
                    firstvalue=false;
                }else{
                    packedData.append("&");
                }

                packedData.append(URLEncoder.encode(key,"UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value,"UTF-8"));
            }while(it.hasNext());
            return packedData.toString();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return null;
    }
}
