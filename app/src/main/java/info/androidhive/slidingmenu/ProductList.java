package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.constants.SubCategoryArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;

public class ProductList extends Fragment {
    private TextView txtResultado;
    private RelativeLayout relLay;
    View rootView;
    public SubCategoryArrayAdapter categoryArrayAdapter;
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
    int layout;
    String codSubCat;

    public ProductList(int layout,String codSubCat) {
        this.layout = layout;
        this.codSubCat = codSubCat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Controller controller = new Controller();
        rootView = inflater.inflate(layout, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        categoryArrayAdapter = new SubCategoryArrayAdapter(getActivity().getApplicationContext(), R.layout.activity_subcategory, R.layout.activity_subcategory, controller.consultaArticulos(codSubCat));
        listView.setAdapter(categoryArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryMessage MessageObj = (CategoryMessage) adapterView.getItemAtPosition(i);
                producto(MessageObj.message);
            }
        });

        return rootView;

    }

    public void producto(String codSubCat){
        this.codSubCat = codSubCat;
        Context context = getActivity().getApplicationContext();
        Fragment fragment = null;
        fragment = new ProductView(codSubCat,R.layout.fragment_product);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }


}

