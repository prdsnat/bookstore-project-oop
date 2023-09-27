import java.io.Serializable;

public class Book implements Serializable {
	private String title;
	private String authors;
	private int isbn;
	private static int num = 11111;
	private String genre;
	private double price;
	private int date;
	private int copies;

	public Book(String title, String author, String genre, double price, int date) {
		this.title = title;
		this.authors = author;
		this.genre = genre;
		this.price = price;
		this.date = date;
		this.isbn = genISBN();
		copies = 1;
	}

	public int genISBN() {
		return num++;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return authors;
	}

	public int getISBN() {
		return isbn;
	}

	public String getGenre() {
		return this.genre;
	}

	public double getPrice() {
		return this.price;
	}

	public int getDate() {
		return this.date;
	}

	public int getCopies() {
		return this.copies;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addAuthor(String author) {
		authors += author;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void addCopy() {
		this.copies++;
	}

	public void deleteCopy() {
		this.copies--;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book x = (Book) obj;
		if (this.title.compareTo(x.getTitle()) == 0 && this.isbn == x.getISBN()) {
			return true;
		}
		return false;
	}

	public String toString(Book book) {
		return "title: " + book.getTitle() + " authors:" + book.getAuthor() + " genre: " + book.getGenre() + " price: "
				+ book.getPrice() + " date:" + book.getDate() + " copies: " + book.getCopies() + " isbn: "
				+ book.getISBN();

	}
}