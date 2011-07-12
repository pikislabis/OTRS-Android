package agaex.otrs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agaex.otrs.json.JSON;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Queue_View extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);
        
        //Bundle bundle = getIntent().getExtras();
        JSONArray data = new JSONArray();
        
        String cadena_conexion = ((Otrs)getApplicationContext()).getUrl();
		cadena_conexion += "&Method=QueueView";
		
		JSON json = new JSON(cadena_conexion);
		String success = json.getResult();
		
		if (!success.equals("successful")){
			Toast.makeText(Queue_View.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
			return;
		}
			
        data = json.getJson_array();
		
		final String[] colas = new String[data.length()];
		final String[] ids = new String[data.length()];
		
		for (int i = 0; i < data.length(); i++){
			
			JSONObject queue = new JSONObject();
			
			try{
				
				queue = new JSONObject(data.get(i).toString());
				colas[i] = queue.getString("QueueName");
				ids[i] = queue.getString("QueueID");
				
				
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			
		}
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colas);
		
		ListView lstColas = (ListView)findViewById(R.id.LstColas);
		 
		lstColas.setAdapter(adaptador);
		
		lstColas.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent(Queue_View.this, Queue.class);
				Bundle bundle = new Bundle();
				
				bundle.putString("QUEUE", ids[arg2]);
				bundle.putString("QUEUE_NAME", colas[arg2]);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
			
		});
		
	}
	
}
