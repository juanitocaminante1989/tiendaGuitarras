package info.androidhive.slidingmenu.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Images;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.fragments.ProductFragment;

public class SubCategoryArrayAdapter extends ArrayAdapter {

    TextView chatText;
    TextView itemPrice;
    private LinearLayout singleMessageContainer;
    int layout;
    ImageView itemImage;
    private ArrayList<Producto> categoryMessages;
    Context context;
    LinearLayout itemLinear;

    public void add(Producto object) {
        categoryMessages.add(object);
        super.add(object);

    }

    public SubCategoryArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<Producto> categoryMessages) {
        super(context, textViewResourceId);
        this.context = context;
        this.layout = layout;
        this.categoryMessages = categoryMessages;
    }

    public int getCount() {
        return this.categoryMessages.size();
    }

    public Producto getItem(int index) {
        return (Producto) this.categoryMessages.get(index);

    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        final Producto MessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.itemText);
        itemPrice = (TextView) row.findViewById(R.id.itemPrice);
        itemLinear = (LinearLayout) row.findViewById(R.id.itemLinear);
        itemImage = (ImageView) row.findViewById(R.id.itemImage);
        File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
        File[] files = filePath.listFiles();
        Uri uri = null;
        if(MessageObj.getDirectorio().size()>0) {
            for (File file : files) {
                if (file.getName().equals(MessageObj.getDirectorio().get(0).getDirectory())) {
                    uri = Uri.fromFile(file);

                }
            }
            itemImage.setImageURI(uri);
        }
//        holder.imagen.setBackgroundResource(-1);

        chatText.setText(MessageObj.getArticulo());
        itemPrice.setText(MessageObj.getPrecio() + "€");
        itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.getCodArticulo(), layout, context);

                Constants.currentFragment = 3;
            }
        });

        return row;
    }

    public void producto(String codSubCat, int layout, Context context) {
        this.layout = layout;
        Fragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductFragment(context, R.layout.activity_products_main, controller.consulta(codSubCat));
        Constants.createNewFragment(R.id.frame_container, fragment);

    }


}