package info.androidhive.slidingmenu.constants;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.entities.Producto;

/**
 * Created by Juan on 15/06/2016.
 */
public class Constants {

    public static SQLiteDatabase database =null;
    public static FragmentManager manager = null;

    public static void createNewFragment(int layoutId, Fragment fragment){
        if(Constants.manager!=null) {
            Constants.manager.beginTransaction().replace(layoutId, fragment).commit();
        }
    }

    public static void createNewFragment(int layoutId, Fragment fragment, String tag){
        if(Constants.manager!=null) {
            Constants.manager.beginTransaction().replace(layoutId, fragment, tag).commit();
        }
    }

    public final static String IP = "192.168.1.100";
    public final static String url = "http://"+IP+":80/CarritoCompra/conexion.php";
    public final static String restService = "http://"+IP+":80/CarritoCompra/restservice.php";
    public final static String recievedata = "http://"+IP+":80/CarritoCompra/recievedata.php";

    public static ArrayList<JSONObject> objects;
    public static boolean hasExecuted = false;
    public static int currentFragment = -1;
    public static int whichFragment = -1;
    public static String subCategoryPosition = "";
    public static Fragment currentFrag = null;
    public static boolean userLogged = false;
    public static String currentFragmentStr = "";
    public static int idMarca = -1;
    public static Producto productSent = null;

}
