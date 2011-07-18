package agaex.otrs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String login = ((Otrs)getApplicationContext()).getLogin();
        TextView txtLogin = (TextView)findViewById(R.id.loginName);
        txtLogin.setText(login);
	}
	
	public void queue(View v){
		
		Intent intent = new Intent(Main.this, Queue_View.class);
		Bundle bundle = new Bundle();
		bundle.putString("VIEW_TYPE", "queue");
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void status(View v){
		Intent intent = new Intent(Main.this, Queue_View.class);
		Bundle bundle = new Bundle();
		bundle.putString("VIEW_TYPE", "state");
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void escallation (View v){
		Intent intent = new Intent(Main.this, Queue_View.class);
		Bundle bundle = new Bundle();
		bundle.putString("VIEW_TYPE", "escalation");
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
