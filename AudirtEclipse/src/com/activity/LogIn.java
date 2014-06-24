package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.audirt.AudirtService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.audirt.R;
import com.clases.Usuario;

/**
 * Created by david on 8/06/13.
 */
public class LogIn extends Activity {

	private ImageButton bsign;
	private ImageButton bregistro;
	private ImageButton bpwd;
	private EditText email;
	private EditText pwd;
	private LogIn c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        this.c = this;

        bsign   = (ImageButton)findViewById(R.id.buttonSign);
        bregistro   = (ImageButton)findViewById(R.id.buttonRegistro);
        bpwd   	= (ImageButton)findViewById(R.id.buttonPwd);
        email   = (EditText)findViewById(R.id.editNombre);
        pwd     = (EditText)findViewById(R.id.editText2);

        bsign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            	String em = email.getText().toString();
            	String pw = pwd.getText().toString();

            	JSONObject json = new JSONObject();
                try {
					json.put("user",em);
					json.put("password", pw);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                AudirtService aus = new AudirtService("http://audirt.herokuapp.com/api/token",c, json);
		        aus.execute();
            }
        });

        bregistro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            	if (Usuario.getToken() == null){
            		final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
            		alertDialog.setTitle("Debe iniciar sesión");
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
	                Intent i = new Intent(LogIn.this, RegistroActivity.class);
	                startActivityForResult(i, 1);
            	}
            }
        });
        
        bpwd.setOnClickListener(new View.OnClickListener() {
        	
			public void onClick(View v) {
				
				if (Usuario.getToken() == null){
            		final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
            		alertDialog.setTitle("Debe iniciar sesión");
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
					Intent i = new Intent(LogIn.this, PwdActivity.class);
	                startActivityForResult(i, 1);
            	}
			}
		});
    }
    
    public void gestionaWS(String json){
    	JSONObject respJSON;
		try {
			respJSON = new JSONObject(json);
			Usuario.setToken(respJSON.getString("token"));
			Usuario.setId(respJSON.getString("idusuario"));
			Usuario.setPwd(pwd.getText().toString());
			Usuario.setEmail(email.getText().toString());
			Intent i = new Intent(LogIn.this, InscodeActivity.class);
            startActivityForResult(i, 1);
			
		} catch (JSONException e) {			
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Email o contraseña incorrectos");
			alertDialog.setButton(RESULT_OK, "Aceptar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub		
					alertDialog.cancel();
				}
			});
			alertDialog.show();
		}
		
		//Recuperar datos Usuario
    }
}
