package agaex.otrs.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {

	private String subject;
	private String typeID;
	private String articleType;
	private String from;
	private String to;
	private String created;
	private String senderType;

	public Article(String subject, String typeID, String articleType,
				   String from, String to, String created,
				   String senderType){

		this.subject = subject;
		this.typeID = typeID;
		this.articleType = articleType;
		this.from = from;
		this.to = to;
		this.created = created;
		this.senderType = senderType;

	}

	public Article(JSONObject json) throws JSONException {
		deserializeFromObj(json);
	}

	public String getSubject() {
		return subject;
	}

	public String getTypeID() {
		return typeID;
	}

	public String getArticleType() {
		return articleType;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getCreated() {
		return created;
	}

	public String getSenderType() {
		return senderType;
	}

	private void deserializeFromObj (JSONObject json) throws JSONException {

		this.subject =  json.getString("Subject");
		this.typeID = json.getString("TypeID");
		this.articleType = json.getString("ArticleType");
		this.from = json.getString("From");
		this.to = json.getString("To");
		this.created = json.getString("Created");
		this.senderType = json.getString("SenderType");

	}

}
