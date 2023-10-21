package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.geometry.*;
import javafx.scene.control.*;

public class Main extends Application{
	
	Scene registerPage, loginPage, landingPage, userPage, adminPage;
	
	//memakai array list karena keyboard mempunyai banyak komponen
	ArrayList<user> savedUser = new ArrayList<>();
	ArrayList<product> saveProduct = new ArrayList<>();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stages) throws Exception {
		savingUser();
		listingProduct();
		
		loginPage(stages);

		
		stages.show();
	}
	
	user current = new user("email", "password", 0, 0, 0, 0); //Reset Current User
	
	String users(String email, String password, Integer data1, Integer data2, Integer data3, Integer data4) { //komponen untuk disave di user.txt
		return email + "-" + password + "-" + data1 + "-" + data2 + "-" + data3 + "-" + data4 + "\n";
	}
	
	String products(String path, String keyboardName, Integer keyboardPrice, Integer keyboardStocks, String keyboardDescription) {
		return path + "-" + keyboardName + "-" + keyboardPrice + "-" + keyboardStocks + "-" + keyboardDescription + "\n"; //komponen untuk disave di keyboard.txt
	}
	
	String[] barrier(String data) { //untuk memisahkan baris data di file ".txt"
		return data.split("-");
	}
	
	public void savingUser() throws FileNotFoundException{ //menambahkan user ke txt
		if(savedUser.isEmpty()) {
			File filesave = new File("data/user.txt");
			try {
				Scanner scanner = new Scanner(filesave);
				while(scanner.hasNextLine()) {
					String result = scanner.nextLine();
					String[] save = barrier(result);
					//save user dengan membaca data dengan pemisah "-"
					savedUser.add(new user(save[0], save[1], Integer.valueOf(save[2]), Integer.valueOf(save[3]), Integer.valueOf(save[4]), Integer.valueOf(save[5])));
				}
				scanner.close();
			} catch(Exception ex) {
			}
		}
	}
	
	public void listingProduct() throws FileNotFoundException{ //menambahkan produk ke txt
		if(saveProduct.isEmpty()) {
			File readProduct = new File("data/keyboard.txt");
			try {
				Scanner scanning = new Scanner(readProduct);
				while(scanning.hasNextLine()) {
					String result = scanning.nextLine();
					String[] hasil = barrier(result);
					saveProduct.add(new product(hasil[0], hasil[1], Integer.valueOf(hasil[2]), Integer.valueOf(hasil[3]), hasil[4]));
				}
				scanning.close();
			} catch(Exception e) {
			}
		}
	}
	
	public void registerPage(Stage stages) throws FileNotFoundException {
		//Layout
		Image image = new Image(new File("assets/logo.png").toURI().toString());
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();
		HBox hb = new HBox();
		
		//Komponen Register page
		Label title = new Label("Register");
		Label LEmail = new Label("Email");
		TextField email = new TextField();
		LEmail.setStyle("-fx-font-size:15px");
		email.setMinWidth(280);
		email.setMinHeight(35);
		
		
		Label LPassword = new Label("Password");
		PasswordField pass = new PasswordField();
		LPassword.setStyle("-fx-font-size:15px");
		LPassword.setPrefWidth(80);
		pass.setMinWidth(280);
		pass.setMinHeight(35);
		
		
		Label LConfirmPass = new Label("Confirm Password");
		PasswordField confirmPassword = new PasswordField();
		LConfirmPass.setPrefWidth(130);
		LConfirmPass.setStyle("-fx-font-size:15px");
		confirmPassword.setMinWidth(280);
		confirmPassword.setMinHeight(35);
		
		
		Button bttnRegister = new Button("Register");
		bttnRegister.setStyle("-fx-color:#252525; -fx-text-fill:white; -fx-font-size:12px;");
		bttnRegister.setMinWidth(135);
		bttnRegister.setMinHeight(35);
		
		Button bttnLogin = new Button("Login");
		bttnLogin.setStyle("-fx-color:#252525; -fx-text-fill:white; -fx-font-size:12px");
		bttnLogin.setMinWidth(135);
		bttnLogin.setMinHeight(35);
		
		stages.getIcons().add(image);
		stages.setTitle("Register");
		
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		title.setMinHeight(100);
		title.setStyle("-fx-font-size:50px; -fx-font-weight:bold");
		
		hb.getChildren().addAll(bttnLogin,bttnRegister);
		hb.setSpacing(15);
		
		//Positioning
		gp.add(LEmail, 0, 0);
		gp.add(email, 1, 0);
		gp.add(LPassword, 0, 1);
		gp.add(pass, 1, 1);
		gp.add(LConfirmPass, 0, 2);
		gp.add(confirmPassword, 1, 2);
		gp.add(hb, 1, 3);
		bp.setCenter(gp);
		gp.setAlignment(Pos.BASELINE_CENTER);
		gp.setVgap(15);
		
		//Lambda untuk kondisi
		bttnLogin.setOnAction(e->{
			try {
				loginPage(stages);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		});
		
		bttnRegister.setOnAction(e->{
			int check = 0; //checking
			int count = 0; //counting
			
			for(int i=0;i<email.getText().length();i++) {
				if(email.getText().charAt(i)=='@') {
					count++;
				}
			}
			for(user User : savedUser) {
				if(User.getEmail().equals(email.getText())) {
					check=1;
					break;
				}
			}
			//Condition
			if(email.getText().equals("")||pass.getText().equals("")||confirmPassword.getText().equals("")) { //email & password kosong
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Email & Password must be filled!");
				alert.show();
				}
			else if(!confirmPassword.getText().equals(pass.getText())) { //password dan confirm password tidak sama
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!");
				alert.setContentText("Confirm password must be the same as Password");
				alert.show();
				}
			else if(!email.getText().contains("@")) { //email tidak ada "@"
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Email must contains '@'!");
				alert.show();
				}
			else if(count>1) { //email tidak ada "@"
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Email must have at least one '@'");
				alert.show();
				}
			else if(email.getText().startsWith("@")) { //email mempunyai "@" di karakter pertama
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Email must not start with '@'");
				alert.show();
				}
			else if(email.getText().contains(" ")) { // email ada spasi
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Email must not have space character!");
				alert.show();
				}
			else if(!email.getText().endsWith(".com")) { //tidak ada ".com" diakhir email
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!!");
				alert.setContentText("Incorect Email Format!");
				alert.show();
				}
			else if(check==1) { //email sudah terdaftar
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Register not Completed!");
				alert.setContentText("Email has been registered before");
				alert.show();
				}
			else { //Register berhasil
				current = new user(email.getText(), pass.getText(), 0, 0, 0, 0);
				savedUser.add(current);
				saveAccount(users(email.getText(), pass.getText(), 0, 0, 0, 0));
				
				try {
					loginPage(stages);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
		);
		
		registerPage = new Scene(bp,800,600);
		stages.setScene(registerPage);
	}
	
	//Login page
	public void loginPage(Stage stages) throws FileNotFoundException {
		Image image = new Image(new File("assets/logo.png").toURI().toString());
		stages.getIcons().add(image);
		stages.setTitle("Login");
		
		//komponen login page
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();
		HBox hb = new HBox();
		
		Label title = new Label("Jee Keyboard Store");
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		title.setMinHeight(100);
		title.setStyle("-fx-font-size:50px; -fx-font-weight:bold");
		
		//Styling element
		Label LEmail = new Label("Email");
		TextField email = new TextField();
		LEmail.setStyle("-fx-font-size:12px");
		email.setMinWidth(265);
		email.setMinHeight(35);
		
		Label LPassword = new Label("Password");
		PasswordField pass = new PasswordField();
		LPassword.setStyle("-fx-font-size:12px");
		LPassword.setPrefWidth(80);
		pass.setMinWidth(265);
		pass.setMinHeight(35);
		
		Button bttnRegister = new Button("Register");
		bttnRegister.setMinWidth(125);
		bttnRegister.setMinHeight(35);
		bttnRegister.setStyle("-fx-color:#252525; -fx-text-fill:white; -fx-font-size:12px;");
		
		Button bttnLogin = new Button("Login");
		bttnLogin.setStyle("-fx-color:#252525; -fx-text-fill:white; -fx-font-size:12px");
		bttnLogin.setMinWidth(125);
		bttnLogin.setMinHeight(35);
		
		hb.getChildren().addAll(bttnRegister,bttnLogin); //menjadikan register dan login button menjadi HBox untuk bersebelahan
		hb.setSpacing(15);
		
		//Mengatur posisi gridpane
		gp.add(LEmail, 0, 0);
		gp.add(email, 1, 0);
		gp.add(LPassword, 0, 1);
		gp.add(pass, 1, 1);
		gp.add(hb, 1, 2); //HBox button dijadikan baris paling bawah
		
		bp.setCenter(gp);
		gp.setAlignment(Pos.BASELINE_CENTER);
		gp.setVgap(15);
		
		//Lambda untuk kondisi
		bttnRegister.setOnAction(e->{
			try {
				registerPage(stages);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		});
		
		bttnLogin.setOnAction(e->{
			int check=1;
			for(user User : savedUser) {
				if(User.getEmail().equals(email.getText()) && User.getPassword().equals(pass.getText())) {
					check = 0;
					current = User;
					break;
				}
			}
			if(email.getText().equals("") || pass.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login failed");
				alert.setContentText("Email & Password must be filled!");
				alert.show();
			}
			else if(email.getText().equals("admin") && pass.getText().equals("admin")) {
				try {
					adminPage(stages);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
			else if(check==1) { // error message apabila email / password salah
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login failed");
				alert.setContentText("Email or Password incorrect!");
				alert.show();
			}
			else {
				try {
					landingPage(stages);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		loginPage = new Scene(bp,800,600);
		stages.setScene(loginPage);
	}

	//Update barang yang ada di admin
	public void adminUpdate(TilePane tp, product products) throws FileNotFoundException{
		GridPane gp = new GridPane();
		
		ImageView img = new ImageView(new Image(new File(products.getpath()).toURI().toString()));;
		
		Label keyboardName = new Label("Name:	");
		Label keyboardPrice = new Label("Price:	");
		Label keyboardStocks = new Label("Stock:	");
		Label keyboardDescription = new Label("Description:	");
		
		TextField tfKeyboard = new TextField(products.getkeyboardName());
		tfKeyboard.setDisable(true);
		TextField tfPrice = new TextField(String.valueOf(products.getkeyboardPrice()));
		TextField tfStok = new TextField(String.valueOf(products.getkeyboardStocks()));
		TextField tfDescription = new TextField(products.getkeyboardDescription());
		
		Button bttnUpdate = new Button("Update");
		
		img.setFitHeight(100); 
		img.setFitWidth(200);
		
		gp.setAlignment(Pos.CENTER);
		gp.add(img, 0, 0);
		gp.add(keyboardName, 1, 0);
		gp.add(tfKeyboard, 2, 0);
		gp.add(keyboardPrice, 3, 0);
		gp.add(tfPrice, 4, 0);
		gp.add(keyboardStocks, 5, 0);
		gp.add(tfStok, 6, 0);
		gp.add(keyboardDescription, 1, 1);
		gp.add(tfDescription, 2, 1);
		GridPane.setColumnSpan(tfDescription, 4);
		gp.add(bttnUpdate, 2, 2);
		
		tp.getChildren().add(gp);
		
		//Lambda untuk update button
		bttnUpdate.setOnMouseClicked(e->{
			try {
				if(tfStok.getText().equals("")) { //Stok kosong
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Stock must be filled");
					alert.show();
				}
				else if(tfPrice.getText().equals("")) { //harga kosong
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Price must be filled");
					alert.show();
				}
				else if(tfDescription.getText().equals("")) { //deskripsi kosong
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Description must be filled");
					alert.show();
				}
				else if(!(tfStok.getText().matches("^-[1-9]\\d*|0$") || tfStok.getText().matches("^[0-9]+$"))) { //stok bukan numerik
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Stock must be numeric");
					alert.show();
				}
				else if(!(tfPrice.getText().matches("^-[1-9]\\d*|0$") || tfPrice.getText().matches("^[0-9]+$"))) { //harga bukan numerik
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Price must be numeric");
					alert.show();
				}
				else if(Integer.valueOf(tfStok.getText()) < 0) { //stok > atau = dari 0
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Stock must be more than or equal to 0");
					alert.show();
				}
				else if(Integer.valueOf(tfPrice.getText()) <= 0) { //harga >0
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Update unsuccessfull");
					alert.setContentText("Price must be more than 0");
					alert.show();
				}
				else{
					products.setkeyboardPrice(Integer.valueOf(tfPrice.getText()));
					products.setkeyboardStocks(Integer.valueOf(tfStok.getText()));
					products.setkeyboardDescription(tfDescription.getText());					
					addProduct(products(saveProduct.get(0).getpath(), saveProduct.get(0).getkeyboardName(), saveProduct.get(0).getkeyboardPrice(), saveProduct.get(0).getkeyboardStocks(), saveProduct.get(0).getkeyboardDescription()));
					
					for(int i = 1; i < saveProduct.size(); i++) {
						addProduct(products(saveProduct.get(i).getpath(), saveProduct.get(i).getkeyboardName(), saveProduct.get(i).getkeyboardPrice(), saveProduct.get(i).getkeyboardStocks(), saveProduct.get(i).getkeyboardDescription()));
					}
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Update Product");
					
					alert.setContentText("Data Updated!");
					alert.show();
				}
				
			} catch (Exception ex){
				ex.printStackTrace();
			}
		});
	}
	
	//Admin
	public void adminPage(Stage stages) throws FileNotFoundException {
		Image image = new Image(new File("assets/logo.png").toURI().toString());
		stages.setTitle("Admin");
		stages.getIcons().add(image);
		
		ScrollPane sp = new ScrollPane();
		TilePane tp = new TilePane();
		tp.setPadding(new Insets(15));
		tp.setVgap(40);
		tp.setAlignment(Pos.CENTER);
		sp.setContent(tp);
		sp.setPadding(new Insets(30, 70, 0, 70));
		sp.setFitToWidth(true);
		
		MenuBar mb = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem logout = new MenuItem("Logout");
		menu.getItems().add(logout);

		mb.getMenus().add(menu);
		BorderPane bp = new BorderPane();
		bp.setTop(mb);
		bp.setCenter(sp);
		
		adminUpdate(tp, saveProduct.get(0));
		adminUpdate(tp, saveProduct.get(1));
		adminUpdate(tp, saveProduct.get(2));
		adminUpdate(tp, saveProduct.get(3));
		
		logout.setOnAction(e->{
			try {
				loginPage(stages);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		});
		
		adminPage = new Scene(bp, 1200, 600);
		stages.setScene(adminPage);
	}
	
	//Welcome 
	public void landingPage(Stage stages) throws FileNotFoundException {

		Image image = new Image(new File("assets/logo.png").toURI().toString());
		VBox vb = new VBox();
		BorderPane bp = new BorderPane();
		MenuBar mb = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem viewKeyboard = new MenuItem("View Keyboard");
		MenuItem logout = new MenuItem("Logout");
		
		//Video
		Media vid = new Media(new File("assets/keyboard.mp4").toURI().toString());
		MediaPlayer mp = new MediaPlayer(vid);
		MediaView video = new MediaView(mp);
		
		Text title = new Text("NEW KEYBOARD ARRIVED!");
		Button bttnContinue = new Button("CONTINUE");
		mp.setAutoPlay(true);
		mp.setCycleCount(MediaPlayer.INDEFINITE);
		video.setFitWidth(400);
		video.setFitHeight(400);
		
		menu.getItems().addAll(viewKeyboard,logout);
		mb.getMenus().add(menu);
		bp.setTop(mb); 
		vb.getChildren().addAll(title,video,bttnContinue);
		
		//styling headline dan continue button
		title.setStyle("-fx-font-size:50px;-fx-font-weight:bold; -fx-color:#252525");
		bttnContinue.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-text-fill:white; -fx-color:#252525");
		bttnContinue.setMinWidth(210);
		bttnContinue.setMinHeight(40);
		
		bp.setCenter(vb);
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(23);
		
		stages.getIcons().add(image);
		stages.setTitle("User");
		
		//menu items
		logout.setOnAction(e->{
			try {
				mp.stop();
				loginPage(stages);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		});
		
		bttnContinue.setOnAction(e->{
			try {
				mp.stop();
				userPage(stages);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		});
		
		landingPage = new Scene(bp,900,500);
		stages.setScene(landingPage);
	}
	
	//User 
		public void userPage(Stage stages) throws FileNotFoundException {
			Image image = new Image(new File("assets/logo.png").toURI().toString());
			
			//INPUT MP3 for backsound
			Media backsound = new Media(new File("assets/lofi.mp3").toURI().toString());
			MediaPlayer lofiMusic = new MediaPlayer(backsound);
			
			VBox vbCart = new VBox();
			ScrollPane spCart = new ScrollPane();
			TilePane tpCart = new TilePane();
			
			Label LCart = new Label("Your Cart");
			
			Button bttnBuy = new Button("Buy");
			Button bttnClear = new Button("Clear");
			
			bttnBuy.setMinWidth(90);
			bttnClear.setMinWidth(90);
			HBox bttnCart = new HBox(bttnBuy, bttnClear);
			bttnCart.setSpacing(5);
			
			spCart.setContent(tpCart);
			spCart.setFitToWidth(true);
			spCart.setPrefSize(450, 450);
			
			tpCart.setPadding(new Insets(10));
			tpCart.setHgap(10);
			tpCart.setVgap(10);
			
			MenuBar mb = new MenuBar();
			Menu menu = new Menu("Menu");
			MenuItem logout = new MenuItem("Logout");
			
			BorderPane bp = new BorderPane();
			VBox container = new VBox();
			HBox mainCart = new HBox();
			
			//Header
			Label header = new Label("Catalogue");
			container.getChildren().addAll(mb,header,bp);
			ScrollPane sp = new ScrollPane();
			TilePane tp = new TilePane();
			
			//display product dengan Tilepane
			cart(tp, saveProduct.get(0));
			cart(tp, saveProduct.get(1));
			cart(tp, saveProduct.get(2));
			cart(tp, saveProduct.get(3));
			
			//scrollpane
			sp.setContent(tp);
			sp.setFitToWidth(true);
			sp.setPrefSize(550, 550);
			
			//Space Vbox & Hbox
			tp.setHgap(15);
			tp.setVgap(15);
			tp.setPadding(new Insets(10));
			
			vbCart.getChildren().addAll(LCart, spCart, bttnCart);
			vbCart.setAlignment(Pos.CENTER);
			bttnCart.setAlignment(Pos.BOTTOM_LEFT);
			
			bttnClear.setOnAction(e->{
				current.setdata1(0);
				current.setdata2(0);
				current.setdata3(0);
				current.setdata4(0);

				try {
					userPage(stages);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
				
			});
			
			bttnBuy.setOnAction(e->{ //Submit Cart dan menghilangkan item item di cart
				if(current.getdata1() == 0 && current.getdata2() == 0 && current.getdata3() == 0 && current.getdata4() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Buy");
					
					alert.setContentText("Your Cart is Empty");
					alert.show();
					return;
				}
				
				saveProduct.get(0).setkeyboardStocks(saveProduct.get(0).getkeyboardStocks() - current.getdata1());
				saveProduct.get(1).setkeyboardStocks(saveProduct.get(1).getkeyboardStocks() - current.getdata2());
				saveProduct.get(2).setkeyboardStocks(saveProduct.get(2).getkeyboardStocks() - current.getdata3());
				saveProduct.get(3).setkeyboardStocks(saveProduct.get(3).getkeyboardStocks() - current.getdata4());

				current.setdata1(0);
				current.setdata2(0);
				current.setdata3(0);
				current.setdata4(0);
				
				
				addProduct(products(saveProduct.get(0).getpath(), saveProduct.get(0).getkeyboardName(), saveProduct.get(0).getkeyboardPrice(), saveProduct.get(0).getkeyboardStocks(), saveProduct.get(0).getkeyboardDescription()));
				
//				Save product info
				for(int i = 1; i < saveProduct.size(); i++) {
					addProduct(products(saveProduct.get(i).getpath(), saveProduct.get(i).getkeyboardName(), saveProduct.get(i).getkeyboardPrice(), saveProduct.get(i).getkeyboardStocks(), saveProduct.get(i).getkeyboardDescription()));
				}
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Buy");
				
				alert.setContentText("Purchased!");
				alert.show();
				
				try {
					userPage(stages);
				} 
				catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			});
			
			spCart.setOnDragOver((e)->{	//Pendeteksi drag and drop
				if(e.getGestureSource()!=spCart && e.getDragboard().hasString()) {
					e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				e.consume(); //agar event menjadi local problem saja
			});
			
				//Memakai if semua karena bisa menambahkan lebih dari 1 tipe produk
				if(saveProduct.get(0).getkeyboardStocks() < current.getdata1()) {
					current.setdata1(0);
				}
				if(saveProduct.get(1).getkeyboardStocks() < current.getdata2()) {
					current.setdata2(0);
				}
				if(saveProduct.get(2).getkeyboardStocks() < current.getdata3()) {
					current.setdata3(0);
				}
				if(saveProduct.get(3).getkeyboardStocks() < current.getdata4()) {
					current.setdata4(0);
				}
				
				
				if(current.getdata1() > 0) {
					addtoCart(tpCart, saveProduct.get(0));
				}
				if(current.getdata2() > 0) {
					addtoCart(tpCart, saveProduct.get(1));
				}
				if(current.getdata3() > 0) {
					addtoCart(tpCart, saveProduct.get(2));
				}
				if(current.getdata4() > 0) {
					addtoCart(tpCart, saveProduct.get(3));
				}
			
			
			spCart.setOnDragDropped((e)->{
				Dragboard db = e.getDragboard();
				
				if(db.hasString()) {
					Label id = new Label(db.getString());
					
					if(id != null) {
						try {
							Integer jumlah = 0;
							for(int i = 0; i < saveProduct.size(); i++) {
								if(saveProduct.get(i).getkeyboardName().equals(db.getString())) {
									jumlah = i;
									break;
								}
							}
							
//							Kondisi dimana apabila user membeli suatu tipe keyboard dan membolehkan lebih dari 1 item
							if(saveProduct.get(jumlah).getkeyboardName().equals("Igoltech Keebs XO200") && saveProduct.get(jumlah).getkeyboardStocks() > current.getdata1()){	
								
											current.setdata1(current.getdata1() + 1);
											
							}else if(saveProduct.get(jumlah).getkeyboardName().equals("Dark Black RGB") && saveProduct.get(jumlah).getkeyboardStocks() > current.getdata2()){
								
											current.setdata2(current.getdata2() + 1);
											
							}else if(saveProduct.get(jumlah).getkeyboardName().equals("Watermelon Keebs Z200") && saveProduct.get(jumlah).getkeyboardStocks() > current.getdata3()){
								
											current.setdata3(current.getdata3() + 1);
											
							}else if(saveProduct.get(jumlah).getkeyboardName().equals("Igoltech Classic Keebs") && saveProduct.get(jumlah).getkeyboardStocks() > current.getdata4()){
								
											current.setdata4(current.getdata4() + 1);
											
							}else{
//								Apabila demand melebihi stok yang tersedia
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error");
								alert.setContentText("Stock unavailable!");
								alert.show();
								return;
							}userPage(stages);
						}catch(FileNotFoundException ex){
							ex.printStackTrace();
						}
					}
					e.setDropCompleted(true);
				}	else{e.setDropCompleted(false);
				}
			});
			
			mainCart.getChildren().addAll(sp, vbCart);
			bp.setCenter(mainCart);
			
			container.setAlignment(Pos.TOP_CENTER);
			
			LCart.setStyle("-fx-font-size:15px; -fx-font-weight:bold");
			stages.getIcons().add(image);
			stages.setTitle("User");
			BorderPane.setAlignment(mainCart, Pos.TOP_CENTER);
			
			menu.getItems().addAll(logout);
			mb.getMenus().add(menu);
			
			for(int i = 0; i < savedUser.size(); i++) {
				if(savedUser.get(i).getEmail().equals(current.getEmail()) && savedUser.get(i).getPassword().equals(current.getPassword())) {
					savedUser.get(i).setdata1(current.getdata1());
					savedUser.get(i).setdata2(current.getdata2());
					savedUser.get(i).setdata3(current.getdata3());
					savedUser.get(i).setdata4(current.getdata4());
					break;
				}
			}
			
			saveAccount(users(savedUser.get(0).getEmail(), savedUser.get(0).getPassword(), savedUser.get(0).getdata1(), savedUser.get(0).getdata2(), savedUser.get(0).getdata3(), savedUser.get(0).getdata4()));
			
			for(int i = 1; i < savedUser.size(); i++) {
				saveAccount(users(savedUser.get(i).getEmail(), savedUser.get(i).getPassword(), savedUser.get(i).getdata1(), savedUser.get(i).getdata2(), savedUser.get(i).getdata3(), savedUser.get(i).getdata4()));
			}
			
			logout.setOnAction(e->{
				try {
					lofiMusic.stop();
					loginPage(stages);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			});
			
			userPage = new Scene(container,900,500);
			stages.setScene(userPage);
		}
	
	//Cart
	public void cart(TilePane tp, product products) throws FileNotFoundException{
		VBox gp = new VBox();
		
		ImageView img = new ImageView(new Image(new File(products.getpath()).toURI().toString()));
		Label keyboardName = new Label("Name: " + products.getkeyboardName());
		Label keyboardPrice = new Label("Price: " + String.valueOf(products.getkeyboardPrice()));
		Label keyboardStocks = new Label("Stock: " + String.valueOf(products.getkeyboardStocks()));
		Label keyboardDescription = new Label("Description: " + products.getkeyboardDescription());
		
		keyboardName.setMaxSize(200, 200);
		keyboardName.setWrapText(true);
		
		keyboardDescription.setMaxSize(200, 200);
		keyboardDescription.setWrapText(true);
		
		img.setFitHeight(200);
		img.setFitWidth(300);
		
		gp.getChildren().addAll(img, keyboardName, keyboardPrice, keyboardStocks, keyboardDescription);
		gp.setId(products.getkeyboardName());
		
		tp.getChildren().add(gp);
		
		
		gp.setOnDragDetected((e)->{
			
			Dragboard db = gp.startDragAndDrop(TransferMode.ANY);
			ClipboardContent cont = new ClipboardContent();
		
			cont.putString(gp.getId());
			db.setContent(cont);
			
			e.setDragDetect(true);
			e.consume();
		});
	}
	
	public void addtoCart(TilePane tp, product products) throws FileNotFoundException{
		VBox gp = new VBox();
		
		ImageView img = new ImageView(new Image(new File(products.getpath()).toURI().toString()));
		img.setFitHeight(200);
		img.setFitWidth(200);
		
		Label keyboardName = new Label(products.getkeyboardName());
		keyboardName.setMaxSize(200, 200);
		keyboardName.setWrapText(true);
		
		//Detail 
		Label CartDetail = new Label("Total: ");
		if(products.getkeyboardName().equals("Igoltech Keebs XO200")) {
			CartDetail.setText("Total: " + current.getdata1());
		} else if(products.getkeyboardName().equals("Dark Black RGB")){
			CartDetail.setText("Total: " + current.getdata2());
		} else if(products.getkeyboardName().equals("Watermelon Keebs Z200")){
			CartDetail.setText("Total: " + current.getdata3());
		} else if(products.getkeyboardName().equals("Igoltech Classic Keebs")){
			CartDetail.setText("Total: " + current.getdata4());
		}
		
		img.setFitHeight(200);
		img.setFitWidth(200);
		gp.getChildren().addAll(img, keyboardName, CartDetail);
		tp.getChildren().add(gp);
	}
	
	void saveAccount(String data) {
		try {
			FileWriter overWrite = new FileWriter("save/user.txt", true);
			overWrite.write(data);
			overWrite.close();
		}catch (Exception ex){
			
		}
	}
	
	void addProduct(String data) {
		try {
			FileWriter overWrite = new FileWriter("data/keyboard.txt", true);
			overWrite.write(data);
			overWrite.close();
		}catch (Exception ex){
			
		}
	}
}