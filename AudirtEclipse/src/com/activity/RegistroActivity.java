package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.audirt.AudirtGet;
import android.audirt.AudirtPut;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.audirt.R;
import com.clases.Usuario;

public class RegistroActivity extends Activity {

	private TextView mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private EditText editNombre;
	private EditText editApellidos;
	private EditText editTelefono;
	private EditText editCPostal;
	private EditText editCiudad;
	private EditText editDireccion;
	private Spinner sexoSpinner;
	
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
		editApellidos = (EditText) findViewById(R.id.Edit1Apellido);
		editTelefono = (EditText) findViewById(R.id.EditTelefono);
		editDireccion = (EditText) findViewById(R.id.EditDireccion);
		editCPostal = (EditText) findViewById(R.id.EditCodPostal);
		editCiudad = (EditText) findViewById(R.id.EditCiudad);
		sexoSpinner = (Spinner) findViewById(R.id.spinnerSexo);
		
		TextView textoHola = (TextView) findViewById(R.id.textHolaUsuario);
		textoHola.setText("Hola, "+Usuario.getEmail());
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sexoArray,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexoSpinner.setAdapter(adapter);
		
		botonAceptar = (ImageButton) findViewById(R.id.buttonSign);
		
		botonAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject json = null;
				JSONObject datos_user = null;
                try {
                	json = new JSONObject();
                    datos_user = new JSONObject();
					json.put("nombre", editNombre.getText().toString());
					json.put("apellidos", editApellidos.getText().toString());
					json.put("bday", mDateDisplay.getText().toString());
					json.put("telefono", editTelefono.getText().toString());
					json.put("sexo", sexoSpinner.getSelectedItem().toString());
					json.put("direccion", "direccion");
					json.put("cpostal", editCPostal.getText().toString());
					json.put("ciudad", editCiudad.getText().toString());
					datos_user.put("datos_user", json);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
		        AudirtPut aus = new AudirtPut("http://audirt.herokuapp.com/usuarios/"+Usuario.getId()+"?auth_token="+Usuario.getToken(),c, datos_user);
		        aus.execute();
			}
		});
		
		fechaNac.setOnClickListener(new OnClickListener() {
			 
            @Override
            public void onClick(View v) { 
                showDialog(DATE_DIALOG_ID);
            } 
        });
		
		AudirtGet aus = new AudirtGet("http://audirt.herokuapp.com/usuarios/"+Usuario.getId()+"?auth_token="+Usuario.getToken(),this);
		aus.execute();
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
	
	public void gestionaGet(String json) {
		// TODO Auto-generated method stub
		
		JSONObject respJSON;
		try {
			respJSON = new JSONObject(json);
			
			editNombre.setText(respJSON.getString("nombre"));
			editApellidos.setText(respJSON.getString("apellidos"));
			mDateDisplay.setText(respJSON.getString("bday"));
			editTelefono.setText(respJSON.getString("telefono"));
			for (int i=0; i< sexoSpinner.getCount(); i++){
				if (sexoSpinner.getItemAtPosition(i).equals(respJSON.getString("sexo"))){
					sexoSpinner.setSelection(i);
				}				
			}
			editCiudad.setText(respJSON.getString("ciudad"));
			editCPostal.setText(respJSON.getString("cpostal"));
			editDireccion.setText(respJSON.getString("direccion"));
		}
		catch (JSONException e) {			
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error al recuperar los datos del Usuario");
			alertDialog.setButton(RESULT_OK, "Aceptar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub		
					alertDialog.cancel();
				}
			});
			alertDialog.show();
		}		
	}

}