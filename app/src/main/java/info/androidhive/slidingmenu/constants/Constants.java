package info.androidhive.slidingmenu.constants;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.entities.Client;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.fragments.CustomFragment;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 15/06/2016.
 */
public class Constants {

    public static SQLiteDatabase database =null;
    public static FragmentManager manager = null;

    public static void createNewFragment(int layoutId, CustomFragment fragment){
        try {
            if (Constants.manager != null) {
                FragmentTransaction e = Constants.manager.beginTransaction();
                e.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                e.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);

                e.replace(layoutId, fragment).commit();
            }
        }catch (Exception e){
            DebugUtilities.writeLog("Error",e);
        }
    }

    public static void createNewFragment(int layoutId, CustomFragment fragment, String tag){
        try {
            if (Constants.manager != null) {
                FragmentTransaction e = Constants.manager.beginTransaction();
                e.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                e.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);


                e.replace(layoutId, fragment, tag).commit();
            }
        }catch (Exception e){
            DebugUtilities.writeLog("Error",e);
        }
    }

    public final static String IP = "192.168.1.100";
    public final static String url = "http://"+IP+":80/CarritoCompra/conexion.php";
    public final static String restService = "http://"+IP+":80/CarritoCompra/restservice.php";
    public final static String recievedata = "http://"+IP+":80/CarritoCompra/recievedata.php";
    public final static String checkUser = "http://"+IP+":80/CarritoCompra/checkUser.php";

    public static SparseArray<JSONObject> objects;
    public static boolean hasExecuted = false;
    public static int currentFragment = -1;
    public static int whichFragment = -1;
    public static String subCategoryPosition = "";
    public static CustomFragment currentFrag = null;
    public static boolean userLogged = false;
    public static String currentFragmentStr = "";
    public static int idMarca = -1;
    public static Producto productSent = null;
    public static Client currentClient = null;


}
