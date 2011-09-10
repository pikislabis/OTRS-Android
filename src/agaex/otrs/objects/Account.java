package agaex.otrs.objects;

public class Account {

	private int id;
	private String title;
	private String url_account;
	private String url;
	private String login;
	private String password;
	
	public Account(int id, String title, String url, String login, String password) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.login = login;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getUrl() {
		return url;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl_account(){
		return url_account;
	}
	
	public void setUrl_account(String url_account) {
		this.url_account = url_account;
	}
	
}
