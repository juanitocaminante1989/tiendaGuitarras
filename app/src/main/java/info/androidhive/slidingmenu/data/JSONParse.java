package info.androidhive.slidingmenu.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;

/**
 * Created by Juan on 22/03/2017.
 */

public class JSONParse  extends AsyncTask<String, Integer, JSONArray> {

    Context context;
    MainActivity activity;

    public JSONParse(Context context, MainActivity activity){
        this.context =context;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        activity.setMainlayoutVisible();
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        JSONParser jParser = new JSONParser();

        // Getting JSON from URL
        JSONArray json = jParser.getJSONArrayFromUrl(Constants.url);
        if (json != null)
            return json;
        else {
            activity.dialog();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray json) {
        int id = 0;
        try {
            // Getting JSON Array
            Constants.objects = new SparseArray<JSONObject>();
            if (json != null) {

                JSONObject jsonObject = null;
                for (int i = 0; i < json.length(); i++) {
                    jsonObject = json.getJSONObject(i);
                    if (jsonObject.has("categoria")) {
                        jsonObject.getJSONObject("categoria");
                        String codCat = jsonObject.getJSONObject("categoria").get("codCat").toString();
                        String category_name = jsonObject.getJSONObject("categoria").get("category_name").toString();
                        String description_cat = jsonObject.getJSONObject("categoria").get("descripcion").toString();
                        //String queryCat = "INSERT INTO categoria VALUES ('" + codCat + "', '" + category_name + "', '" + description_cat + "')";
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("codCat", codCat);
                        initialValues.put("category_name", category_name);
                        initialValues.put("descripcion", description_cat);
                        id = Controller.insertOrUpdateCategory(initialValues);

                    } else if (jsonObject.has("subcategoria")) {
                        jsonObject.getJSONObject("subcategoria");
                        String codSubCat = jsonObject.getJSONObject("subcategoria").get("codSubCat").toString();
                        String codCat = jsonObject.getJSONObject("subcategoria").get("codCat").toString();
                        String subcategory_name = jsonObject.getJSONObject("subcategoria").get("subcategory_name").toString();
                        String description_subcat = jsonObject.getJSONObject("subcategoria").get("descripcion").toString();
                        String deletedSub = jsonObject.getJSONObject("subcategoria").get("deleted").toString();
                        //String querysubCat = "INSERT INTO subCategoria VALUES ('" + codSubCat + "', '" + codCat + "', '" + subcategory_name + "', '" + description_subcat + "')";
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("codSubCat", codSubCat);
                        initialValues.put("codCat", codCat);
                        initialValues.put("subcategory_name", subcategory_name);
                        initialValues.put("descripcion", description_subcat);
                        id = Controller.insertOrUpdateSubCategory(initialValues);
                        if (deletedSub.equals("1")) {
                            Constants.database.delete("subCategoria", "codSubCat=?", new String[]{(String) initialValues.get("codSubCat")});
                        }

                    } else if (jsonObject.has("articulo")) {
                        jsonObject.getJSONObject("articulo");
                        String cotArt = jsonObject.getJSONObject("articulo").get("codArticulo").toString();
                        String codCat = jsonObject.getJSONObject("articulo").get("codCat").toString();
                        String codSubCat = jsonObject.getJSONObject("articulo").get("codSubCat").toString();
                        String articulo_name = jsonObject.getJSONObject("articulo").get("articulo_name").toString();
                        String description_art = jsonObject.getJSONObject("articulo").get("descripcion").toString();
                        String marca_art = jsonObject.getJSONObject("articulo").get("marca").toString();
                        String idmarca_art = jsonObject.getJSONObject("articulo").get("idMarca").toString();
                        String modelo_art = jsonObject.getJSONObject("articulo").get("modelo").toString();
                        String precio_art = jsonObject.getJSONObject("articulo").get("precio").toString();
                        String iva_art = jsonObject.getJSONObject("articulo").get("IVA").toString();
                        String views_art = jsonObject.getJSONObject("articulo").get("views").toString();
                        String deletedArt = jsonObject.getJSONObject("articulo").get("deletedArt").toString();

                        ContentValues initialValues = new ContentValues();
                        initialValues.put("codArticulo", cotArt);
                        initialValues.put("codSubCat", codSubCat);
                        initialValues.put("codCat", codCat);
                        initialValues.put("articulo_name", articulo_name);
                        initialValues.put("marca", marca_art);
                        initialValues.put("idMarca", idmarca_art);
                        initialValues.put("modelo", modelo_art);
                        initialValues.put("descripcion", description_art);
                        initialValues.put("precio", precio_art);
                        initialValues.put("IVA", iva_art);
                        initialValues.put("views", views_art);
                        id = Controller.insertOrUpdateProduct(initialValues);
                        if (deletedArt.equals("1")) {
                            Constants.database.delete("articulo", "codArticulo=?", new String[]{(String) initialValues.get("codArticulo")});
                        }

                    } else if (jsonObject.has("tiendas")) {
                        jsonObject.getJSONObject("tiendas");
                        String codTienda = jsonObject.getJSONObject("tiendas").get("idtienda").toString();
                        String nombre = jsonObject.getJSONObject("tiendas").get("nombre").toString();
                        String ciudad = jsonObject.getJSONObject("tiendas").get("ciudad").toString();
                        String calle = jsonObject.getJSONObject("tiendas").get("calle").toString();
                        String latitud = jsonObject.getJSONObject("tiendas").get("latitud").toString();
                        String longitud = jsonObject.getJSONObject("tiendas").get("longitud").toString();
                        String deletedTienda = jsonObject.getJSONObject("tiendas").get("deleted").toString();
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("idtienda", codTienda);
                        initialValues.put("nombre", nombre);
                        initialValues.put("ciudad", ciudad);
                        initialValues.put("calle", calle);
                        initialValues.put("latitud", latitud);
                        initialValues.put("longitud", longitud);
                        id = Controller.insertOrUpdateShops(initialValues);
                        if (deletedTienda.equals("1")) {
                            Constants.database.delete("tiendas", "idtienda=?", new String[]{(String) initialValues.get("idtienda")});
                        }

                    } else if (jsonObject.has("stock")) {
                        jsonObject.getJSONObject("stock");
                        String idStock = jsonObject.getJSONObject("stock").get("idStock").toString();
                        String codTienda = jsonObject.getJSONObject("stock").get("idtienda").toString();
                        String codarticulo = jsonObject.getJSONObject("stock").get("codArticulo").toString();
                        String stock = jsonObject.getJSONObject("stock").get("stock").toString();
                        String deletedStock = jsonObject.getJSONObject("stock").get("deleted").toString();
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("idStock", idStock);
                        initialValues.put("idtienda", codTienda);
                        initialValues.put("codArticulo", codarticulo);
                        initialValues.put("stock", stock);
                        id = Controller.insertOrUpdateStock(initialValues);
                        if (deletedStock.equals("1")) {
                            Constants.database.delete("stock", "idStock=?", new String[]{(String) initialValues.get("idStock")});
                        }

                    } else if (jsonObject.has("images")) {
                        jsonObject.getJSONObject("images");
                        String directory = jsonObject.getJSONObject("images").get("directory").toString();
                        String codarticulo = jsonObject.getJSONObject("images").get("codArticulo").toString();
                        String deletedimage = jsonObject.getJSONObject("images").get("deleted").toString();
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("directory", directory);
                        initialValues.put("codArticulo", codarticulo);
                        id = Controller.insertOrUpdateImages(initialValues);
                        if (deletedimage.equals("1")) {
                            Constants.database.delete("images", "directory=?", new String[]{(String) initialValues.get("directory")});
                        }

                    } else if (jsonObject.has("marcas")) {
                        jsonObject.getJSONObject("marcas");
                        String idmarcas = jsonObject.getJSONObject("marcas").get("idMarca").toString();
                        String nombre = jsonObject.getJSONObject("marcas").get("nombre").toString();
                        String deletedmarca = jsonObject.getJSONObject("marcas").get("deleted").toString();
                        ContentValues initialValues = new ContentValues();
                        initialValues.put("idMarca", idmarcas);
                        initialValues.put("nombre", nombre);
                        id = Controller.insertOrUpdateMarcas(initialValues);
                        if (deletedmarca.equals("1")) {
                            Constants.database.delete("marcas", "idMarca=?", new String[]{(String) initialValues.get("idMarca")});
                        }

                    }
                    Constants.database.beginTransaction();
                    Constants.database.endTransaction();
                }

                Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                activity.setLoadLayoutGone();
                activity.setMainlayoutVisible();
            } else {
                Toast.makeText(context, "Download error", Toast.LENGTH_LONG).show();
                activity.setLoadLayoutGone();
                activity.setMainlayoutVisible();
                activity.dialog();
            }


            if (id != -1) {
//                downloadAndSaveFile(Constants.IP, 21, "android", "android");
                new downloadAndSaveFile(Constants.IP, 21, "android", "android").execute();
            }
            activity.initialize();
        } catch (Exception e) {
            activity.setLoadLayoutGone();
            activity.setMainlayoutVisible();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // if we get here, length is known, now set indeterminate to false
        activity.setProgressBarValues(values);

        activity.setProgressUpdateText(values[0] * 2 + "%");
// set the drawable as progress drawable
    }
}
