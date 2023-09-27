import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.CornerRadii;

public class GUIProj extends Application {
	Stage regStage;
	Stage adminStage;
	Stage userStage = new Stage();
	Scene adminScene = null;
	Scene bookManScene;
	Scene userScene = null;
	Button back = new Button("back");
	TextField titleF;
	TextField authF;
	TextField isbnF;
	TextField genrF;
	TextField priceF;
	TextField dateF;
	User currU;

	@Override
	public void start(Stage stage) {
		try {
			DataCenter.getInstance().loadIn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pane root = loginLayout();
		Scene login = new Scene(root, 600, 500);
		stage.setScene(login);
		stage.setTitle("Kuromi and MyMelody's Bookstore");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Pane loginLayout() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		Label wel = new Label("welcome ! !");
		Label userQ = new Label("please enter username: ");
		Label passQ = new Label("please enter password: ");

		Image i1 = new Image(
				"https://th.bing.com/th/id/R.b54917f05617905b4429d9f0b3693f2b?rik=fNkCjs50o0Z3Pg&pid=ImgRaw&r=0");
		ImageView im = new ImageView(i1);

		Image i2 = new Image("https://i.pinimg.com/originals/a9/e9/8d/a9e98d0d97c438a82f6c2e43b7dd69aa.png");
		ImageView im1 = new ImageView(i2);

		im.setFitWidth(100);
		im.setFitHeight(100);

		im1.setFitWidth(80);
		im1.setFitHeight(95);

		wel.setFont(Font.font("Helvetica", 30));
		userQ.setFont(Font.font("Helvetica", 13));
		passQ.setFont(Font.font("Helvetica", 13));

		Button ent = new Button("enter!");
		Button reg = new Button("register");
		TextField userField = new TextField();
		TextField passField = new TextField();

		ent.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		reg.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		ent.setFont(Font.font("Helvetica", 13));
		reg.setFont(Font.font("Helvetica", 13));

		ent.setDisable(true);
		reg.setDisable(true);

		userField.setPrefHeight(10);
		userField.setPrefWidth(100);
		passField.setPrefHeight(10);
		passField.setPrefWidth(100);

		pane.add(im, 0, 0);
		pane.add(im1, 2, 0);
		pane.add(wel, 1, 0);
		pane.add(userQ, 1, 1);
		pane.add(userField, 1, 2);
		pane.add(ent, 1, 5);
		pane.add(passQ, 1, 3);
		pane.add(passField, 1, 4);
		pane.add(reg, 2, 5);

		passField.setOnKeyTyped(e -> {
			String password = passField.getText();
			String username = userField.getText();
			boolean val = loginVal(username, password);
			ent.setDisable(!val);
		});

		ent.setOnAction(e -> {
			int code = DataCenter.getInstance().userLogin(userField.getText(), passField.getText());
			if (code == 0) {
				String err = "error code is " + code + "! username and password are not found in our records!";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
				reg.setDisable(false);
				userField.clear();
				passField.clear();
			} else if (code == 2) {
				String err = "error code is " + code + "! wrong password! please try again !";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
				passField.clear();
				reg.setDisable(true);
			} else if (code == 1 || code == 3) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("login successful !!");
				alert.showAndWait();
				currU = DataCenter.getInstance().getUser(userField.getText());
				if (code == 3) {
					adminStage = new Stage();
					Pane root = adminHome();
					adminScene = new Scene(root, 600, 500);
					adminStage.setScene(adminScene);
					adminStage.setTitle("Admin Home");
					adminStage.show();
				} else {
					Pane root = userHome();
					userScene = new Scene(root, 600, 500);
					userStage.setScene(userScene);
					userStage.setTitle("User Home");
					userStage.show();
				}
			}
		});

		reg.setOnAction(e -> {
			regStage = new Stage();
			Pane root = registerPage();
			Scene regScene = new Scene(root, 600, 500);
			regStage.setScene(regScene);
			regStage.setTitle("Register Screen");
			regStage.show();
		});

		return pane;
	}

