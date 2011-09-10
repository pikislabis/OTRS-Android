package agaex.otrs.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agaex.otrs.Otrs;
import agaex.otrs.R;
//import agaex.otrs.R.menu;
import agaex.otrs.json.JSON;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Queue_View extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_view);
        
        Bundle bundle = getIntent().getExtras();
        final String view_type = bundle.getString("VIEW_TYPE");
		
        String cadena_conexion = ((Otrs)getApplicationContext()).getAccount().getUrl();
        TextView txtViewType = (TextView) findViewById(R.id.viewType);
        
        if (view_type.equals("queue")){
        	cadena_conexion += "&Method=QueueView";
        	txtViewType.setText("Queues");
        }
        else if (view_type.equals("state")){
        	cadena_conexion += "&Method=StatusView";
        	txtViewType.setText("States");
        }	
        else if (view_type.equals("escalation")){
        	cadena_conexion += "&Method=EscalationView";
        	txtViewType.setText("Escalations");
        }
        
        JSON json = new JSON(cadena_conexion);
		String success = json.getResult();
		
		if (!success.equals("successful")){
			Toast.makeText(Queue_View.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
			return;
		}
			
        JSONArray data = json.getJson_array();
		
        final String[] colas = new String[data.length()];
        final String[] ids = new String[data.length()];
        final String[] nrotickets = new String[data.length()];
        final String[] nroticketsmsg = new String[data.length()];
        
		for (int i = 0; i < data.length(); i++){
			
			JSONObject queue = new JSONObject();
			
			try{
				queue = new JSONObject(data.get(i).toString());
				
				if (view_type.equals("queue")){
					colas[i] = queue.getString("QueueName");
					ids[i] = queue.getString("QueueID");
				}
				else if (view_type.equals("state") || view_type.equals("escalation")){
					colas[i] = queue.getString("StateType");
					ids[i] = queue.getString("FilterName");
				}
				
				nrotickets[i] = queue.getString("NumberOfTickets");
				nroticketsmsg[i] = queue.getString("NumberOfTicketsWithNewMessages");
				
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
				bundle.putString("VIEW_TYPE", view_type);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
			
		});
		
	}
/*	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//Alternativa 1
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	*/
}
