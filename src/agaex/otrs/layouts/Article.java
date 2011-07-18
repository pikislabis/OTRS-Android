package agaex.otrs.layouts;

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
	
}