	public Pane registerPage() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		Label regT = new Label("register below");
		Label userQ = new Label("please enter username: ");
		Label passQ = new Label("please enter password: ");
		Label passQ2 = new Label("confirm password: ");
		Label adminCode = new Label("enter admin code: ");
		TextField userField = new TextField();
		TextField passField = new TextField();
		TextField passVal = new TextField();
		TextField adminField = new TextField();
		Button ent = new Button("enter");
		ent.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		regT.setFont(Font.font("Helvetica", 13));
		userQ.setFont(Font.font("Helvetica", 13));
		passQ.setFont(Font.font("Helvetica", 13));
		passQ2.setFont(Font.font("Helvetica", 13));
		adminCode.setFont(Font.font("Helvetica", 13));
		ent.setFont(Font.font("Helvetica", 13));

		pane.add(regT, 1, 0);
		pane.add(userQ, 1, 1);
		pane.add(userField, 1, 2);
		pane.add(ent, 1, 9);
		pane.add(passQ, 1, 3);
		pane.add(passField, 1, 4);
		pane.add(passQ2, 1, 5);
		pane.add(passVal, 1, 6);
		pane.add(adminCode, 1, 7);
		pane.add(adminField, 1, 8);

		ent.setDisable(true);
		userField.setPrefHeight(10);
		userField.setPrefWidth(100);
		passField.setPrefHeight(10);
		passField.setPrefWidth(100);
		passVal.setPrefHeight(10);
		passVal.setPrefWidth(100);
		adminField.setPrefHeight(10);
		adminField.setPrefWidth(100);

		userField.setOnMouseExited(e -> {
			boolean b = DataCenter.getInstance().userSearch(userField.getText());
			if (!b) {
				String err = "this username is taken !" + randUserGen(userField.getText());
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
				userField.clear();
			}
		});

		passVal.setOnMouseExited(e -> {
			if (passVal.getText().compareTo(passField.getText()) != 0) {
				String err = "passwords do not match !";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
				passField.clear();
				passVal.clear();
			} else {
				ent.setDisable(false);
			}
		});

		ent.setOnAction(e -> {
			boolean b = loginVal(userField.getText(), passField.getText());
			String key = adminField.getText();

			if (b) {
				if (key.compareTo("S13QQ27J7") == 0) {
					DataCenter.getInstance().addUser(userField.getText(), passField.getText(), 2);
				} else {
					DataCenter.getInstance().addUser(userField.getText(), passField.getText(), 1);
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("registered successfully!");
				alert.showAndWait();
				regStage.close();
			} else {
				String err = "username must be longer than 6 characters and password must be longer than 9 characters!";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
				userField.clear();
				passField.clear();
				passVal.clear();
				ent.setDisable(true);
			}
		});

		return pane;
	}

	public Pane userHome() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		String[] opts = { "author", "isbn", "genre", "date", "price", "none" };
		ObservableList<String> b = FXCollections.observableArrayList();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		TextField bar = new TextField();
		ComboBox<String> box = new ComboBox<>(FXCollections.observableArrayList(opts));
		ArrayList<Book> bookList = DataCenter.getInstance().getLibrary().getAll();
		TableView<Book> diss = tView(bookList);
		Button search = new Button("search");
		Button mBooks = new Button("my books !");
		Button bor = new Button("borrow");
		Button ret = new Button("return!");
		Button out = new Button("log out!");

		search.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		mBooks.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		bor.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		ret.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		out.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		search.setFont(Font.font("Helvetica", 13));
		mBooks.setFont(Font.font("Helvetica", 13));
		bor.setFont(Font.font("Helvetica", 13));
		ret.setFont(Font.font("Helvetica", 13));
		out.setFont(Font.font("Helvetica", 13));

		box.setPromptText("filters");
		search.setDisable(true);
		bor.setDisable(true);
		ret.setDisable(true);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.setSpacing(10);
		hbox.getChildren().addAll(bor, ret, mBooks, out);

		pane.add(bar, 0, 0);
		pane.add(box, 1, 0);
		pane.add(search, 2, 0);
		pane.add(hbox, 0, 3);
		pane.add(diss, 0, 1);

