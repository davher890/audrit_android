package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.audirt.AudirtService;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.audirt.R;
import com.clases.Token;

public class RegistroActivity extends Activity {

	private TextView mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private EditText editNombre;
	private EditText edit1Apellido;
	private EditText edit2Apellido;
	private EditText editFechaNac;
	private EditText editTelefono;
	private EditText editSexo;
	private EditText editDireccion;
	private EditText editCPostal;
	private EditText editCiudad;
	
	private ImageButton botonAceptar;
	
	private RegistroActivity c;
	
	protected static final int DATE_DIALOG_ID = 999;
	Button fechaNac;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registroform);
		
		c=this;
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		mDateDisplay = (TextView) findViewById(R.id.mDateDisplay);
		fechaNac=(Button) findViewById(R.id.buttonFechaNac);
		
		editNombre = (EditText) findViewById(R.id.editNombre);
		edit1Apellido = (EditText) findViewById(R.id.Edit1Apellido);
		edit2Apellido = (EditText) findViewById(R.id.Edit2Apellido);
		editTelefono = (EditText) findViewById(R.id.EditTelefono);
		editSexo = (EditText) findViewById(R.id.EditSexo);
		//editDireccion = (EditText) findViewById(R.id.edit);
		editCPostal = (EditText) findViewById(R.id.EditCodPostal);
		editCiudad = (EditText) findViewById(R.id.EditCiudad);
		
		//"datos_user"=>{"nombre"=>"Ruben", "apellidos"=>"", "bday"=>"", "telefono"=>"", "sexo"=>"masculino", "direccion"=>"", "cpostal"=>"", "ciudad"=>""}
		botonAceptar = (ImageButton) findViewById(R.id.buttonSign);
		
		botonAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject json = new JSONObject();
                try {
					json.put("nombre", editNombre.getText().toString());
					json.put("apellidos", new StringBuffer(edit1Apellido.getText().toString()).append(edit2Apellido.getText().toString()));
					json.put("bday", mDateDisplay.getText().toString());
					json.put("telefono", editTelefono.getText().toString());
					json.put("sexo", editSexo.getText().toString());
					json.put("direccion", "asdassad");
					json.put("cpostal", editCPostal.getText().toString());
					json.put("ciudad", editCiudad.getText().toString());
					json.put("", Token.getToken());
					
				} catch (JSONException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}   
		        AudirtService aus = new AudirtService("http://audirt.herokuapp.com/users",c, json);
		        aus.execute();
			}
		});
		
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