package agaex.otrs.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import agaex.otrs.R;
import agaex.otrs.data.DataSQLite;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccount extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
     
        Bundle bundle = getIntent().getExtras();
        final Button btnLogin = (Button) findViewById(R.id.BtnLogin);
        final EditText title = (EditText) findViewById(R.id.title);
        final EditText login = (EditText) findViewById(R.id.user);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText url = (EditText) findViewById(R.id.url);
        
        final DataSQLite dbaccounts = new DataSQLite(this, "DBAccounts", null, 1);
        
        final String id_account = bundle.getString("Account_id");
        
        if (id_account != null){
        	btnLogin.setText("Update");
        	
        	SQLiteDatabase db = dbaccounts.getReadableDatabase();
        	String[] args = new String[] {id_account};
    		Cursor c = db.rawQuery(" SELECT id, login, password, url, title FROM Accounts WHERE id=? ", args);
    		
    		//Nos aseguramos que existe al menos un registro
    		if (c.moveToFirst()){
    			
    			do {
    				
    				login.setText(c.getString(1));
    				password.setText(c.getString(2));
    				url.setText(c.getString(3));
    				title.setText(c.getString(4));
    				
    			}while(c.moveToNext());
    			
    		}
    		
    		db.close();
        }
        else
        	btnLogin.setText("Add");

        btnLogin.setOnClickListener(new OnClickListener(){
    	
        	public void onClick(View v){
    		
        		Intent intent = new Intent(NewAccount.this, Accounts.class);
        		
        		if (title.getText().toString() == null ||	
        				login.getText().toString() == null || 
        				password.getText().toString() == null || 
        				url.getText().toString() == null){
        			Toast.makeText(NewAccount.this, "Valores incorrectos.", Toast.LENGTH_LONG).show();
        			return;
        		}
        		
        		//TODO Verificamos que la url está bien formada. Le antepondremos el http si no lo contiene.
                String url_aux = url.getText().toString();
                
                if (!url_aux.startsWith("http://"))
                	url_aux = "http://"+url_aux;
                if (!url_aux.endsWith("/"))
                	url_aux = url_aux+"/";
        		
                if (!check_connection(url_aux)){
                	Toast.makeText(NewAccount.this, "La url no es correcta.", Toast.LENGTH_LONG).show();
        			return;
                }
        		
        		//Abrimos la base de datos 'DBAccounts' en modo escritura
        		SQLiteDatabase db = dbaccounts.getWritableDatabase();
        		
        		if (db != null && id_account != null){
        		
        			ContentValues valores = new ContentValues();
        			valores.put("login", login.getText().toString());
        			valores.put("password", password.getText().toString());
        			valores.put("url", url_aux);
        			valores.put("title", title.getText().toString());
        			String[] args = new String[]{id_account};
        			db.update("Accounts", valores, "id=?", args);
        			
        		}else if(db != null){
        			
        			//Creamos el registro a insertar como objeto ContentValues
        			ContentValues nuevoRegistro = new ContentValues();
        			
        			nuevoRegistro.put("title", title.getText().toString());
        			nuevoRegistro.put("url", url_aux);
        			nuevoRegistro.put("login", login.getText().toString());
        			nuevoRegistro.put("password", password.getText().toString());
        			
        			db.insert("Accounts", null, nuevoRegistro);
        			
        		}
        		
        		db.close();
        		
        		startActivity(intent);
        	}

			private boolean check_connection(String url_aux) {
				
				boolean ret = true;
				// TODO Auto-generated method stub
				try {
				    URL url = new URL(url_aux+"json.pl");
				    URLConnection conn = url.openConnection();
				    conn.connect();
				} catch (MalformedURLException e) {
				    // the URL is not in a valid form
					ret = false;
				} catch (IOException e) {
				    // the connection couldn't be established
					ret = false;
				}
				return ret;
			}		
        });
	}
}