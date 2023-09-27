import java.io.Serializable;
import java.util.ArrayList;

public class Bookstore implements Serializable {
	private ArrayList<Book> books = new ArrayList<Book>();

	public boolean addBook(Book book) {
		if (this.books.isEmpty()) {
			books.add(book);
		}
		for (Book x : books) {
			if (x.equals(book)) {
				x.addCopy();
				return true;
			}
		}
		books.add(book);
		return true;
	}
	
	public ArrayList<Book> getAll(){
		return books;
	}

	public ArrayList<Book> search(String word) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getTitle().contains(word)) {
				list.add(x);
			}
		}
		return list;
	}

	public ArrayList<Book> authorSearch(String name) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getAuthor().contains(name)) {
				list.add(x);
			}
		}
		return list;
	}

	public ArrayList<Book> isbnSearch(int isbn) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getISBN() == isbn) {
				list.add(x);
			}
		}
		return list;
	}

	public ArrayList<Book> genreSearch(String genre) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getGenre().compareToIgnoreCase(genre) == 0) {
				list.add(x);
			}
		}
		return list;
	}

	public ArrayList<Book> dateSearch(int d1, int d2) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getDate() >= d1 | x.getDate() <= d2) {
				list.add(x);
			}
		}
		return list;
	}

	public ArrayList<Book> priceSearch(double p1, double p2) {
		ArrayList<Book> list = new ArrayList<Book>();
		for (Book x : books) {
			if (x.getPrice() >= p1 || x.getPrice() <= p2) {
				list.add(x);
			}
		}
		return list;
	}

	public boolean deleteBook(int isbn) {
		for (Book x : books) {
			if (x.getISBN() == isbn) {
				if (x.getISBN() < 1) {
					x.deleteCopy();
					return true;
				}
				books.remove(books.indexOf(x));
				return true;
			}

		}
		return false;
	}

	public void listBooks() {
		for (Book x : books) {
			x.toString();
		}
	}

}