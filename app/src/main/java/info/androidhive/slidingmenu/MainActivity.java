package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.data.JSONParser;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.database.SlideSQLHelper;
import info.androidhive.slidingmenu.fragments.HomeFragment;
import info.androidhive.slidingmenu.fragments.ProfileFragment;
import info.androidhive.slidingmenu.fragments.SearchFragment;
import info.androidhive.slidingmenu.model.NavDrawerItem;
import info.androidhive.slidingmenu.services.NotificationBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    private int mInterval = 5000; // 5 seconds by default, can be changed later

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView SecondDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private ArrayList<String> navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private Controller controller;
    private Bundle savedInstanceState;
    private LinearLayout mainLayout;
    private LinearLayout loadLayout;
    private TextView progressUpdate;

    public Context context;
    private ProgressBar mProgressBar;
    private Handler mHandler;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_main);

        SlideSQLHelper usdbh;
        usdbh = new SlideSQLHelper(context, "CarritoCompra", null, 1);
        Constants.database = usdbh.getWritableDatabase();
        Constants.manager = getFragmentManager();


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        SecondDrawerList = (ListView) findViewById(R.id.second_list_slidermenu);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        loadLayout = (LinearLayout) findViewById(R.id.load_layout);
        progressUpdate = (TextView) findViewById(R.id.progressUpdate);
        actionBar = getActionBar();

        controller = new Controller();
        // load slide menu items
        this.savedInstanceState = savedInstanceState;

        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (connection() == true) {
                    recieveData();
                } else {
                    mainLayout.setVisibility(View.VISIBLE);
                    loadLayout.setVisibility(View.GONE);
                    initialize();

                }
                startRepeatingTask();
            }
        }, 3000);

    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                //this function can change value of mInterval.
                new JSONReceive().execute();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        boolean secondDrawerOpen = mDrawerLayout.isDrawerOpen(SecondDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        menu.findItem(R.id.action_settings).setVisible(!secondDrawerOpen);
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
        for (int i = 0; i < categoryId.size(); i++) {
            if (position == 0) {
                fragment = new HomeFragment(context);
                Constants.currentFragment = 0;
                Constants.currentFragmentStr = "home";
            } else if (position == 1) {
                fragment = new ProfileFragment(context);
                Constants.currentFragment = 1;
            } else if (position == 2) {
                fragment = new SearchFragment(context);
                Constants.currentFragment = 1;
                Constants.currentFragmentStr = "srch";
            } else {
//                fragment = new FragmentCreator(R.layout.fragment_layout, categoryId.get(position - 1), context);
//                Constants.currentFragment = 1;

                setSecondDrawerList(categoryId.get(position - 2));
                mDrawerLayout.openDrawer(SecondDrawerList);
            }
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        setTitle(navMenuTitles.get(position));
        if (fragment != null) {
            Constants.currentFrag = fragment;
            Constants.createNewFragment(R.id.frame_container, fragment);

            // update selected item and title, then close the drawer

            mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerLayout.closeDrawer(SecondDrawerList);
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
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }



    public void onBackPressed() {
        final ColorDrawable cd = new ColorDrawable(Color.rgb(0, 0, 255));
        actionBar.setBackgroundDrawable(cd);
        if (Constants.currentFragment == 0) {
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
        } else if (Constants.currentFragment == 1) {
            Fragment fragment = null;
            if(Constants.currentFragmentStr.equals("srch")) {
                fragment = new SearchFragment(context);
            }else if(Constants.currentFragmentStr.equals("home")){
                fragment = new HomeFragment(context);
            }else{
                fragment = new HomeFragment(context);
            }
            if (fragment != null) {
                Constants.createNewFragment(R.id.frame_container, fragment);
                mDrawerList.setItemChecked(0, true);
                mDrawerList.setSelection(0);
                setTitle(navMenuTitles.get(0));
                mDrawerLayout.closeDrawer(mDrawerList);

            }
            Constants.currentFragment = 0;

        } else if (Constants.currentFragment == 2) {
            if (Constants.currentFrag != null)
                Constants.createNewFragment(R.id.frame_container, Constants.currentFrag);

            Constants.currentFragment = 1;

        } else if (Constants.currentFragment == 3) {
            Fragment fragment = new ProductList(R.layout.fragment_subcategory, Constants.subCategoryPosition, context);
            Constants.createNewFragment(R.id.frame_container, fragment);

            Constants.currentFragment = 2;
        }
    }


    public void recieveData() {
        new JSONParse().execute();
    }

    public void dialog() {
        new AlertDialog.Builder(context)
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

    private class JSONParse extends AsyncTask<String, Integer, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mainLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONArray json = jParser.getJSONArrayFromUrl(Constants.url);
            if (json != null)
                return json;
            else {
                dialog();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            int id = 0;
            try {
                // Getting JSON Array
                Constants.objects = new ArrayList<JSONObject>();
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
                            String modelo_art = jsonObject.getJSONObject("articulo").get("modelo").toString();
                            String precio_art = jsonObject.getJSONObject("articulo").get("precio").toString();
                            String iva_art = jsonObject.getJSONObject("articulo").get("IVA").toString();
                            String deletedArt = jsonObject.getJSONObject("articulo").get("deletedArt").toString();

                            ContentValues initialValues = new ContentValues();
                            initialValues.put("codArticulo", cotArt);
                            initialValues.put("codSubCat", codSubCat);
                            initialValues.put("codCat", codCat);
                            initialValues.put("articulo_name", articulo_name);
                            initialValues.put("marca", marca_art);
                            initialValues.put("modelo", modelo_art);
                            initialValues.put("descripcion", description_art);
                            initialValues.put("precio", precio_art);
                            initialValues.put("IVA", iva_art);
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

                        }else if (jsonObject.has("stock")) {
                            jsonObject.getJSONObject("stock");
                            String codTienda = jsonObject.getJSONObject("stock").get("idtienda").toString();
                            String codarticulo = jsonObject.getJSONObject("stock").get("codArticulo").toString();
                            String stock = jsonObject.getJSONObject("stock").get("stock").toString();
                            String deletedStock = jsonObject.getJSONObject("stock").get("deleted").toString();
                            ContentValues initialValues = new ContentValues();
                            initialValues.put("idtienda", codTienda);
                            initialValues.put("codArticulo", codarticulo);
                            initialValues.put("stock", stock);
                            id = Controller.insertOrUpdateStock(initialValues);
                            if (deletedStock.equals("1")) {
                                Constants.database.delete("stock", "idtienda=?", new String[]{(String) initialValues.get("idtienda")});
                            }

                        }else if (jsonObject.has("images")) {
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

                        }
                        Constants.database.beginTransaction();
                        Constants.database.endTransaction();
                    }

                    Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                    loadLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(context, "Download error", Toast.LENGTH_LONG).show();
                    loadLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    dialog();
                }

            } catch (Exception e) {
                loadLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG);
            }
            if (id != -1) {
                new connectFTPServer().execute();
            }
            initialize();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // if we get here, length is known, now set indeterminate to false
            mProgressBar.setIndeterminate(false);
            mProgressBar.setMax(100);
            mProgressBar.setProgress(values[0]);
            mProgressBar.setProgress(0);
            mProgressBar.setMax(100);
            mProgressBar.setProgress(values[0]);


