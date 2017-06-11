package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 16/03/2017.
 */

public abstract class CustomFragment extends Fragment {

    private int layout = 0;
    private View rootView = null;
    private Context context = null;
    private int codProd;
    private Producto producto;
    private String codSubCat;
    private String tag;
    private String title;

    public CustomFragment(int layout, View rootView, Context context, String codSubCat, String tag, String title) {
        this.layout = layout;
        this.rootView = rootView;
        this.context = context;
        this.codSubCat = codSubCat;
        this.tag = tag;
        this.title = title;
    }

    public CustomFragment(int layout, View rootView, Context context, Producto producto, String tag, String title) {
        this.layout = layout;
        this.rootView = rootView;
        this.context = context;
        this.producto = producto;
        this.tag = tag;
        this.title = title;
    }

    public CustomFragment(int layout, View rootView, Context context, int codProd, String tag, String title) {
        this.layout = layout;
        this.rootView = rootView;
        this.context = context;
        this.codProd = codProd;
        this.tag = tag;
        this.title = title;
    }

    public CustomFragment(int layout, View rootView, Context context, String tag, String title) {
        this.layout = layout;
        this.rootView = rootView;
        this.context = context;
        this.tag = tag;
        this.title = title;
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.layout != 0 && this.rootView == null) {
            this.rootView = inflater.inflate(this.layout, container, false);
            this.context = this.getActivity();
            return this.onCreateCustomView(this.rootView);
        } else {
            return this.rootView;
        }
    }

    protected void sendViewToBack(View child) {
        ViewGroup parent = (ViewGroup)child.getParent();
        if(null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }

    }

    protected void sendViewToFront(View child) {
        ViewGroup parent = (ViewGroup)child.getParent();
        if(null != parent) {
            parent.removeView(child);
            parent.addView(child);
        }

    }
    public abstract View onCreateCustomView(View var1);

    public String getFragmentTag(){
        return this.tag;
    }

    public String getTitle(){
        return this.title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.rootView = null;
    }

//    public Thread.UncaughtExceptionHandler UnCaughtExceptionHandler(){
//
//        Thread.UncaughtExceptionHandler thread = new Thread.UncaughtExceptionHandler(){
//            @Override
//            public void uncaughtException(Thread thread, Throwable throwable) {
//                DebugUtilities.writeLog("Error: "+throwable.getMessage());
//            }
//        };
//
//        Thread.setDefaultUncaughtExceptionHandler(thread);
//        return  thread;
//    }
}
