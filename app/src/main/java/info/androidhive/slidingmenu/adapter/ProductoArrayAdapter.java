package info.androidhive.slidingmenu.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.entities.Producto;

public class ProductoArrayAdapter extends Fragment {

    TextView articulo;
    TextView marca;
    TextView modelo;
    TextView precio;
    TextView descripcion;
    ImageView imagen;
    private LinearLayout sampleLinear;
    int layout;
    HashMap<Integer, View> productViews;
    private Producto producto;
    private Button play;
    Context context;
    private ProgressBar progressBar;


    /*public CategoryArrayAdapter(int layout){
        this.layout = layout;
	}*/

    public ProductoArrayAdapter(Context context, int layout, Producto producto) {

        this.context = context;
        productViews = new HashMap<Integer, View>();
        this.layout = layout;
        this.producto = producto;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_products_main, container, false);

            sampleLinear = (LinearLayout) rootView.findViewById(R.id.samplelinear);
            articulo = (TextView) rootView.findViewById(R.id.articulo);
            marca = (TextView) rootView.findViewById(R.id.marca);
            modelo = (TextView) rootView.findViewById(R.id.modelo);
            precio = (TextView) rootView.findViewById(R.id.precio);
            imagen = (ImageView) rootView.findViewById(R.id.imagen);
            descripcion = (TextView) rootView.findViewById(R.id.descripcion);
            play = (Button) rootView.findViewById(R.id.playsample);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar4);


        if (producto.codCat.equals("guitar100") || producto.codCat.equals("bass200")) {
            sampleLinear.setVisibility(View.VISIBLE);
        } else {
            sampleLinear.setVisibility(View.GONE);
        }

        articulo.setText(" " + producto.articulo);
        marca.setText(" " + producto.marca);
        modelo.setText(" " + producto.modelo);
        String price = Double.toString(producto.precio);
        precio.setText(" " + price + "€ (IVA incluido)");
        File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath()+"/"+"tiendamusica");
        File[] files = filePath.listFiles();
        Uri uri = null;
        for (File file: files){
            if(file.getName().equals(producto.directorio)){
                uri = Uri.fromFile(file);

            }
        }
//        holder.imagen.setBackgroundResource(-1);
        imagen.setImageURI(uri);
        descripcion.setText(" " + producto.descripcion);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSample();
            }
        });
        return rootView;
    }



    private void playSample() {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sample);
        final SeekBar seekBar = new SeekBar(context);
        final int duration = mp.getDuration();
        final int amountToUpdate = duration / 100;
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (!(amountToUpdate * seekBar.getProgress() >= duration)) {
                    int p = seekBar.getProgress();
                    p += 1;
                    seekBar.setProgress(p);
                }
            }
        }, amountToUpdate);
        mp.start();
    }

}