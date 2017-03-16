package info.androidhive.slidingmenu.entities;

import android.widget.Button;

public class CategoryMessage {

	public String title;
	public String message;
	static String blab = "";

	public CategoryMessage(){
		this.message = "";
		this.title = "";
	}

	public CategoryMessage(String title, String message) {
		// TODO Auto-generated method stub
		super();
		this.title = title;
		this.message = message;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public void bla(){
		int i = 3;
		float j = 23;

		blab = null;
	}
}

