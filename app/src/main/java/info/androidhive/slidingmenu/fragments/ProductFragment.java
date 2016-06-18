package info.androidhive.slidingmenu.fragments;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.CategoryArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;

/**
 * Created by Juan on 18/06/2016.
 */
public class ProductFragment extends FragmentCreator{

    private TextView txtResultado;
    private RelativeLayout relLay;
    View rootView;
    public CategoryArrayAdapter categoryArrayAdapter;
    public LinearLayout opciones;
    public ListView listView;
    public Animation animFadein1;
    public Animation animFadein2;
    public NotificationManager myNotificationManager;
    public int numMessagesOne = 0;
    public int notificationIdOne = 111;
    List<String> mensajes = new ArrayList<String>();
    String noti1;
    String noti2;
    String noti3;
    String query;
    int layout;

    public ProductFragment(int layout, String query) {
        this.query=query;
        this.layout = layout;
    }


    @Override
    public View FragmentCreator(int layout, String query) {

        View view = super.FragmentCreator(layout, query);

        return view;
    }
}
