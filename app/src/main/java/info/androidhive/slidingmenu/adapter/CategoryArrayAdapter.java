package info.androidhive.slidingmenu.adapter;

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
import info.androidhive.slidingmenu.constants.Constants;


public class CategoryArrayAdapter extends ArrayAdapter {

    Button chatText;
    int layout;
    private ArrayList<CategoryMessage> categoryMessages;
    Context context;
    public void add(CategoryMessage object) {
        categoryMessages.add(object);
        super.add(object);

    }

    public CategoryArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<CategoryMessage> categoryMessages) {
        super(context, textViewResourceId);
        this.context = context;
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

                subCategory(MessageObj.title, layout, context);

                Constants.currentFragment = 2;
            }
        });

        return row;
    }

    public void subCategory(String codSubCat, int layout, Context context) {
        this.layout = layout;
        Fragment fragment = null;
        Constants.subCategoryPosition = codSubCat;
        fragment = new ProductList(R.layout.fragment_subcategory,codSubCat, context);
        Constants.createNewFragment(R.id.frame_container, fragment);
    }


}