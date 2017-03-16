package info.androidhive.slidingmenu.util;

import android.util.Log;

/**
 * Created by Juan on 16/03/2017.
 */

public class DebugUtilities {
    private static final String TAG = "DEBUG";

    public DebugUtilities() {
    }

    public static void writeLog(String text) {
        writeLog(text, (Exception)null);
    }

    public static void writeLog(String text, Exception excep) {
        Log.e(TAG, text);
        if(excep != null) {
            excep.printStackTrace();
        }

    }
}
