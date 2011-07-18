package agaex.otrs.layouts;

public class Ticket {

	private String subject;
	private String ticketNumber;
	private String from;
	private String age;
	private String state;
	private String queue;
	private String priority;
	private String priorityColor;
	private String ticketID;
	private String customer;
	private String responsible;
	private String owner;
	private String type;
	
	public Ticket(String subject, String ticketNumber, String from, 
				  String age, String state, String queue, 
				  String priority, String priorityColor, String ticketID,
				  String customer, String resposible, String owner,
				  String type){
		
		int ageInt;
		ageInt = Integer.parseInt(age);
		
		this.age = (ageInt/86400)+"d "+((ageInt%86400)/3600)+"h";
		this.subject = subject;
		this.ticketNumber = ticketNumber;
		this.from = from;
		this.state = state;
		this.queue = queue;
		this.priority = priority;
		this.priorityColor = priorityColor;
		this.ticketID = ticketID;
		this.customer = customer;
		this.responsible = resposible;
		this.owner = owner;
		this.type = type;
	}
	
	public String getSubject() {
		return subject;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public String getFrom() {
		return from;
	}
	public String getAge() {
		return age;
	}

	public String getState() {
		return state;
	}

	public String getQueue() {
		return queue;
	}

	public String getPriority() {
		return priority;
	}

	public String getPriorityColor() {
		return priorityColor;
	}

	public String getTicketID() {
		return ticketID;
	}

	public String getCustomer() {
		return customer;
	}

	public String getResponsible() {
		return responsible;
	}
	
	public String getOwner() {
		return owner;
	}

	public String getType() {
		return type;
	}
	
}
