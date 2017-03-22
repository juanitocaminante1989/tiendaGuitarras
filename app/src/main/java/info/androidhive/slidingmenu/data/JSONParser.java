package info.androidhive.slidingmenu.data;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 18/06/2016.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONArray jArray = null;
    static JSONObject jObj = null;
    static String jsonString = "";

    // constructor
    public JSONParser() {

    }

    public JSONArray getJSONArrayFromUrl(String url) {

        // Making HTTP request
        try {
            // defaultHttpClient
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            is = httpEntity.getContent();

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            jsonString = sb.toString();
        } catch (Exception e) {
            DebugUtilities.writeLog("JSON Parser Error converting data " , e);
        }

        // try parse the string to a JSON object
        try {
            if(jsonString != null) {
                if(jsonString.length()>0) {
                    String newJson = jsonString.substring(0, jsonString.length() - 1);
                    if(newJson.equals("null"))
                        return null;
                    else
                        jArray = new JSONArray(newJson);
                }
            }
        } catch (JSONException e) {
            DebugUtilities.writeLog("JSON Parser Error parsing data " , e);
        }

        // return JSON String
        return jArray;

    }


    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            // defaultHttpClient
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 5000);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            is = httpEntity.getContent();

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            jsonString = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            if(jsonString != null) {
                if(jsonString.length()>0) {
                    String newJson = jsonString.substring(0, jsonString.length() - 1);
                    jObj = new JSONObject(newJson);
                }
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}