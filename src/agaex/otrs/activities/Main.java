package agaex.otrs.activities;

import agaex.otrs.Otrs;
import agaex.otrs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Main extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String login = ((Otrs)getApplicationContext()).getAccount().getLogin();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//Alternativa 1
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_1, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Mnu1Opc1:
				Intent intent = new Intent(Main.this, Accounts.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
