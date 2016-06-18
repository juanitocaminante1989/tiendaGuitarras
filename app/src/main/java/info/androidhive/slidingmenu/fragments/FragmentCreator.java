package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.os.Bundle;
import android.text.Layout;
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
public class FragmentCreator extends Fragment{

    private TextView txtResultado;
    private RelativeLayout relLay;
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

    public View FragmentCreator(int layout, String query) {
        View view = null;
        this.query=query;
        this.layout = layout;
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(layout, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;
//       rootView = FragmentCreator()
        rootView = this.FragmentCreator(this.layout, this.query);
        return rootView;

    }


}
