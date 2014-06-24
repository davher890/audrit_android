package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.audirt.AudirtPut;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.audirt.R;
import com.clases.Usuario;

public class PwdActivity extends Activity {
		
	private EditText editAntigua;
	private EditText editNueva;
	private ImageButton botonAceptar;
	private EditText editRepite;
	private PwdActivity c;
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cambiopwd);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		c = this;
		editAntigua = (EditText) findViewById(R.id.editNombre);
		editNueva = (EditText) findViewById(R.id.editNueva);
		editRepite = (EditText) findViewById(R.id.EditRepite);
		botonAceptar = (ImageButton) findViewById(R.id.buttonCambiaPwd);
		
		botonAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject json = null;
				JSONObject user = null;
                try {
                	json = new JSONObject();
                	user = new JSONObject();
                	String mensaje_dialog = null;
                	if (!editAntigua.getText().toString().equals(Usuario.getPwd())){
                		
                		mensaje_dialog = "Contraseña incorrecta";
                	}
                	else if (editRepite.getText().toString().length() < 8 || 
                		editNueva.getText().toString().length() < 8){
                	
                		mensaje_dialog = "La nueva contraseña debe tener al menos 8 caracteres";
                	}
                	else if (!editRepite.getText().toString().equals(editNueva.getText().toString())){
                		
                		mensaje_dialog = "Las contraseñas no coinciden";
                	}
                		
                	if (mensaje_dialog != null){
                		final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
                		alertDialog.setTitle(mensaje_dialog);
                		alertDialog.setButton(RESULT_OK, "Aceptar", new DialogInterface.OnClickListener() {    				
    	    				@Override
    	    				public void onClick(DialogInterface dialog, int which) {
    	    					// TODO Auto-generated method stub		
    	    					alertDialog.cancel();
    	    				}
                		});
                		alertDialog.show();
                	}
                	else{
						json.put("current_password", editAntigua.getText().toString());
						json.put("password", editNueva.getText().toString());
						json.put("password_confirmation", editRepite.getText().toString());
						user.put("user", json);
                	}
					
				} catch (JSONException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		        AudirtPut aus = new AudirtPut("http://audirt.herokuapp.com/users?auth_token="+Usuario.getToken(),c, user);
		        aus.execute();
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}
}
