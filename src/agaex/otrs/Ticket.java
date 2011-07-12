package agaex.otrs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agaex.otrs.json.JSON;
import agaex.otrs.layouts.Article;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Ticket extends Activity {

	private Article[] articles;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);
     
        Bundle bundle = getIntent().getExtras();
		final String ticket_id = bundle.getString("TICKETID");
		
		String cadena_conexion = ((Otrs)getApplicationContext()).getUrl();
		cadena_conexion += "&Method=ArticleGet&Data=%7B%22TicketID%22:"+ticket_id+"%7D";
		
		ListView lstArticle = (ListView) findViewById(R.id.lstArticle);
		
		JSON json = new JSON(cadena_conexion);
		String success = json.getResult();
		
		final JSONArray tickets_json = json.getJson_array();
		
		if (!success.equals("successful")){
		
			Toast.makeText(Ticket.this, "Ha ocurrido un error.", Toast.LENGTH_LONG).show();
			return;
			
		}
		
		TextView ticketNumber = (TextView) findViewById(R.id.txtTicketNum);
		ticketNumber.setText(bundle.getString("TICKETNUMBER"));
		TextView ticketSubject = (TextView) findViewById(R.id.ticketSubject);
		ticketSubject.setText(bundle.getString("TICKETSUBJECT"));
		TextView ticketAge = (TextView) findViewById(R.id.ticketAge);
		ticketAge.setText(bundle.getString("TICKETAGE"));
		TextView ticketType = (TextView) findViewById(R.id.ticketType);
		ticketType.setText(bundle.getString("TICKETTYPE"));	
		TextView ticketState = (TextView) findViewById(R.id.ticketState);
		ticketState.setText(bundle.getString("TICKETSTATE"));
		TextView ticketPriority = (TextView) findViewById(R.id.ticketPriority);
		ticketPriority.setText(bundle.getString("TICKETPRIORITY"));
		TextView ticketQueue = (TextView) findViewById(R.id.ticketQueue);
		ticketQueue.setText(bundle.getString("TICKETQUEUE"));
		TextView ticketCustomer = (TextView) findViewById(R.id.ticketCustomer);
		ticketCustomer.setText(bundle.getString("TICKETCUSTOMER"));
		TextView ticketOwner = (TextView) findViewById(R.id.ticketOwner);
		ticketOwner.setText(bundle.getString("TICKETOWNER"));
		TextView ticketResponsible = (TextView) findViewById(R.id.ticketResponsible);
		ticketResponsible.setText(bundle.getString("TICKETRESPONSIBLE"));
		
		articles = new Article[tickets_json.length() - 1];
		
		for (int i = 1; i < tickets_json.length(); i++){
		
			try{
				articles[i-1] = new Article(((JSONObject)tickets_json.get(i)).getString("Subject"),
										  ((JSONObject)tickets_json.get(i)).getString("TypeID"), 
										  ((JSONObject)tickets_json.get(i)).getString("ArticleType"), 
										  ((JSONObject)tickets_json.get(i)).getString("From"),
										  ((JSONObject)tickets_json.get(i)).getString("To"), 
										  ((JSONObject)tickets_json.get(i)).getString("Created"));
			}catch (JSONException e) {
				Toast.makeText(Ticket.this, "Ha ocurrido un error. "+e.getMessage(), Toast.LENGTH_LONG).show();
				return;
			}
		}
		
		AdaptadorArticle adaptador = new AdaptadorArticle(this);
		lstArticle.setAdapter(adaptador);
	}
	
	class AdaptadorArticle extends ArrayAdapter {
		Activity context;
		private int[] colors = new int[] {Color.WHITE, Color.GRAY};
		
		public AdaptadorArticle(Activity context) {
			super(context, R.layout.ticket_list, articles);
			this.context = context;
		}
		
		public View getView(int position, View convertView, ViewGroup parent){
			
			LayoutInflater inflater = context.getLayoutInflater();
			View item = new View(context);
			try{
				item = inflater.inflate(R.layout.article_list, null);
			}catch (Exception e) {
				e.printStackTrace();
			}
			int colorPos = position % colors.length;
			
			TextView articleTypeID = (TextView) item.findViewById(R.id.articleTypeID);
			articleTypeID.setText(articles[position].getTypeID());
			TextView articleSubject = (TextView) item.findViewById(R.id.articleSubject);
			articleSubject.setText(articles[position].getSubject());
			TextView articleCreated = (TextView) item.findViewById(R.id.articleCreated);
			articleCreated.setText(articles[position].getCreated());
			TextView articleFrom = (TextView) item.findViewById(R.id.articleFrom);
			articleFrom.setText(articles[position].getFrom());
			TextView articleTo = (TextView) item.findViewById(R.id.articleTo);
			articleTo.setText(articles[position].getTo());
			
			item.setBackgroundColor(colors[colorPos]);
			
			return item;
		}
	}
	
}