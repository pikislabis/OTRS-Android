package agaex.otrs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bundle bundle = getIntent().getExtras();
        JSONArray data = new JSONArray();
        
        
        try {
			data = new JSONArray(bundle.getString("DATA"));
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
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
				
				Intent intent = new Intent(Main.this, Queue.class);
				Bundle bundle = new Bundle();
				
				bundle.putString("QUEUE", ids[arg2]);
				bundle.putString("QUEUE_NAME", colas[arg2]);
				
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
			
		});
		
	}
	
}
