import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataCenter {
	private static DataCenter instance = null;
	ObjectOutputStream out;
	File userFile = new File("userList.txt");
	File bookFile = new File("bookList.txt");

	private ArrayList<User> users = new ArrayList<>();
	private Bookstore books = new Bookstore();

	private DataCenter() {
		users = new ArrayList<>();
	}

	public static DataCenter getInstance() {
		if (instance == null) {
			instance = new DataCenter();
		}

		return instance;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}

	public int userLogin(String username, String password) {
		User in = new User(username, password);
		for (User user : users) {
			if (user.equals(in)) {
				if (user instanceof Admin) {
					return 3;
				}
				return 1;
			}
		}

		for (User user : users) {
			if (user.getUser().compareTo(username) == 0) {
				return 2;
			}

		}

		return 0;
	}

	public boolean userSearch(String username) {
		for (User user : users) {
			if (user.getUser().compareTo(username) == 0) {
				return false;
			}
		}
		return true;
	}

	public void addUser(String username, String password, int i) {
		User user = null;
		if (i == 1) {
			user = new User(username, password);
		} else if (i == 2) {
			user = new Admin(username, password);
		}
		users.add(user);
		save(userFile, users);
	}
	public void saveUser() {
		 try (ObjectOutputStream oos = new ObjectOutputStream(
	                new FileOutputStream(userFile))) {
			oos.writeObject(users);
		}  catch (Exception e) {
			
		}
	}
	public void save(File file, Object o)  {
		 try (ObjectOutputStream oos = new ObjectOutputStream(
	                new FileOutputStream(file))) {
			oos.writeObject(o);
		}  catch (Exception e) {
			
		}
	}
	
	public ArrayList<Book> current(User user){
		return users.get(users.indexOf(user)).getCurr();
	}

	public boolean addNewBook(Book book) {
		if (books.addBook(book)) {
			books.addBook(book);
			save(bookFile, books);
			return true;
		}
		return false;
	}

	public boolean delBook(int isbn) {
		if (books.deleteBook(isbn)) {
			books.deleteBook(isbn);
			save(bookFile, books);
			return true;
		}
		return false;
	}
	
	public Bookstore getLibrary() {
		return books;
	}
	
	public void borBook(String u, Book b) {
		getUser(u).addBook(b);
		save(userFile, users);
	}
	
	public void retBook(String u, Book b) {
		getUser(u).returnBook(b);
		save(userFile, users);
	}
	
	public int getGenreCount(String gen) {
		if(gen.compareTo("fiction")==0) {
			return books.genreSearch("fiction").size();
		}
		if(gen.compareTo("non-fiction")==0) {
			return books.genreSearch("non-fiction").size();
		}
		if(gen.compareTo("mystery")==0) {
			return books.genreSearch("mystery").size();
		}
		if(gen.compareTo("drama")==0) {
			return books.genreSearch("drama").size();
		}
		if(gen.compareTo("sci-fi")==0) {
			return books.genreSearch("sci-fi").size();
		}
		return 0;
	}

	public ArrayList<Book> bookSearch(String word, int num) {
		switch (num) {

		case 1:
			return books.authorSearch(word);

		case 2:
			return books.genreSearch(word);

		case 3:
			return books.isbnSearch(Integer.parseInt(word));

		case 4:
			int x = Integer.parseInt(word.substring(0,word.indexOf('-')-1));
			int y = Integer.parseInt(word.substring(word.indexOf('-')+1));
			return books.dateSearch(x, y);

		case 5:
			int f = Integer.parseInt(word.substring(0,word.indexOf('-')-1));
			int g = Integer.parseInt(word.substring(word.indexOf('-')+1));
			return books.priceSearch(f, g);

		default:
			return books.search(word);
		}
	}
	
	public void loadIn() {
		try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(userFile))) {
            users = (ArrayList<User>) ois.readObject();
		}catch (Exception e) {
			
		}
		try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(bookFile))) {
            books = (Bookstore) ois.readObject();
		}catch (Exception e) {
			
		}
	}

	public void delete(int uid) {
		for (User user : users) {
			if (user.getUID() == uid) {
				users.remove(user);
			}
			save(userFile, users);
		}
	}
	
	public User getUser(String u) {
		int x = 0;
		for(User user : users) {
			if(user.getUser().compareTo(u)==0) {
				x =users.indexOf(user);
			}
		}
		return users.get(x);
	}
}