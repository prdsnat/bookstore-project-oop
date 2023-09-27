import java.io.Serializable;
import java.util.ArrayList;
public class User implements Serializable{
	String user;
	String pass;
	int uid;
	private static int id = 0;
	ArrayList<Book> curr ;
	ArrayList<Book> hist ;
	
	public User(String user, String pass) {
		this.user= user;
		this.pass = pass;
		this.uid = genID();
		this.hist = new ArrayList<Book>();
		this.curr = new ArrayList<Book>();
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPass() {
		return this.pass;
	}
	
	public int getUID() {
		return this.uid;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public int genID() {
		return id++;
	}
	
	public ArrayList<Book> getCurr(){
		return this.curr;
	}
	
	public ArrayList<Book> getHist(){
		return this.hist;
	}
	
	public void addBook(Book book) {
		this.curr.add(book);
	}
	
	public void returnBook(Book book) {
		this.curr.remove(book);
		this.hist.add(book);
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		User x = (User) o;
		if(this.user.compareTo(x.getUser())==0 && this.pass.compareTo(x.getPass())==0){
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "username: "+this.getUser()+" password: "+this.getPass()+" UID: "+this.getUID();
	}
}
