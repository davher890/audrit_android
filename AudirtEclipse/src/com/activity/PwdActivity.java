package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.audirt.AudirtService;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.audirt.R;
import com.clases.Token;

public class PwdActivity extends Activity {
	
	protected static final int DATE_DIALOG_ID = 999;
	
	private EditText editAntigua;
	private EditText editNueva;
	private ImageButton botonAceptar;
	private EditText editRepite;
	private PwdActivity c;
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cambiopwd);
		
		c = this;
		editAntigua = (EditText) findViewById(R.id.editNombre);
		editNueva = (EditText) findViewById(R.id.editNueva);
		editRepite = (EditText) findViewById(R.id.EditRepite);
		botonAceptar = (ImageButton) findViewById(R.id.buttonCambiaPwd);
		
		botonAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject json = new JSONObject();
                try {
					json.put("current_password", editAntigua.getText().toString());
					json.put("password", editNueva.getText().toString());
					json.put("password_confirmation", editRepite.getText().toString());
					json.put("auth_token", Token.getToken());
					
				} catch (JSONException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}   
		        AudirtService aus = new AudirtService("http://audirt.herokuapp.com/users",c, json);
		        aus.execute();
			}
		});
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}
}
