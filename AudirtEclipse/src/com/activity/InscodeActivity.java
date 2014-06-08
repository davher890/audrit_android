package com.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.audirt.AudirtService;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.audirt.R;
import com.clases.Token;

public class InscodeActivity extends Activity implements OnTouchListener {
	
	Button ba;
	Button bb;
	Button bc;
	Button bd;
	Button be;
	Button b0;
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	Button b7;
	Button b8;
	Button b9;
	Button bdel;
	Button bintro;
	
	TextView code;
	
	String status;
    String tiempo;
    String imagen;
    
    boolean envia = true;
    
    Context c;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.inscode);
	    
	    c = this;
	    
	    ba = (Button) findViewById(R.id.ButtonA);
		bb = (Button) findViewById(R.id.ButtonB);
		bc = (Button) findViewById(R.id.ButtonC);
		bd = (Button) findViewById(R.id.ButtonD);
		be = (Button) findViewById(R.id.ButtonE);
		b0 = (Button) findViewById(R.id.Button0);
		b1 = (Button) findViewById(R.id.Button1);
		b2 = (Button) findViewById(R.id.Button2);
		b3 = (Button) findViewById(R.id.Button3);
		b4 = (Button) findViewById(R.id.Button4);
		b5 = (Button) findViewById(R.id.Button5);
		b6 = (Button) findViewById(R.id.Button6);
		b7 = (Button) findViewById(R.id.Button7);
		b8 = (Button) findViewById(R.id.Button8);
		b9 = (Button) findViewById(R.id.Button9);
		bdel = (Button) findViewById(R.id.ButtonDEL);
		bintro = (Button) findViewById(R.id.ButtonINTRO);
		
		
		ba.setOnTouchListener(this);		
		bb.setOnTouchListener(this);
		bc.setOnTouchListener(this);
		bd.setOnTouchListener(this);
		be.setOnTouchListener(this);
		b0.setOnTouchListener(this);
		b1.setOnTouchListener(this);
		b2.setOnTouchListener(this);
		b3.setOnTouchListener(this);
		b4.setOnTouchListener(this);
		b5.setOnTouchListener(this);
		b6.setOnTouchListener(this);
		b7.setOnTouchListener(this);
		b8.setOnTouchListener(this);
		b9.setOnTouchListener(this);
		bdel.setOnTouchListener(this);
		bintro.setOnTouchListener(this);
		
		code = (TextView) findViewById(R.id.TextViewCode);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		// TODO Auto-generated method stub
		if (v.getId() == ba.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				ba.setBackgroundResource(R.drawable.boton_pulsado_letra_a);
				aniadeCode('A');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				ba.setBackgroundResource(R.drawable.boton_reposo_letra_a);
		}
		
		if (v.getId() == bb.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				bb.setBackgroundResource(R.drawable.boton_pulsado_letra_b);
				aniadeCode('B');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				bb.setBackgroundResource(R.drawable.boton_reposo_letra_b);
		}
		
		if (v.getId() == bc.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				bc.setBackgroundResource(R.drawable.boton_pulsado_letra_c);
				aniadeCode('C');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				bc.setBackgroundResource(R.drawable.boton_reposo_letra_c);
		}
		
		if (v.getId() == bd.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				bd.setBackgroundResource(R.drawable.boton_pulsado_letra_d);
				aniadeCode('D');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				bd.setBackgroundResource(R.drawable.boton_reposo_letra_d);
		}
		
		if (v.getId() == be.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				be.setBackgroundResource(R.drawable.boton_pulsado_letra_e);
				aniadeCode('E');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				be.setBackgroundResource(R.drawable.boton_reposo_letra_e);
		}
		
		if (v.getId() == b0.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b0.setBackgroundResource(R.drawable.boton_pulsado_num_0);
				aniadeCode('0');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b0.setBackgroundResource(R.drawable.boton_reposo_num_0);
		}
		
		if (v.getId() == b1.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b1.setBackgroundResource(R.drawable.boton_pulsado_num_1);
				aniadeCode('1');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b1.setBackgroundResource(R.drawable.boton_reposo_num_1);
		}
		
		if (v.getId() == b2.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b2.setBackgroundResource(R.drawable.boton_pulsado_num_2);
				aniadeCode('2');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b2.setBackgroundResource(R.drawable.boton_reposo_num_2);
		}
		
		if (v.getId() == b3.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b3.setBackgroundResource(R.drawable.boton_pulsado_num_3);
				aniadeCode('3');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b3.setBackgroundResource(R.drawable.boton_reposo_num_3);
		}
		
		if (v.getId() == b4.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b4.setBackgroundResource(R.drawable.boton_pulsado_num_4);
				aniadeCode('4');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b4.setBackgroundResource(R.drawable.boton_reposo_num_4);
		}
		
		if (v.getId() == b5.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b5.setBackgroundResource(R.drawable.boton_pulsado_num_5);
				aniadeCode('5');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b5.setBackgroundResource(R.drawable.boton_reposo_num_5);
		}
		
		if (v.getId() == b6.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b6.setBackgroundResource(R.drawable.boton_pulsado_num_6);
				aniadeCode('6');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b6.setBackgroundResource(R.drawable.boton_reposo_num_6);
		}
		
		if (v.getId() == b7.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b7.setBackgroundResource(R.drawable.boton_pulsado_num_7);
				aniadeCode('7');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b7.setBackgroundResource(R.drawable.boton_reposo_num_7);
		}
		
		if (v.getId() == b8.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b8.setBackgroundResource(R.drawable.boton_pulsado_num_8);
				aniadeCode('8');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b8.setBackgroundResource(R.drawable.boton_reposo_num_8);
		}
		
		if (v.getId() == b9.getId()){
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				b9.setBackgroundResource(R.drawable.boton_pulsado_num_9);
				aniadeCode('9');
			}
			else if (e.getAction() == MotionEvent.ACTION_UP)
				b9.setBackgroundResource(R.drawable.boton_reposo_num_9);
		}
		
		if (v.getId() == bdel.getId()){
			
			if (e.getAction() == MotionEvent.ACTION_DOWN){
				bdel.setBackgroundResource(R.drawable.boton_pulsado_atras);
			
				if (code.getText().toString().length() > 0){
					
					char letra = code.getText().toString().charAt(code.getText().toString().length()-1);
					
					switch (letra){
					case 'A':
						ba.setBackgroundResource(R.drawable.boton_reposo_letra_a);
						break;
						
					case 'B':
						bb.setBackgroundResource(R.drawable.boton_reposo_letra_b);
						break;
						
					case 'C':
						bc.setBackgroundResource(R.drawable.boton_reposo_letra_c);
						break;
						
					case 'D':
						bd.setBackgroundResource(R.drawable.boton_reposo_letra_d);
						break;
						
					case 'E':
						be.setBackgroundResource(R.drawable.boton_reposo_letra_e);
						break;
						
					case '0':
						b0.setBackgroundResource(R.drawable.boton_reposo_num_0);
						break;
						
					case '1':
						b1.setBackgroundResource(R.drawable.boton_reposo_num_1);
						break;
						
					case '2':
						b2.setBackgroundResource(R.drawable.boton_reposo_num_2);
						break;
						
					case '3':
						b3.setBackgroundResource(R.drawable.boton_reposo_num_3);
						break;
						
					case '4':
						b4.setBackgroundResource(R.drawable.boton_reposo_num_4);
						break;
						
					case '5':
						b5.setBackgroundResource(R.drawable.boton_reposo_num_5);
						break;
						
					case '6':
						b6.setBackgroundResource(R.drawable.boton_reposo_num_6);
						break;
						
					case '7':
						b7.setBackgroundResource(R.drawable.boton_reposo_num_7);
						break;
						
					case '8':
						b8.setBackgroundResource(R.drawable.boton_reposo_num_8);
						break;
						
					case '9':
						b9.setBackgroundResource(R.drawable.boton_reposo_num_9);
						break;
	
					default: 
						break;				
					}
					code.setText(code.getText().toString().substring(0, code.getText().toString().length()-1));
				}
			}
			if (e.getAction() == MotionEvent.ACTION_UP)
				bdel.setBackgroundResource(R.drawable.boton_reposo_atras);
		}
		
		if (v.getId() == bintro.getId()){
			if(e.getAction() == MotionEvent.ACTION_UP){
				
				bintro.setBackgroundResource(R.drawable.boton_reposo_enviar);
				
				if (code.getText().toString().length() == 6 && envia){
					JSONObject json = new JSONObject();
	                try {
						json.put("codigo", code.getText().toString());
						json.put("auth_token", Token.getToken());
					} catch (JSONException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}   
			        AudirtService aus = new AudirtService("http://audirt.herokuapp.com/codes.json",this, json);
			        aus.execute();
				}
			}
			else if(e.getAction() == MotionEvent.ACTION_DOWN)
				bintro.setBackgroundResource(R.drawable.boton_pulsado_enviar);
		}
		return false;
			
	}

	private void aniadeCode(char digit) {
		if (code.getText().length() < 6)
			code.setText(code.getText().toString()+digit);
	}
	
	public void introduceBBDD(String json) {
        JSONObject respJSON;
		try {
			respJSON = new JSONObject(json);
	        status = respJSON.getString("status");
	        tiempo = respJSON.getString("tiempo");
	        imagen = respJSON.getString("imagen");
	        
	        if (Integer.parseInt(status) == 1 /*&& Long.parseLong(tiempo.replace('.', ',')) > 0*/){
	        	code.setTextColor(Color.RED);
	        	envia = false;
	        	CountDownTimer timer = new CountDownTimer(5/*Long.parseLong(tiempo)*/, 5/*Long.parseLong(tiempo)*/){
	        		public void onTick(long millisUntilFinished) {
		              // Do something every second	        			
	        		}
	        		@Override
	        		public void onFinish() {
	        			envia = true;
	        			code.setTextColor(Color.WHITE);
	        		}
	        	};
	        	timer.start();
	        	RetreiveFeedTask aus = new RetreiveFeedTask(c, imagen);
	        	aus.execute();
	        }
		} catch (JSONException e) {
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Codigo recibdo erroneo");
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
