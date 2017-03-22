package info.androidhive.slidingmenu.data;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 22/03/2017.
 */

public class GetClients extends AsyncTask<String, Integer, JSONArray> {

    Context context;
    MainActivity activity;

    public GetClients(Context context, MainActivity activity){
        this.context = context;
        this.activity= activity;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {

        JSONParser jsonParser = new JSONParser();

        JSONArray jsonArray = jsonParser.getJSONArrayFromUrl(Constants.getClients);

        if(jsonArray != null)
            return jsonArray;
        else
            return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try{
            JSONObject jsonObject = null;
            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.has("clientes")) {

                        jsonObject.getJSONObject("clientes");
                        String nif = jsonObject.getJSONObject("clientes").get("NIF").toString();
                        String nombre = jsonObject.getJSONObject("clientes").get("nombre").toString();
                        String apellidos = jsonObject.getJSONObject("clientes").get("apellidos").toString();
                        String direccion = jsonObject.getJSONObject("clientes").get("direccion").toString();
                        String codPost = jsonObject.getJSONObject("clientes").get("codPost").toString();
                        String correo = jsonObject.getJSONObject("clientes").get("correo").toString();
                        String telefono = jsonObject.getJSONObject("clientes").get("telefono").toString();
                        String clave = jsonObject.getJSONObject("clientes").get("clave").toString();
                        String logged = jsonObject.getJSONObject("clientes").get("logged").toString();

                        if (Controller.getClient(nif) != null) {
                            Constants.userLogged = true;
                            activity.recieveData();
                            break;

                        } else {
                            Constants.userLogged = false;
                            activity.initLoggin();
                            activity.setMainlayoutVisible();
                            activity.setLoadLayoutGone();
                        }

                    } else {

                        Constants.userLogged = false;
                        activity.initLoggin();
                        activity.setMainlayoutVisible();
                        activity.setLoadLayoutGone();
                    }
                }
            }else{
                Constants.userLogged = false;
                activity.initLoggin();
                activity.setMainlayoutVisible();
                activity.setLoadLayoutGone();

            }
        }catch (Exception e){
            DebugUtilities.writeLog("GetClients:onPostExecute",e);
        }
    }
}
