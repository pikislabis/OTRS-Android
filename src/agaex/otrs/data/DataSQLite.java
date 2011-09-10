package agaex.otrs.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSQLite extends SQLiteOpenHelper {

	String sqlCreate = "CREATE TABLE Accounts (id INTEGER PRIMARY KEY, title TEXT, url TEXT, login TEXT, password TEXT)";
	
	public DataSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Se elimina la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS Accounts");
		//Se crea la nueva versión de la tabla
		db.execSQL(sqlCreate);

	}

}
