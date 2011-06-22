package agaex.otrs.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

	private String cadena_conexion;
	private JSONObject json;
	private String result;
	private JSONArray json_array;

	public JSON(String cadena_conexion) {
		super();	
		this.cadena_conexion = cadena_conexion;
		
		HttpClient httpclient = new DefaultHttpClient();
		         
        try {
        	
        	HttpGet httpget = new HttpGet(this.cadena_conexion); 

            HttpResponse response;
        	
            response = httpclient.execute(httpget);
            
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
 
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                
                this.json = new JSONObject(result);
                
                this.result = json.getString("Result");
                this.json_array = json.getJSONArray("Data");
                
                instream.close();
               
            }
                
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
            this.result = "Fail: "+e.toString();
        } catch (IOException e) {
        	e.printStackTrace();
            this.result = "Fail: "+e.toString();
        } catch (JSONException e) {
        	e.printStackTrace();
            this.result = "Fail: "+e.toString();
        } catch (Exception e) {
			e.printStackTrace();
            this.result = "Fail: "+e.toString();
		}

	}
	
	public String getCadena_conexion() {
		return cadena_conexion;
	}

	public void setCadena_conexion(String cadena_conexion) {
		this.cadena_conexion = cadena_conexion;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public JSONArray getJson_array() {
		return json_array;
	}

	public void setJson_array(JSONArray json_array) {
		this.json_array = json_array;
	}

	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
}
