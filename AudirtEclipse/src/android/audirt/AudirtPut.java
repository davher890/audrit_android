package android.audirt;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by david on 17/06/13.
 */
public class AudirtPut extends AsyncTask<String, Context, String> {

    private ProgressDialog dialog;
    private Context c = null;
    private String url;
    private JSONObject json;
    
    public AudirtPut(String url, Context c, JSONObject json){
        this.url = url;
        this.json = json;
        this.c = c;
    }

    protected String doInBackground(String...params) {    	
    	// Create a new HttpClient and Post Header
    	String result = null;
        
	    try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpPut httpput = new HttpPut(url);

	    	httpput.setHeader("Accept", "application/json");
	    	httpput.setHeader("Content-Type", "application/json");
	    	httpput.setHeader("Accept-Charset", "utf-8");
	    	
	    	StringEntity se = new StringEntity(json.toString());
	    	httpput.setEntity(se);
	    	
	        HttpResponse response = httpclient.execute(httpput);
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
        dialog = new ProgressDialog(c);
        dialog.setTitle("Audirt");
        dialog.setMessage("Enviando Datos...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    protected void onPostExecute(String result) {
        dialog.cancel();
        if (result != null)
        	System.out.println(result);        
    }
}