		out.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("log out successful");
			alert.showAndWait();
			userStage.close();
		});

		ObservableList<String> boos = FXCollections.observableArrayList();
		ObservableList<Book> def = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		String str = "";
		for (Book bo : def) {
			str += bo.toString() + "\n";
			boos.add(str);
		}

		bar.setOnKeyTyped(e -> {
			search.setDisable(false);
		});

		box.setOnAction(e -> {
			String sel = box.getSelectionModel().getSelectedItem();
			if (sel.compareTo("date") == 0) {
				bar.setPromptText("enter date range format: YYYYMMDD-YYYYMMDD");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 4));
					diss.setItems(bookL);

				});
			} else if (sel.compareTo("price") == 0) {
				bar.setPromptText("enter price range format: XX.XX-XX.XX");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 5));
					diss.setItems(bookL);
				});
			} else if (sel.compareTo("author") == 0) {
				bar.setPromptText("enter name here");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 1));
					diss.setItems(bookL);
				});
			} else if (sel.compareTo("genre") == 0) {
				bar.setPromptText("enter genre here");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 2));
					diss.setItems(bookL);
				});
			} else if (sel.compareTo("isbn") == 0) {
				bar.setPromptText("enter isbn here");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 3));
					diss.setItems(bookL);
				});
			} else if (sel.compareTo("none") == 0) {
				bar.setPromptText("enter title here");
				search.setOnAction(e1 -> {
					ObservableList<Book> bookL = FXCollections
							.observableArrayList(DataCenter.getInstance().bookSearch(bar.getText(), 0));
					diss.setItems(bookL);
				});
			}
		});

		mBooks.setOnAction(e -> {
			ObservableList<Book> bookL = FXCollections.observableArrayList(DataCenter.getInstance().current(currU));
			diss.setItems(bookL);
			ret.setDisable(false);
		});

		diss.setOnMouseClicked(e -> {
			bor.setDisable(false);
		});

		ret.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("would you like to return this book?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				Book k = (Book) diss.getSelectionModel().getSelectedItem();
				DataCenter.getInstance().getUser(currU.getUser()).returnBook(k);
				DataCenter.getInstance().saveUser();
			}
		});

		bor.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("would you like to borrow this book?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				Book k = (Book) diss.getSelectionModel().getSelectedItem();
				DataCenter.getInstance().getUser(currU.getUser()).addBook(k);
				DataCenter.getInstance().saveUser();
			}

		});
		return pane;
	}

	public Pane adminHome() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		Image i1 = new Image(
				"https://th.bing.com/th/id/R.1b0b081bc088f358b6447dd7f80574fd?rik=lNp7jp2xakn3Iw&riu=http%3a%2f%2fblog.unidyna.com%2fwp-content%2fuploads%2f2014%2f02%2fLOGO.png&ehk=gu89qFEYpK1QeQCJbn7cqA%2bhIQ1T6FBBVlW9d11xtEI%3d&risl=&pid=ImgRaw&r=0");
		ImageView im = new ImageView(i1);

		im.setFitWidth(150);
		im.setFitHeight(150);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);

		Label wel = new Label("welcome admin !");
		Button userInf = new Button("user informantion");
		Button bookInf = new Button("book information");
		Button bookMan = new Button("book management");
		Button nonAd = new Button("enter user mode");
		Button out = new Button("log out");

		userInf.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		bookInf.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		bookMan.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		nonAd.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		out.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		wel.setFont(Font.font("Helvetica", 13));
		userInf.setFont(Font.font("Helvetica", 13));
		bookInf.setFont(Font.font("Helvetica", 13));
		bookMan.setFont(Font.font("Helvetica", 13));
		nonAd.setFont(Font.font("Helvetica", 13));
		out.setFont(Font.font("Helvetica", 13));

		vbox.getChildren().addAll(im, wel, userInf, bookInf, bookMan, nonAd, out);
		pane.add(vbox, 0, 2, 2, 1);

		userInf.setOnAction(e -> {
			SplitPane root = null;
			try {
				root = uInfPage();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Scene uInfScene = new Scene(root, 600, 500);
			adminStage.setScene(uInfScene);
		});

		bookInf.setOnAction(e -> {
			SplitPane root = bookInfPage();
			Scene bookInfScene = new Scene(root, 600, 500);
			adminStage.setScene(bookInfScene);
		});

		bookMan.setOnAction(e -> {
			Pane root = bookManPage();
			bookManScene = new Scene(root, 600, 500);
			adminStage.setScene(bookManScene);
		});

		nonAd.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("are you sure you want to switch to user mode?");
			alert.showAndWait();
			adminStage.close();
			Pane root = userHome();
			userScene = new Scene(root, 600, 500);
			userStage.setScene(userScene);
			userStage.show();
		});

		out.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("log out successful");
			alert.showAndWait();
			adminStage.close();
		});
		return pane;
	}

	public SplitPane uInfPage() throws FileNotFoundException, IOException {
		SplitPane sPane = new SplitPane();
		GridPane pane = new GridPane();
		GridPane pane2 = new GridPane();
		sPane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);
		pane2.setAlignment(Pos.CENTER);
		pane2.setPadding(new Insets(50));
		pane2.setHgap(6);
		pane2.setVgap(10);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);

		TextField bar = new TextField();
		TextArea bookList = new TextArea();
		Button search = new Button("search");
		search.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		search.setFont(Font.font("Helvetica", 13));

		bookList.setEditable(false);
		search.setDisable(true);
		bar.setPromptText("type in username");
		vbox.getChildren().addAll(bar, search, bookList);
		pane2.add(vbox, 0, 0);
		bar.setOnKeyTyped(e -> {
			search.setDisable(false);
		});

		search.setOnAction(e -> {
			ObservableList<User> userNames = FXCollections.observableArrayList(DataCenter.getInstance().getUsers());
			for (User user : userNames) {
				if (user.getUser().compareTo(bar.getText()) == 0) {
					ObservableList<Book> books = FXCollections.observableArrayList();
					books.addAll(user.getCurr());
					books.addAll(user.getHist());
					String str = "";
					for (Book book : books) {
						str += book.toString() + "\n";
					}
					bookList.setText(str);

				}
			}
		});

		TextArea users = new TextArea();
		users.setEditable(false);
		users.setPrefWidth(500);
		users.setPrefHeight(300);

		ObservableList<User> userDis = FXCollections.observableArrayList(DataCenter.getInstance().getUsers());
		String str = "";
		for (User user : userDis) {
			str += user.toString() + "\n";
		}
		users.setText(str);

		pane.add(users, 0, 0);
		pane2.add(back, 3, 2);
		;
		sPane.getItems().add(pane);
		sPane.getItems().add(pane2);
		back.setAlignment(Pos.BOTTOM_RIGHT);

		back.setOnAction(e -> {
			adminStage.setScene(adminScene);
		});

		return sPane;
	}

	public Pane bookManPage() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		Label man = new Label("book management!");
		Button add = new Button("add book");
		Button rem = new Button("remove book");

		add.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
		rem.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		man.setFont(Font.font("Helvetica", 13));
		add.setFont(Font.font("Helvetica", 13));
		rem.setFont(Font.font("Helvetica", 13));

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);

		vbox.getChildren().addAll(man, add, rem, back);
		pane.add(vbox, 0, 2, 2, 1);

		back.setOnAction(e -> {
			adminStage.setScene(adminScene);
		});

		add.setOnAction(e -> {
			Pane root = bookAddPage();
			Scene bookAddScene = new Scene(root, 600, 500);
			adminStage.setScene(bookAddScene);
		});

		rem.setOnAction(e -> {
			Pane root = bookRemPage();
			Scene bookRemScene = new Scene(root, 600, 500);
			adminStage.setScene(bookRemScene);
		});

		return pane;
	}

	public SplitPane bookInfPage() {
		ObservableList<Book> lib = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		SplitPane sPane = new SplitPane();
		GridPane pane = new GridPane();
		TabPane p2 = new TabPane();
		sPane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);
		pane.add(back, 0, 3);

		Tab genCh = new Tab();
		Tab datCh = new Tab();
		Tab priCh = new Tab();

		genCh.setContent(genreChart());
		genCh.setText("genres");
		datCh.setContent(dateChart());
		datCh.setText("dates");
		priCh.setContent(priceChart());
		priCh.setText("prices");

		p2.getTabs().addAll(genCh, datCh, priCh);

		back.setOnAction(e -> {
			adminStage.setScene(adminScene);
		});

		TableView<Book> tv = new TableView<Book>();
		TableColumn<Book, String> col1 = new TableColumn<>("title");
		col1.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Book, String> col2 = new TableColumn<>("author");
		col2.setCellValueFactory(new PropertyValueFactory<>("author"));

		TableColumn<Book, String> col3 = new TableColumn<>("genre");
		col3.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn<Book, Integer> col4 = new TableColumn<>("copies");
		col4.setCellValueFactory(new PropertyValueFactory<>("copies"));

		TableColumn<Book, Integer> col5 = new TableColumn<>("ISBN");
		col5.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

		TableColumn<Book, Integer> col6 = new TableColumn<>("price");
		col6.setCellValueFactory(new PropertyValueFactory<>("price"));

		tv.getColumns().addAll(col1, col2, col3, col4, col5, col6);
		pane.add(tv, 0, 0);
		lib = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		sPane.getItems().addAll(pane, p2);
		tv.setItems(lib);

		return sPane;
	}

	public PieChart genreChart() {
		ArrayList<String> g = new ArrayList<String>();
		ObservableList<Book> list = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		ObservableList<PieChart.Data> dList = FXCollections.observableArrayList();
		for (Book k : list) {
			if (g.isEmpty() || !g.contains(k.getGenre())) {
				g.add(k.getGenre());
				dList.add(new Data(k.getGenre(), DataCenter.getInstance().getGenreCount(k.getGenre())));
			}
		}
		PieChart genPie = new PieChart(dList);
		genPie.setTitle("genres");
		return genPie;
	}

	public PieChart dateChart() {
		ArrayList<Integer> d = new ArrayList<Integer>();
		ObservableList<Book> list = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		ObservableList<PieChart.Data> dList = FXCollections.observableArrayList();
		int num = 0;
		for (Book k : list) {
			num = Integer.parseInt((Integer.toString(k.getDate()).substring(0, 4)));
			if (d.isEmpty() || !d.contains(num)) {
				d.add(num);
				dList.add(new Data("" + num, num));
			}
		}
		PieChart datePie = new PieChart(dList);
		datePie.setTitle("genres");
		return datePie;
	}

	public PieChart priceChart() {

		ObservableList<Book> list = FXCollections.observableArrayList(DataCenter.getInstance().getLibrary().getAll());
		ObservableList<PieChart.Data> dList = FXCollections.observableArrayList();
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		for (Book k : list) {
			if (k.getPrice() > 0 && k.getPrice() <= 10) {
				a++;
			} else if (k.getPrice() > 10 && k.getPrice() <= 25) {
				b++;
			} else if (k.getPrice() > 25 && k.getPrice() <= 50) {
				c++;
			} else if (k.getPrice() > 50 && k.getPrice() <= 75) {
				d++;
			} else if (k.getPrice() > 75 && k.getPrice() <= 100) {
				e++;
			} else if (k.getPrice() > 100) {
				f++;
			}
		}
		dList.add(new Data("<= $10", a));
		dList.add(new Data("$11 - $25", b));
		dList.add(new Data("$26 - $50", c));
		dList.add(new Data("$51 - $75", d));
		dList.add(new Data("$76 - $100", e));
		dList.add(new Data("< $100", f));

		PieChart pricePie = new PieChart(dList);
		pricePie.setTitle("genres");
		return pricePie;
	}

	public Pane bookAddPage() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		Label title = new Label("title: ");
		titleF = new TextField();
		Label author = new Label("author: ");
		authF = new TextField();
		Label genre = new Label("genre: ");
		genrF = new TextField();
		Label price = new Label("price: ");
		priceF = new TextField();
		Label date = new Label("date: ");
		dateF = new TextField();
		Button add = new Button("add !");

		add.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		titleF.setFont(Font.font("Helvetica", 13));
		authF.setFont(Font.font("Helvetica", 13));
		genrF.setFont(Font.font("Helvetica", 13));
		priceF.setFont(Font.font("Helvetica", 13));
		dateF.setFont(Font.font("Helvetica", 13));

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);

		vbox.getChildren().addAll(title, titleF, author, authF, genre, genrF, price, priceF, date, dateF, add, back);
		pane.add(vbox, 0, 2, 2, 1);
		add.setDisable(false);

		back.setOnAction(e -> {
			adminStage.setScene(bookManScene);
		});

		add.setOnAction(e -> {
			if (!DataCenter.getInstance().addNewBook(new Book(titleF.getText(), authF.getText(), genrF.getText(),
					Double.parseDouble(priceF.getText()), Integer.parseInt(dateF.getText())))) {
				String err = "sorry ! unable to add that book please check information and try again !";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("book successfully added !");
				alert.showAndWait();
			}
		});
		return pane;
	}

	public Pane bookRemPage() {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(50));
		pane.setHgap(6);
		pane.setVgap(10);

		Label entISBN = new Label("enter ISBN of book to DELETE");
		TextField isbn = new TextField();
		Button ent = new Button("remove !");
		ent.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));

		entISBN.setFont(Font.font("Helvetica", 13));
		ent.setFont(Font.font("Helvetica", 13));

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		ent.setDisable(true);

		back.setOnAction(e -> {
			adminStage.setScene(bookManScene);
		});

		vbox.getChildren().addAll(entISBN, isbn, ent, back);
		pane.add(vbox, 0, 2, 2, 1);

		isbn.setOnKeyTyped(e -> {
			ent.setDisable(false);
		});

		ent.setOnAction(e -> {
			if (!DataCenter.getInstance().delBook(Integer.parseInt(isbn.getText()))) {
				String err = "sorry ! that isbn is invalid! please check isbn and try again !";
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(err);
				alert.showAndWait();
			} else {
				DataCenter.getInstance().delBook(Integer.parseInt(isbn.getText()));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("book successfully removed !");
				alert.showAndWait();
				adminStage.setScene(bookManScene);
			}
		});

		return pane;
	}

	public boolean loginVal(String user, String pass) {
		return user.length() >= 6 && pass.length() >= 8;
	}

	public boolean bookAddVal() {
		return !titleF.getText().isEmpty() && !authF.getText().isEmpty() && !isbnF.getText().isEmpty()
				&& !genrF.getText().isEmpty() && !priceF.getText().isEmpty() && !dateF.getText().isEmpty();
	}

	public String randUserGen(String user) {
		Random r = new Random();
		String str = " suggestions: ";
		for (int i = 0; i <= 5; i++) {
			str += user + r.nextInt(1000);
			if (i != 5) {
				str += ". ";
			}
		}
		return str;
	}

	public TableView<Book> tView(ArrayList<Book> x) {
		ObservableList<Book> lib = FXCollections.observableArrayList(x);
		TableView<Book> tv = new TableView<Book>();
		TableColumn<Book, String> col1 = new TableColumn<>("title");
		col1.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Book, String> col2 = new TableColumn<>("author");
		col2.setCellValueFactory(new PropertyValueFactory<>("author"));

		TableColumn<Book, String> col3 = new TableColumn<>("genre");
		col3.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn<Book, Integer> col4 = new TableColumn<>("ISBN");
		col4.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

		TableColumn<Book, Integer> col5 = new TableColumn<>("price");
		col5.setCellValueFactory(new PropertyValueFactory<>("price"));

		tv.getColumns().addAll(col1, col2, col3, col4, col5);
		tv.setItems(lib);
		return tv;
	}

}