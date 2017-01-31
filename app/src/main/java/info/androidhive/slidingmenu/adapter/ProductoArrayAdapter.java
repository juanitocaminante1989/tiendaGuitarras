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

public class ProductoArrayAdapter extends ArrayAdapter {

    TextView articulo;
    TextView marca;
    TextView modelo;
    TextView precio;
    TextView descripcion;
    ImageView imagen;
    private LinearLayout sampleLinear;
    int layout;
    HashMap<Integer, View> productViews;
    private ArrayList<Producto> productos;
    private Button play;
    Context context;
    private ProgressBar progressBar;

    public void add(Producto object) {
        productos.add(object);
        super.add(object);

    }
    /*public CategoryArrayAdapter(int layout){
        this.layout = layout;
	}*/

    public ProductoArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<Producto> productos) {
        super(context, textViewResourceId);
        this.context = context;
        productViews = new HashMap<Integer, View>();
        this.layout = layout;
        this.productos = productos;
    }

    public int getCount() {
        return this.productos.size();
    }

    public Producto getItem(int index) {
        return (Producto) this.productos.get(index);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Producto MessageObj = getItem(position);
        if (!productViews.containsKey(position)) {
            convertView = inflater.inflate(layout, parent, false);

            sampleLinear = (LinearLayout) convertView.findViewById(R.id.samplelinear);
            articulo = (TextView) convertView.findViewById(R.id.articulo);
            marca = (TextView) convertView.findViewById(R.id.marca);
            modelo = (TextView) convertView.findViewById(R.id.modelo);
            precio = (TextView) convertView.findViewById(R.id.precio);
            imagen = (ImageView) convertView.findViewById(R.id.imagen);
            descripcion = (TextView) convertView.findViewById(R.id.descripcion);
            play = (Button) convertView.findViewById(R.id.playsample);
            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar4);

            productViews.put(position, convertView);

        } else {

            convertView = productViews.get(position);
        }

        if (MessageObj.codCat.equals("guitar100") || MessageObj.codCat.equals("bass200")) {
            sampleLinear.setVisibility(View.VISIBLE);
        } else {
            sampleLinear.setVisibility(View.GONE);
        }

        articulo.setText(" " + MessageObj.articulo);
        marca.setText(" " + MessageObj.marca);
        modelo.setText(" " + MessageObj.modelo);
        String price = Double.toString(MessageObj.precio);
        precio.setText(" " + price + "€ (IVA incluido)");
        File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath()+"/"+"tiendamusica");
        File[] files = filePath.listFiles();
        Uri uri = null;
        for (File file: files){
            if(file.getName().equals(MessageObj.directorio)){
                uri = Uri.fromFile(file);

            }
        }
//        holder.imagen.setBackgroundResource(-1);
        imagen.setImageURI(uri);
        descripcion.setText(" " + MessageObj.descripcion);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSample();
            }
        });
        return convertView;
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