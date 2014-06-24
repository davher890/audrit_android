package android.audirt;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.activity.RegistroActivity;
import com.clases.Usuario;

/**
 * Created by david on 17/06/13.
 */
public class AudirtGet extends AsyncTask<String, Context, String> {

    private ProgressDialog dialog;
    private RegistroActivity log = null;
    private String url;

    public AudirtGet(String url, RegistroActivity c){
        this.url = url;
        this.log = c;
    }
    
    protected String doInBackground(String...params) {    	
    	// Create a new HttpClient and Post Header
    	String result = null;
        
	    try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpGet httpget = new HttpGet(url);

	    	httpget.setHeader("Accept", "application/json");
	    	httpget.setHeader("Content-Type", "application/json");
	    	httpget.setHeader("Accept-Charset", "utf-8");
	       
	    	/*JSONObject json = new JSONObject();
	    	json.put("auth_token", Usuario.getToken());*/
	    	
	    	/*HttpParams parametros = httpget.getParams() ;
	    	parametros.setParameter(
			httpget.setParams(parametros);*/
			
	        HttpResponse response = httpclient.execute(httpget);
	        result = EntityUtils.toString(response.getEntity());
	
	    } catch (ClientProtocolException e) {
	    	e.printStackTrace();
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	e.printStackTrace();
	        // TODO Auto-generated catch block
	    } catch(Exception ex){  
	    	ex.printStackTrace();
        	// TODO Auto-generated catch block          
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(log);
        dialog.setTitle("Audirt");
        dialog.setMessage("Descargando Datos...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    protected void onPostExecute(String result) {
        dialog.cancel();
        if (result != null)
        	log.gestionaGet(result);       
    }
}
