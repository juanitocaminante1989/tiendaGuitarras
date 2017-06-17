package info.androidhive.slidingmenu.data;

import android.os.AsyncTask;
import android.util.Log;

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
import info.androidhive.slidingmenu.entities.Producto;

/**
 * Created by Juan on 22/03/2017.
 */

public class JSONUpdateProducts extends AsyncTask<JSONObject, JSONObject, JSONObject> {

    @Override
    protected JSONObject doInBackground(JSONObject... data) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000);

        JSONObject jsonResponse = new JSONObject();
        ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        JSONObject jsonObject = null;
        try {
            Producto producto = Constants.productSent;

            jsonObject = new JSONObject();

            jsonObject.put("codArticulo", producto.getCodArticulo());
            jsonObject.put("codCat", producto.getCodCat());
            jsonObject.put("codSubCat", producto.getCodSubCat());
            jsonObject.put("articulo_name", producto.getArticulo());
            jsonObject.put("descripcion", producto.getDescripcion());
            jsonObject.put("marca", producto.getMarca());
            jsonObject.put("idMarca", producto.getIdMarca());
            jsonObject.put("modelo", producto.getModelo());
            jsonObject.put("precio", producto.getPrecio());
            jsonObject.put("IVA", producto.getIVA());
            jsonObject.put("views", producto.getViews());
            jsonObjects.add(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpPost post = new HttpPost(Constants.recievedata);
        try {

            String jsonString = jsonObjects.toString();
            StringEntity se = new StringEntity("json=" + jsonString);
            post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.setEntity(se);

            HttpResponse response;
            response = client.execute(post);
            String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());

//            jsonResponse = new JSONObject(resFromServer);
//            Log.i("Response from server", jsonResponse.getString("msg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

}