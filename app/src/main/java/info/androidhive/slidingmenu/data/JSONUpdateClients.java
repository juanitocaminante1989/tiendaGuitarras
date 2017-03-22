package info.androidhive.slidingmenu.data;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 22/03/2017.
 */

public class JSONUpdateClients extends AsyncTask<JSONObject, JSONObject, JSONObject> {

    @Override
    protected JSONObject doInBackground(JSONObject... data) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000);

        JSONObject jsonResponse = new JSONObject();
        ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        JSONObject jsonObject = null;
        try {
            if (Constants.currentClient != null) {

                jsonObject = new JSONObject();
                jsonObject.put("NIF", Constants.currentClient.getNif());
                jsonObject.put("nombre", Constants.currentClient.getNombre());
                jsonObject.put("apellidos", Constants.currentClient.getApellidos());
                jsonObject.put("direccion", Constants.currentClient.getDireccion());
                jsonObject.put("codPost", Constants.currentClient.getCodPos());
                jsonObject.put("correo", Constants.currentClient.getCorreo());
                jsonObject.put("telefono", Constants.currentClient.getTelefono());
                jsonObject.put("clave", Constants.currentClient.getClave());
                jsonObject.put("logged", 1);

                jsonObjects.add(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpPost post = new HttpPost(Constants.checkUser);
        try {

            String jsonString = jsonObjects.toString();
            StringEntity se = new StringEntity("json=" + jsonString);
            se.setContentType("Application/JSON");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            post.setEntity(se);

            HttpResponse response;
            response = client.execute(post);
            String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());

//                resFromServer = resFromServer.replace("\\\\","");
//                jsonResponse = new JSONObject("{\"msg\":\"ok\"}");
//                jsonResponse = new JSONObject(resFromServer);
            if (resFromServer.equals("ok")) {
                Constants.success = true;
            } else if(resFromServer.equals("1062")){
                Constants.mysqlErrNo = Integer.parseInt(resFromServer);
                Constants.success = false;
            }
//                Log.i("Response from server", jsonResponse.getString("msg"));
        } catch (Exception e) {
            DebugUtilities.writeLog("Error: ", e);
        }

        return jsonResponse;
    }

}
