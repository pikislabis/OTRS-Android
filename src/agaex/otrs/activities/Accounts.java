package agaex.otrs.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import agaex.otrs.Otrs;
import agaex.otrs.R;
import agaex.otrs.data.DataSQLite;
import agaex.otrs.json.JSON;
import agaex.otrs.objects.Account;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Accounts extends Activity {

	private Account[] accounts;
	private List<Account> account_list;
	private AdaptadorAccount adaptador;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.accounts);

		final DataSQLite dbaccounts = new DataSQLite(this, "DBAccounts", null, 1);

		ListView lstAccount = (ListView) findViewById(R.id.lstAccount);

		//Registramos el menu contextual.
		registerForContextMenu(lstAccount);

		//Abrimos la base de datos 'DBAccounts' en modo lectura
		SQLiteDatabase db = dbaccounts.getReadableDatabase();

		Cursor c = db.rawQuery(" SELECT id, login, password, url, title FROM Accounts ", null);

		//Nos aseguramos que existe al menos un registro
		if (c.moveToFirst()){

			accounts = new Account[c.getCount()];

			do {
				int id = c.getInt(0);
				String login = c.getString(1);
				String password = c.getString(2);
				String url_account = c.getString(3);
				String url = url_account + "json.pl?User=" + login + "&Password=" + password + "&Object=iPhoneObject&Method=QueueView";
				String title = c.getString(4);

				accounts[c.getPosition()] = new Account(id, title, url, login, password);
				accounts[c.getPosition()].setUrl_account(url_account);

			}while(c.moveToNext());

		}

		if (accounts == null || accounts.length == 0)
			return;

		account_list = new ArrayList<Account>(Arrays.asList(accounts));

		adaptador = new AdaptadorAccount(this);
		lstAccount.setAdapter(adaptador);
        //lstAccount.setSelector(Color.BLUE);

        lstAccount.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				Intent intent = new Intent(Accounts.this, Main.class);
        		JSON json = new JSON(account_list.get(position).getUrl());
        		String success = json.getResult();

        		if (success.equals("successful")){
        			Otrs appState = (Otrs)getApplicationContext();
        			Account account = new Account(account_list.get(position).getId(),
        					account_list.get(position).getTitle(),
        					account_list.get(position).getUrl(),
        					account_list.get(position).getLogin(),
        					account_list.get(position).getPassword());
                	account.setUrl_account(account_list.get(position).getUrl_account());
        			appState.setAccount(account);

        		}else{
        			Toast.makeText(Accounts.this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show();
        			Toast.makeText(Accounts.this, "Resultado: "+success, Toast.LENGTH_LONG).show();
        			return;
        		}

        		startActivity(intent);

			}

        });

	}

	@SuppressWarnings("rawtypes")
	class AdaptadorAccount extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		public AdaptadorAccount(Activity context) {
			super(context, R.layout.account_list, account_list);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent){

			LayoutInflater inflater = context.getLayoutInflater();
			View item = new View(context);

			item = inflater.inflate(R.layout.account_list, null);

			TextView title = (TextView) item.findViewById(R.id.accntTitle);
			title.setText(account_list.get(position).getTitle());

			TextView url = (TextView) item.findViewById(R.id.accntUrl);
			url.setText(account_list.get(position).getUrl_account());

			return item;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//Alternativa 1
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Mnu2Opc1:
				Intent intent = new Intent(Accounts.this, NewAccount.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_ctx_account, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Intent intent_edit = new Intent(Accounts.this, NewAccount.class);
		Bundle bundle = new Bundle();

		switch (item.getItemId()) {
			case R.id.item1:
				bundle.putString("Account_id", accounts[info.position].getId()+"");
				intent_edit.putExtras(bundle);
				startActivity(intent_edit);
				return true;
			case R.id.item2:
				int id = accounts[info.position].getId();
				delete_account(id);
				account_list.remove(info.position);
				adaptador.notifyDataSetChanged();
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	private void delete_account(int id) {
		final DataSQLite dbaccounts = new DataSQLite(this, "DBAccounts", null, 1);
		SQLiteDatabase db = dbaccounts.getWritableDatabase();

		try{
			String[] args = new String[]{id+""};
			db.execSQL("DELETE FROM Accounts WHERE id=?", args);
		}catch (Exception e) {
			Toast.makeText(Accounts.this, "Error ."+e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		db.close();
	}
}
