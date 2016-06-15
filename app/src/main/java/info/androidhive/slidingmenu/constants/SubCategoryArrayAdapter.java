package info.androidhive.slidingmenu.constants;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.ProductView;
import info.androidhive.slidingmenu.R;

public class SubCategoryArrayAdapter extends ArrayAdapter {

    TextView chatText;
    private LinearLayout singleMessageContainer;
    int layout;
    int textViewResourceId;
    private String codSubCat;
    private ArrayList<CategoryMessage> categoryMessages;
    public void add(CategoryMessage object) {
        categoryMessages.add(object);
        super.add(object);

    }
    /*public CategoryArrayAdapter(int layout){
		this.layout = layout;
	}*/

    public SubCategoryArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<CategoryMessage> categoryMessages) {
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
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        //chatText.setMaxWidth(450);
        chatText.setText(MessageObj.title);
        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.message, layout, parent);

            }
        });
        return row;
    }

    public void producto(String codSubCat, int layout, ViewGroup parent) {
        View row;
        //ViewGroup parent;
        this.codSubCat = codSubCat;
        this.layout = layout;
        //layout = R.layote(layout, parent, false);
        final Context context = parent.getContext();
        Fragment fragment = null;
        fragment = new ProductView(codSubCat);
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }


}