package agaex.otrs.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agaex.otrs.Otrs;
import agaex.otrs.R;
import agaex.otrs.json.JSON;
import agaex.otrs.objects.Article;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Ticket extends Activity {

	private Article[] articles;
	private Article[] articles_aux;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);
     
        Bundle bundle = getIntent().getExtras();
		final String ticket_id = bundle.getString("TICKETID");
		
		String cadena_conexion = ((Otrs)getApplicationContext()).getAccount().getUrl();
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
		
		
		articles_aux = new Article[tickets_json.length()];
		
		int i = 0;
		int j = 0;
		
		while(j < tickets_json.length()){
			
			try{
				
				articles_aux[i] = new Article((JSONObject)tickets_json.get(j));
				
			}catch (JSONException e) {
				Toast.makeText(Ticket.this, "Ha ocurrido un error. "+e.getMessage(), Toast.LENGTH_LONG).show();
				return;
			}catch (ClassCastException e) {
				i--;
			}
			catch (Exception e) {
				Toast.makeText(Ticket.this, "Ha ocurrido un error. "+e.getMessage(), Toast.LENGTH_LONG).show();
				return;
			}
			
			i++;
			j++;
			
		}
		
		articles = new Article[i];
		java.lang.System.arraycopy(articles_aux, 0, articles, 0, i);
		
		TextView txtNoArticles = (TextView) findViewById(R.id.txtNoArticles);
		txtNoArticles.setText(articles.length+"");
		
		TextView txtStrArticles = (TextView) findViewById(R.id.txtStrArticles);
		
		if (articles.length > 1)
			txtStrArticles.setText(R.string.articles);
		else
			txtStrArticles.setText(R.string.article);
		
		AdaptadorArticle adaptador = new AdaptadorArticle(this);
		lstArticle.setAdapter(adaptador);
	}
	
	@SuppressWarnings("rawtypes")
	class AdaptadorArticle extends ArrayAdapter {
		Activity context;
		private int[] colors = new int[] {Color.WHITE, Color.GRAY};
		
		@SuppressWarnings("unchecked")
		public AdaptadorArticle(Activity context) {
			super(context, R.layout.article_list, articles);
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
			articleTypeID.setText((position + 1)+"");
			TextView articleSubject = (TextView) item.findViewById(R.id.articleSubject);
			articleSubject.setText(articles[position].getSubject());
			TextView articleCreated = (TextView) item.findViewById(R.id.articleCreated);
			articleCreated.setText(articles[position].getCreated());
			TextView articleFrom = (TextView) item.findViewById(R.id.articleFrom);
			articleFrom.setText(articles[position].getFrom());
			TextView articleTo = (TextView) item.findViewById(R.id.articleTo);
			articleTo.setText(articles[position].getTo());
			ImageView senderType = (ImageView) item.findViewById(R.id.articleSenderType);
			int resId = context.getResources().getIdentifier(articles[position].getSenderType(), "drawable", "android");
			senderType.setImageResource(resId);
			
			item.setBackgroundColor(colors[colorPos]);
			
			return item;
		}
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