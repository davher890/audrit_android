package android.audirt;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.activity.InscodeActivity;
import com.activity.LogIn;
import com.activity.PwdActivity;

/**
 * Created by david on 17/06/13.
 */
public class AudirtService extends AsyncTask<String, Context, String> {

    private ProgressDialog dialog;
    private LogIn log = null;
    private InscodeActivity ins = null;
    private PwdActivity pwd = null;
    private Context c = null;
    private String url;
    private JSONObject json;

    public AudirtService(String url, LogIn c, JSONObject json){
        this.url = url;
        this.json = json;
        this.log = c;
        this.c = (Context) c;
    }
    
    public AudirtService(String url, InscodeActivity c, JSONObject json){
        this.url = url;
        this.json = json;
        this.ins = c;
        this.c = (Context) c;
    }
    
    public AudirtService(String url, PwdActivity c, JSONObject json){
        this.url = url;
        this.json = json;
        this.pwd = c;
        this.c = (Context) c;
    }

    protected String doInBackground(String...params) {    	
    	// Create a new HttpClient and Post Header
    	String result = null;
        
	    try {
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppost = new HttpPost(url);

	    	httppost.setHeader("Accept", "application/json");
	    	httppost.setHeader("Content-Type", "application/json");
	    	httppost.setHeader("Accept-Charset", "utf-8");
	    	
	    	StringEntity se = new StringEntity(json.toString());
	        
	        httppost.setEntity(se);
	       
	        HttpResponse response = httpclient.execute(httppost);
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
        dialog.setMessage("Descargando Datos...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    protected void onPostExecute(String result) {
        dialog.cancel();
        System.out.println(result);
        
        if (log != null)
        	log.gestionaWS(result);
        
        if (ins != null)
        	ins.introduceBBDD(result);       
    }
}
