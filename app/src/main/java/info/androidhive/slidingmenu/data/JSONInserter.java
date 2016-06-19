package info.androidhive.slidingmenu.data;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.tables.ArticuloTable;
import info.androidhive.slidingmenu.database.tables.CategoriaTable;
import info.androidhive.slidingmenu.database.tables.SubcategoriaTable;

/**
 * Created by Juan on 19/06/2016.
 */
public class JSONInserter {

    public void insert (JSONObject jsonObject) throws JSONException{
        if(jsonObject.has(CategoriaTable.TABLE_NAME)) {
            jsonObject.getJSONObject(CategoriaTable.TABLE_NAME);
            String codCat = jsonObject.getJSONObject(CategoriaTable.TABLE_NAME).get(CategoriaTable.COD_CAT).toString();
            String category_name = jsonObject.getJSONObject(CategoriaTable.TABLE_NAME).get(CategoriaTable.CATEGORY_NAME).toString();
            String description_cat = jsonObject.getJSONObject(CategoriaTable.TABLE_NAME).get(CategoriaTable.CATEGORY_DESCRIPTION).toString();
            String queryCat = CategoriaTable.INSERT_TABLE(codCat, category_name, description_cat);
            Constants.database.execSQL(queryCat);
        }else if(jsonObject.has(SubcategoriaTable.TABLE_NAME)){
            jsonObject.getJSONObject(SubcategoriaTable.TABLE_NAME);
            String codSubCat = jsonObject.getJSONObject(SubcategoriaTable.TABLE_NAME).get(SubcategoriaTable.COD_SUB_CAT).toString();
            String codCat = jsonObject.getJSONObject(SubcategoriaTable.TABLE_NAME).get(SubcategoriaTable.COD_CAT).toString();
            String subcategory_name = jsonObject.getJSONObject(SubcategoriaTable.TABLE_NAME).get(SubcategoriaTable.SUBCATEGORY_NAME).toString();
            String description_subcat = jsonObject.getJSONObject(SubcategoriaTable.TABLE_NAME).get(SubcategoriaTable.SUBCATEGORY_DESCRIPTION).toString();
            String querysubCat = SubcategoriaTable.INSERT_TABLE(codSubCat, codCat, subcategory_name, description_subcat);
            Constants.database.execSQL(querysubCat);

        }else if(jsonObject.has(ArticuloTable.TABLE_NAME)){
            jsonObject.getJSONObject(ArticuloTable.TABLE_NAME);
            String cotArt = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.COD_ARTICULO).toString();
            String codCat = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.COD_CAT).toString();
            String codSubCat = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.COD_SUB_CAT).toString();
            String articulo_name = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.ARTICULO_NAME).toString();
            String description_art = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.ARTICULO_DESCRIPTION).toString();
            String marca_art = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.MARCA).toString();
            String modelo_art = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.MODELO).toString();
            String precio_art = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.PRECIO).toString();
            String iva_art = jsonObject.getJSONObject(ArticuloTable.TABLE_NAME).get(ArticuloTable.IVA).toString();
            String queryArt =ArticuloTable.INSERT_TABLE(cotArt, codCat, codSubCat, articulo_name, description_art, marca_art, modelo_art, precio_art, iva_art, "asdfa");

            Constants.database.execSQL(queryArt);
        }
    }

}
