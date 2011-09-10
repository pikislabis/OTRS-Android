package agaex.otrs;

import agaex.otrs.objects.Account;
import android.app.Application;

public class Otrs extends Application {

	private Account account;
	
	
	public void setAccount(Account account){
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	
}
