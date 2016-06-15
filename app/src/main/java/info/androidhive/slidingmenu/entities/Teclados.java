package info.androidhive.slidingmenu.entities;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
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

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.constants.CategoryArrayAdapter;

public class Teclados extends Fragment {
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
	int layout;
    
	public Teclados(int layout){
		this.layout = layout;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		Controller controller = new Controller();
        rootView = inflater.inflate(R.layout.fragment_teclados, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView)rootView.findViewById(R.id.listView1);
		listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		String query = "keyboard4";
        categoryArrayAdapter = new CategoryArrayAdapter(getActivity().getApplicationContext(), layout, layout, controller.consultaSubCategorias(query));
        listView.setAdapter(categoryArrayAdapter);
        return rootView;
        
    }

}