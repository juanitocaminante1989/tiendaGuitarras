package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;


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
