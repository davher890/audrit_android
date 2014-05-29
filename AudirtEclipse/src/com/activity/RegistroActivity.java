package com.activity;

import com.audirt.R;
import com.audirt.R.id;
import com.audirt.R.layout;
import com.audirt.R.menu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class RegistroActivity extends Activity {

	private TextView mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	protected static final int DATE_DIALOG_ID = 999;
	Button fechaNac;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registroform);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		mDateDisplay = (TextView) findViewById(R.id.mDateDisplay);
		fechaNac=(Button) findViewById(R.id.buttonFechaNac);
		
		fechaNac.setOnClickListener(new OnClickListener() {
			 
            @Override
            public void onClick(View v) { 
                showDialog(DATE_DIALOG_ID);
            } 
        });
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {

		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}    
	private void updateDisplay() {
		mDateDisplay.setText(
			new StringBuilder()
			// Month is 0 based so add 1
			.append(mMonth + 1).append("-")
			.append(mDay).append("-")
			.append(mYear).append(" "));
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener =
		new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

}
