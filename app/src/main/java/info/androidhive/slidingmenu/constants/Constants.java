package info.androidhive.slidingmenu.constants;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;

import info.androidhive.slidingmenu.R;

/**
 * Created by Juan on 15/06/2016.
 */
public class Constants {

    public static SQLiteDatabase database =null;
    public static FragmentManager manager = null;

    public static void createNewFragment(int layoutId, Fragment fragment){
        if(Constants.manager!=null)
            Constants.manager.beginTransaction().replace(layoutId, fragment).commit();
    }
}
