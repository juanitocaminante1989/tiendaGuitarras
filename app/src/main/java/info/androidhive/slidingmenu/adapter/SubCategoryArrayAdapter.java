package info.androidhive.slidingmenu.adapter;

import java.util.ArrayList;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;

public class SubCategoryArrayAdapter extends ArrayAdapter {

    TextView chatText;
    private LinearLayout singleMessageContainer;
    int layout;
    private ArrayList<CategoryMessage> categoryMessages;
    Context context;

    public void add(CategoryMessage object) {
        categoryMessages.add(object);
        super.add(object);

    }

    public SubCategoryArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<CategoryMessage> categoryMessages) {
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

    public View getView(final int position, View convertView, final ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        final CategoryMessage MessageObj = getItem(position);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(MessageObj.title);
        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.message, layout, context);

                Constants.currentFragment = 3;
            }
        });

        return row;
    }

    public void producto(String codSubCat, int layout,Context context) {
        this.layout = layout;
        Fragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductoArrayAdapter(context, R.layout.activity_products_main, controller.consulta(codSubCat));
        if(Constants.manager!= null)
            Constants.manager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }


}