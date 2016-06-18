package info.androidhive.slidingmenu.constants;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import java.util.ArrayList;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.ProductList;
import info.androidhive.slidingmenu.R;


public class CategoryArrayAdapter extends ArrayAdapter {

    Button chatText;
    int layout;
    private ArrayList<CategoryMessage> categoryMessages;
    public void add(CategoryMessage object) {
        categoryMessages.add(object);
        super.add(object);

    }

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
        final CategoryMessage MessageObj = getItem(position);
        chatText = (Button) row.findViewById(R.id.singleMessage);
        chatText.setText(MessageObj.message);
        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.title, layout);

            }
        });

        return row;
    }

    public void producto(String codSubCat, int layout) {
        this.layout = layout;
        Fragment fragment = null;
        fragment = new ProductList(R.layout.fragment_subcategory,codSubCat);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }


}