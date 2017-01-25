package info.androidhive.slidingmenu.constants;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;

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

    public final static String url = "http://192.168.1.104:80/CarritoCompra/conexion.php";

    public static ArrayList<JSONObject> objects;
    public static boolean hasExecuted = false;
    public static int currentFragment = -1;
    public static String subCategoryPosition = "";
    public static Fragment currentFrag = null;

}
