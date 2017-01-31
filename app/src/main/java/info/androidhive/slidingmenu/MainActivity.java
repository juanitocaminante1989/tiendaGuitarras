package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.data.JSONParser;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.database.SlideSQLHelper;
import info.androidhive.slidingmenu.fragments.FragmentCreator;
import info.androidhive.slidingmenu.fragments.HomeFragment;
import info.androidhive.slidingmenu.fragments.ProfileFragment;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
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
    private EditText cuadroBusqueda;
    private Controller controller;
    private Bundle savedInstanceState;

    public Context context;
    private ProgressBar mProgressBar;
    private File file;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setContentView(R.layout.activity_main);


        SlideSQLHelper usdbh;
        usdbh = new SlideSQLHelper(context, "CarritoCompra", null, 1);
        Constants.database = usdbh.getWritableDatabase();

        Constants.manager = getFragmentManager();
        cuadroBusqueda = (EditText) findViewById(R.id.search);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        controller = new Controller();
        // load slide menu items
        this.savedInstanceState = savedInstanceState;



        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {

                recieveData();
            }
        }, 5000);
        //createFolder();


    }


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
        for (int i = 0; i < categoryId.size(); i++) {
            if (position == 0) {
                fragment = new HomeFragment(R.layout.activity_main, "");
                Constants.currentFragment = 0;
            } else if(position == 1){
                fragment = new ProfileFragment(context);
                Constants.currentFragment = 1;


            }else {
                fragment = new FragmentCreator(R.layout.fragment_layout, categoryId.get(position-1),context);
                Constants.currentFragment = 1;
            }
        }

        if (fragment != null) {
            Constants.currentFrag = fragment;
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

    public void busqueda(View v) {
        Fragment fragment = null;
        int layout;
        String busqueda = cuadroBusqueda.getText().toString();

        layout = R.layout.activity_main;
        fragment = new HomeFragment(layout, busqueda);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }

    public void onBackPressed() {

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

            Fragment fragment = new HomeFragment(R.layout.activity_main, "");

            if (fragment != null) {
                Constants.createNewFragment(R.id.frame_container, fragment);
                mDrawerList.setItemChecked(0, true);
                mDrawerList.setSelection(0);
                setTitle(navMenuTitles.get(0));
                mDrawerLayout.closeDrawer(mDrawerList);
                cuadroBusqueda.setText("");
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

            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL

            JSONArray json = jParser.getJSONFromUrl(Constants.url);
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
                                String queryCat = "INSERT INTO categoria VALUES ('" + codCat + "', '" + category_name + "', '" + description_cat + "')";
                                ContentValues initialValues= new ContentValues();
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
                                String querysubCat = "INSERT INTO subCategoria VALUES ('" + codSubCat + "', '" + codCat + "', '" + subcategory_name + "', '" + description_subcat + "')";
                                ContentValues initialValues= new ContentValues();
                                initialValues.put("codSubCat", codSubCat);
                                initialValues.put("codCat", codCat);
                                initialValues.put("subcategory_name", subcategory_name);
                                initialValues.put("descripcion", description_subcat);
                                id = Controller.insertOrUpdateSubCategory(initialValues);
                                if(deletedSub.equals("1")){
                                    Constants.database.delete("subCategoria", "codSubCat=?", new String[]{(String)initialValues.get("codSubCat")});
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
                                String directory = jsonObject.getJSONObject("articulo").get("directory").toString();
                                String deletedArt = jsonObject.getJSONObject("articulo").get("deletedArt").toString();

                                ContentValues initialValues= new ContentValues();
                                initialValues.put("codArticulo", cotArt);
                                initialValues.put("codSubCat", codSubCat);
                                initialValues.put("codCat", codCat);
                                initialValues.put("articulo_name", articulo_name);
                                initialValues.put("marca", marca_art);
                                initialValues.put("modelo", modelo_art);
                                initialValues.put("descripcion", description_art);
                                initialValues.put("precio", precio_art);
                                initialValues.put("IVA", iva_art);
                                initialValues.put("directorio", directory);
                                id = Controller.insertOrUpdateProduct(initialValues);
                                if(deletedArt.equals("1")){
                                    Constants.database.delete("articulo", "codArticulo=?", new String[]{(String)initialValues.get("codArticulo")});
                                }

                            }
                            Constants.database.beginTransaction();
                            Constants.database.endTransaction();
                        }

                    Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "Download error", Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.GONE);
                    dialog();
                }

            } catch (Exception e) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG);
            }
            if(id != -1) {
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

        for (int i = 0; i < controller.getCantidadCategorias(); i++) {
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
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }


    }


    private class connectFTPServer extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                downloadAndSaveFile("192.168.1.104",21, "android","android","blabla.txt",file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }


    private Boolean downloadAndSaveFile(String server, int portNumber,
                                        String user, String password, String filename, File localFile)
            throws IOException {
        FTPClient ftp = null;

        try {
            ftp = new FTPClient();
            ftp.connect(server,portNumber);
            Log.d("", "Connected. Reply: " + ftp.getReplyString());

            ftp.login(user, password);
            Log.d("", "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d("", "Downloading");
            ftp.enterLocalPassiveMode();

            OutputStream outputStream = null;
            String workingDir = ftp.printWorkingDirectory();
            FTPFile[] files = ftp.listFiles();


//            ftp.changeWorkingDirectory("");
            boolean success = false;
            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath()+"/"+"tiendamusica");
            if(!filePath.exists()){
                filePath.mkdirs();
            }
//            boolean change = ftp.changeWorkingDirectory(filePath);
            try {
                for(FTPFile file: files){
                    file.getName();

                    outputStream = new BufferedOutputStream(new FileOutputStream(filePath.getPath()+"/"+file.getName()));
                    success = ftp.retrieveFile(file.getName(), outputStream);
                }

            } finally {
                if (outputStream != null) {

                    outputStream.close();
                }
            }

            return success;
        } finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }
}