// set the drawable as progress drawable
            progressUpdate.setText(values[0]*2+"%");
        }
    }

    private class JSONReceive extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(Constants.restService);
            if (json != null)
                return json;
            else {
                dialog();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            int id = 0;
            try {
                // Getting JSON Array

                if (json != null) {
                    JSONObject jsonObject = null;

                    jsonObject = json;
                    if (jsonObject.has("info")) {

                        NotificationBuilder.Build(jsonObject.get("info").toString(), "Catalogo actualizado", context, MainActivity.class);

                        new JSONTransmitter().execute();
                    }

                } else {
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // if we get here, length is known, now set indeterminate to false
            mProgressBar.setIndeterminate(false);
            mProgressBar.setMax(100);
            mProgressBar.setProgress(values[0]);
        }
    }

    public void initialize() {

        navMenuTitles = controller.getCategoryNames();

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        //navMenuIcons.
        for (int i = 0; i < controller.getCantidadCategorias()+3; i++) {
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

        try {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, //nav menu toggle icon
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(mTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                    if (mDrawerLayout.isDrawerOpen(SecondDrawerList)) {
                        mDrawerLayout.closeDrawer(SecondDrawerList);
                    }
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSecondDrawerList(final String codCat) {

        try {
            ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
            for (int i = 0; i < controller.consultaSubCategorias(codCat).size(); i++) {
                NavDrawerItem navDrawerItem = new NavDrawerItem(controller.consultaSubCategorias(codCat).get(i).getMessage(), navMenuIcons.getResourceId(i, -1));
                navDrawerItems.add(navDrawerItem);
            }

            SecondDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),
                    navDrawerItems);
            SecondDrawerList.setAdapter(adapter);

            SecondDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    subCategory(controller.consultaSubCategorias(codCat).get(i).title, context);


                    Constants.currentFragment = 2;

                    mDrawerLayout.closeDrawer(SecondDrawerList);
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void subCategory(String codSubCat, Context context) {

        Fragment fragment = null;
        Constants.subCategoryPosition = codSubCat;
        fragment = new ProductList(R.layout.fragment_subcategory,codSubCat, context);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }
    private class connectFTPServer extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                downloadAndSaveFile(Constants.IP, 21, "android", "android");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }


    private boolean connection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        int connection = info.getType();

        boolean connectionBool = false;
        switch (connection) {

            case TelephonyManager.NETWORK_TYPE_UMTS:
                connectionBool = false;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                connectionBool = false;
                break;
            case ConnectivityManager.TYPE_WIFI:
                connectionBool = true;
                break;
            default:
                connectionBool = false;
        }
        return connectionBool;
    }

    private Boolean downloadAndSaveFile(String server, int portNumber,
                                        String user, String password)
            throws IOException {
        FTPClient ftp = null;

        try {
            ftp = new FTPClient();
            ftp.connect(server, portNumber);
            Log.d("", "Connected. Reply: " + ftp.getReplyString());

            ftp.login(user, password);
            Log.d("", "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d("", "Downloading");
            ftp.enterLocalPassiveMode();

            OutputStream outputStream = null;
            String workingDir = ftp.printWorkingDirectory();
            FTPFile[] files = ftp.listFiles();

            boolean success = false;
            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
            if (!filePath.exists()) {
                filePath.mkdirs();
            }else{
                filePath.delete();
                filePath.mkdirs();
            }

            try {
                for (FTPFile file : files) {

                    outputStream = new BufferedOutputStream(new FileOutputStream(filePath.getPath() + "/" + file.getName()));
                    success = ftp.retrieveFile(file.getName(), outputStream);
                    outputStream.close();
                }

            } finally {

            }

            return success;
        } finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }

    public class JSONTransmitter extends AsyncTask<JSONObject, JSONObject, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONObject... data) {

            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000);

            JSONObject jsonResponse = new JSONObject();
            try {
                jsonResponse.put("msg", "ok");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpPost post = new HttpPost(Constants.recievedata);
            try {
                StringEntity se = new StringEntity("json=" + jsonResponse.toString());
                post.addHeader("content-type", "application/x-www-form-urlencoded");
                post.setEntity(se);

                HttpResponse response;
                response = client.execute(post);
                String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());

                jsonResponse = new JSONObject(resFromServer);
                Log.i("Response from server", jsonResponse.getString("msg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResponse;
        }

    }

}
