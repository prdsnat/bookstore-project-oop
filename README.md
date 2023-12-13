# bookstore-project-oop# 
Book Management System README

This Book Management System is a JavaFX application designed to provide an interface for both users and administrators for interactions related to books. 

---

## DataCenter:

### Overview:
The central hub for data storage, retrieval, and manipulation. 

### Functionalities:

1. **Singleton Design Pattern**: Ensures there is only one instance of the DataCenter throughout the application for consistent data access and management.
2. **User & Book Retrieval**: Can fetch specific users or books based on various parameters like ISBN, genre, etc.
3. **Add & Remove Books**: Provides functionalities to add new books or delete existing ones.
4. **Data Saving**: Has methods to save user data, presumably to a database or a file.

---

## Admin:

### Overview:
Handles the administrative side of the system.

### Functionalities:

1. **User Oversight**: Can view all registered users and their details.
2. **Book Management**: Responsible for adding and removing books in the system.
3. **Data Analytics**: Visual analytics based on book genres, publication dates, and price ranges using pie charts.
4. **Switch Mode**: Allows administrators to use the system as regular users.

---

## Bookstore:

### Overview:
Represents the store or library where the books are housed.

### Functionalities:

1. **Inventory Management**: Manages the list of all books available.
2. **Book Retrieval**: Allows fetching books based on different parameters.
3. **Genre, Date, Price Analysis**: Can calculate counts or other metrics based on genres, publication dates, or price ranges.

---

## Book:

### Overview:
This class encapsulates all the properties and behaviors of a book.

### Functionalities:

1. **Data Storage**: Holds information about a book, such as its title, author, genre, publication date, and price.
2. **Data Retrieval**: Allows fetching of various book attributes.
3. **String Representation**: Can provide a string representation of the book, useful for display or logging purposes.

---

## User:

### Overview:
Represents the end-users or members of the book management system.

### Functionalities:

1. **User Data**: Contains information about the user like username, borrowed books, etc.
2. **Book Interactions**: Provides methods for users to borrow and return books.
3. **User History**: Maintains a record of all books previously borrowed by the user.

---

## GUI:

### Overview:
The graphical user interface which serves as the front-end of the application.

### Functionalities:

1. **Login Screen**: A dedicated screen for users to register or sign in.
2. **Search Functionality**: Allows users to search for books based on different criteria.
3. **User Interface**: Displays currently borrowed books and offers options to borrow or return books.
4. **Admin Interface**: Provides admin-specific functionalities like accessing user data, book data, and other administrative controls.
5. **Data Visualization**: Offers visual representations like pie charts for genres, dates, and prices of books.
6. **Feedback & Alerts**: Shows pop-up alerts for various actions like successful book addition, errors, etc.

