package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class StarterActivity extends Activity  {

	ProgressBar mProgressBar;
	CountDownTimer mCountDownTimer;
	
	int i=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	
		

		mProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
		mProgressBar.setProgress(i);
		   mCountDownTimer=new CountDownTimer(2000,1000) {

		        @Override
		        public void onTick(long millisUntilFinished) {
		            Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
		            i++;
		            mProgressBar.setProgress(i);

		        }

		        @Override
		        public void onFinish() {
		        //Do what you want
		        	Intent y = new Intent();
		        	y.setClassName("info.androidhive.slidingmenu","info.androidhive.slidingmenu.MainActivity");
		        	y.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
		        	
		    		startActivity(y);
		        	finish();
		            i++;
		            mProgressBar.setProgress(i);
		            
		        }
		    };
		    mCountDownTimer.start();
	}
}
