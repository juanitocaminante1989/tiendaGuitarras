package info.androidhive.slidingmenu.constants;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.ProductList;
import info.androidhive.slidingmenu.R;


public class CategoryArrayAdapter extends ArrayAdapter {

    Button chatText;
    private LinearLayout singleMessageContainer;
    int layout;
    private String codSubCat;
    private ArrayList<CategoryMessage> categoryMessages;
    //Guitarras guitarras;
    public void add(CategoryMessage object) {
        categoryMessages.add(object);
        super.add(object);

    }
    /*public CategoryArrayAdapter(int layout){
		this.layout = layout;
	}*/

    public CategoryArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<CategoryMessage> categoryMessages) {
        super(context, textViewResourceId);
        this.layout = layout;
        this.categoryMessages = categoryMessages;
    }

    public int getCount() {
        return this.categoryMessages.size();
    }

    public CategoryMessage getItem(int index) {
        return (CategoryMessage) this.categoryMessages.get(index);

    }

    public View getView(int position, View convertView, final ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        final CategoryMessage MessageObj = getItem(position);
        chatText = (Button) row.findViewById(R.id.singleMessage);
        //chatText.setMaxWidth(450);
        chatText.setText(MessageObj.message);
        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.title, layout, parent);

            }
        });

        return row;
    }

    public void producto(String codSubCat, int layout, ViewGroup parent) {
        View row;
        //ViewGroup parent;
        this.codSubCat = codSubCat;
        this.layout = layout;
        Fragment fragment = null;
        fragment = new ProductList(R.layout.fragment_subcategory,codSubCat);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }


}