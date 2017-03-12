package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
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
import info.androidhive.slidingmenu.database.Controller;

/**
 * Created by Juan on 18/06/2016.
 */
public class FragmentCreator extends Fragment{

    View rootView;
    public ListView listView;
    String query;
    int layout;
    Context context;

    public FragmentCreator(int layout, String query, Context context) {
        this.context = context;
        this.query=query;
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Controller controller = new Controller();
        rootView = inflater.inflate(R.layout.fragment_accesorios, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        return rootView;

    }
}
