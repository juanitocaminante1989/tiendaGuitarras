package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.constants.BusquedaArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.data.JSONParser;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.database.SlideSQLHelper;
import info.androidhive.slidingmenu.fragments.FragmentCreator;
import info.androidhive.slidingmenu.fragments.HomeFragment;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listView;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private ArrayList<String> navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private EditText cuadroBusqueda;
    private Controller controller;
    private Bundle savedInstanceState;
    private BusquedaArrayAdapter busquedaArrayAdapter;
    private static String url = "http://192.168.1.108:80/CarritoCompra/conexion.php";

    //JSON Node Names
    private static final String TAG_USER = "user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private Context context;

    JSONArray user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideSQLHelper usdbh;
        usdbh = new SlideSQLHelper(this.getApplicationContext(), "CarritoCompra", null, 1);
        Constants.database = usdbh.getWritableDatabase();
        Constants.manager = getFragmentManager();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        controller = new Controller();
        // load slide menu items
        this.savedInstanceState = savedInstanceState;
        recieveData();


    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        int layout;
        Controller controller = new Controller();
        ArrayList<String> categoryId = controller.getCategoryId();
        for(int i = 0;i<categoryId.size();i++){
            if(position==0){
                fragment = new HomeFragment(R.layout.activity_main,"");
            }else{
                fragment = new FragmentCreator(R.layout.fragment_layout, categoryId.get(position));
            }
        }

        if (fragment != null) {
            Constants.createNewFragment(R.id.frame_container, fragment);

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles.get(position));
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(mDrawerToggle!=null)
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        if(mDrawerToggle!=null)
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void busqueda(View v) {
        Fragment fragment = null;
        int layout;
        cuadroBusqueda = (EditText) findViewById(R.id.search);
        String busqueda = cuadroBusqueda.getText().toString();
        layout = R.layout.activity_main;
        fragment = new HomeFragment(layout, busqueda);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Saliendo de la Aplicacion")
                .setMessage("¿Quieres cerrar la Tienda de Musica?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public void recieveData(){
        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONArray> {
//        private ProgressDialog pDialog;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pDialog = new ProgressDialog(context);
//            pDialog.setMessage("Getting Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//
//        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONArray json = jParser.getJSONFromUrl(url);
            if(json != null)
                return json;
            else
                return null;
        }
        @Override
        protected void onPostExecute(JSONArray json) {
            try {
                // Getting JSON Array
                Constants.objects = new ArrayList<JSONObject>();
                if(json!=null) {
                    JSONObject jsonObject = null;
                    for(int i = 0; i<json.length();i++){
                        jsonObject = json.getJSONObject(i);
                        if(jsonObject.has("categoria")) {
                            jsonObject.getJSONObject("categoria");
                            String codCat = jsonObject.getJSONObject("categoria").get("codCat").toString();
                            String category_name = jsonObject.getJSONObject("categoria").get("category_name").toString();
                            String description_cat = jsonObject.getJSONObject("categoria").get("descripcion").toString();
                            String queryCat = "INSERT INTO categoria VALUES ('"+codCat+"', '"+category_name+"', '"+description_cat+"')";
                            Constants.database.execSQL(queryCat);
                        }else if(jsonObject.has("subcategoria")){
                            jsonObject.getJSONObject("subcategoria");
                            String codSubCat = jsonObject.getJSONObject("subcategoria").get("codSubCat").toString();
                            String codCat = jsonObject.getJSONObject("subcategoria").get("codCat").toString();
                            String subcategory_name = jsonObject.getJSONObject("subcategoria").get("subcategory_name").toString();
                            String description_subcat = jsonObject.getJSONObject("subcategoria").get("descripcion").toString();
                            String querysubCat = "INSERT INTO subCategoria VALUES ('"+codSubCat+"', '"+codCat+"', '"+subcategory_name+"', '"+description_subcat+"')";
                            Constants.database.execSQL(querysubCat);

                        }else if(jsonObject.has("articulo")){
                            jsonObject.getJSONObject("articulo");
                            String cotArt = jsonObject.getJSONObject("articulo").get("codArticulo").toString();
                            String codCat = jsonObject.getJSONObject("articulo").get("codCat").toString();
                            String codSubCat = jsonObject.getJSONObject("articulo").get("codSubCat").toString();
                            String articulo_name = jsonObject.getJSONObject("articulo").get("articulo_name").toString();
                            String description_art = jsonObject.getJSONObject("articulo").get("descripcion").toString();
                            String marca_art = jsonObject.getJSONObject("articulo").get("marca").toString();
                            String modelo_art = jsonObject.getJSONObject("articulo").get("modelo").toString();
                            String precio_art = jsonObject.getJSONObject("articulo").get("precio").toString();
                            String iva_art = jsonObject.getJSONObject("articulo").get("IVA").toString();
                            String queryArt = "INSERT INTO articulo  VALUES('"+cotArt+"','"+codSubCat+"', '"+codCat+"', '"+articulo_name+"', '"+marca_art+"', '"+modelo_art+"', '"+description_art+"', "+precio_art+", "+iva_art+" , 'fenderstrdwh')";

                            Constants.database.execSQL(queryArt);
                        }

                    }

                }

            } catch (JSONException e) {
                Log.d("", e.toString());
            }
            initialize();
        }
    }

    public void initialize(){
        navMenuTitles = controller.getCategoryNames();

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);



        navDrawerItems = new ArrayList<NavDrawerItem>();
        context = this.getApplicationContext();
        // adding nav drawer items to array
        //navMenuIcons.



        for(int i = 0;i<controller.getCantidadCategorias();i++){
            NavDrawerItem navDrawerItem = new NavDrawerItem(navMenuTitles.get(i), navMenuIcons.getResourceId(i, -1));
            navDrawerItems.add(navDrawerItem);
        }


        //navDrawerItems.get(0).;
        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        this.deleteDatabase("CarritoCompra");
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }
}
