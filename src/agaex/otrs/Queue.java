package agaex.otrs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import agaex.otrs.json.JSON;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Queue extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.queue);
		
		Bundle bundle = getIntent().getExtras();
		final String queue_id = bundle.getString("QUEUE");
		final String[] tickets;
		
		String cadena_conexion = ((Otrs)getApplicationContext()).getUrl();
		cadena_conexion += "&Method=QueueView&Data=%7B%22QueueID%22:"+queue_id+"%7D";
		
		TextView txtQueue = (TextView) findViewById(R.id.txtQueue);
		txtQueue.setText(bundle.getString("QUEUE_NAME"));
		
		ListView lstTicket = (ListView) findViewById(R.id.lstTicket);
		
		JSON json = new JSON(cadena_conexion);
		String success = json.getResult();
		
		final JSONArray tickets_json = json.getJson_array();
		
		if (success.equals("successful")){
			
			tickets = new String[tickets_json.length()];
            
	        for (int i = 0; i < tickets_json.length(); i++){
	        	try{
	        		tickets[i] = ((JSONObject)tickets_json.get(i)).getString("Subject").substring(1, 10);
	        	}catch (JSONException e) {
					e.printStackTrace();
				}
	        }
	        
	        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tickets);
	        lstTicket.setAdapter(adaptador);
			
		}
		else{
			Toast.makeText(Queue.this, "Ha ocurrido un error.", Toast.LENGTH_LONG).show();
			return;
		}    
		
		lstTicket.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
			
		});
		
		
	}
}