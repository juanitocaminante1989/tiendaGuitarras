package info.androidhive.slidingmenu.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.TimerTask;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.fragments.HomeFragment;
import info.androidhive.slidingmenu.fragments.ProductFragment;

/**
 * Created by Juan on 13/03/2017.
 */

public class CustomPagerAdapterProduct extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    SparseArray<Producto> productos;
    ImageView imageView;
    TextView itemName;
    TextView itemPrice;

    public CustomPagerAdapterProduct(Context context, SparseArray<Producto> producto) {
        mContext = context;
        this.productos = producto;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.view_pager_item, container, false);

        imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        itemName = (TextView) itemView.findViewById(R.id.itemText);
        itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
        LinearLayout offerLinear = (LinearLayout) itemView.findViewById(R.id.offerLinear);


        final Producto producto = productos.get(position);
        itemName.setText(producto.getArticulo());
        itemPrice.setText(producto.getPrecio() + " €");
        File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
        File[] files = filePath.listFiles();
        Uri uri = null;
        if(producto.getDirectorio().size()>0) {
            for (File file : files) {
                if (file.getName().equals(producto.getDirectorio().get(0).getDirectory())) {
                    uri = Uri.fromFile(file);

                }
            }
            imageView.setImageURI(uri);
        }
        offerLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.currentFragment == 0)
                    Constants.currentFragment = 1;
                ProductFragment.goToSimilarProduct(producto.getCodArticulo(), mContext);
            }
        });
        container.addView(itemView);

        return itemView;
    }

    public void setCurrentItem(int position){
        Producto producto = productos.get(position);
        itemName.setText(producto.getArticulo());
        itemPrice.setText(producto.getPrecio() + " €");
        File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
        File[] files = filePath.listFiles();
        Uri uri = null;
        for (File file : files) {
            if (file.getName().equals(producto.getDirectorio().get(0).getDirectory())) {
                uri = Uri.fromFile(file);

            }
        }
        imageView.setImageURI(uri);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
