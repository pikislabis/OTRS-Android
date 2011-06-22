package agaex.otrs;

import agaex.otrs.json.JSON;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        final Button btnLogin = (Button) findViewById(R.id.BtnLogin);
        final EditText login = (EditText) findViewById(R.id.user);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText url = (EditText) findViewById(R.id.url);
        
        btnLogin.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View v){
        		
        		Intent intent = new Intent(Login.this, Main.class);
				Bundle bundle = new Bundle();
        		
        		String cadena_conexion = "http://"+url.getText().toString()+"/json.pl";
        		cadena_conexion += "?User="+login.getText().toString()+"&Password="+password.getText().toString();
        		cadena_conexion += "&Object=iPhoneObject&Method=QueueView";
        		
        		JSON json = new JSON(cadena_conexion);
        		String success = json.getResult();
        		
        		if (success.equals("successful")){
        			Otrs appState = (Otrs)getApplicationContext();
                	appState.setLogin(login.getText().toString());
                	appState.setPassword(password.getText().toString());
                	appState.setUrl("http://"+url.getText().toString()+"/json.pl?User="+login.getText().toString()+"&Password="+password.getText().toString()+"&Object=iPhoneObject");
                	
                	String data = json.getJson_array().toString();
                	bundle.putString("DATA", data);
    				intent.putExtras(bundle);
    				startActivity(intent);
                	
        		}else
        			Toast.makeText(Login.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        	}
        	
        });
    }
}