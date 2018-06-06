import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.*;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.util.*;


public class ClientGUI extends Application {

    private Client current;
    Stage primary_window;



    @Override
    public void start(Stage primaryStage) throws Exception {
        primary_window = primaryStage;
        current = new Client();
        current.setCgui(this);
        authenticationmenu();


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void authfailprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Authentication failed");

        Label label = new Label();
        label.setText("Authentication failed, please try again.");

        Button button = new Button("Close");
        button.setMinWidth(100);
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();


    }

    public void notenoughcreditprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Not enough credit");

        Label label = new Label();
        label.setText("Not enough credit in account.");

        Button button = new Button("Close");
        button.setMinWidth(100);
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();


    }

    public void sellernotonlineprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Seller not online");

        Label label = new Label();
        label.setText("Seller is not online, try again later.");

        Button button = new Button("Close");
        button.setMinWidth(100);
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();


    }

    public void invalidinputprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Invalid input");

        Label label = new Label();
        label.setText("Invalid input.");

        Button button = new Button("Close");
        button.setMinWidth(100);
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();
    }

    public void operationsuccessfulprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Operation successful");

        Label label = new Label();
        label.setText("Operation successfully completed");

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( e -> stage.close() );
        delay.play();

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();

    }

    public void requestfailprompt() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Request failed");

        Label label = new Label();
        label.setText("Request failed, try again later.");

        Button button = new Button("Close");
        button.setMinWidth(100);
        button.setOnAction(e -> stage.close());

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox, 280, 180);
        stage.setScene(scene);
        stage.show();
    }

    public void authenticationmenu() {
        primary_window.setTitle("Finchat login menu");

        Label label = new Label("Username:");
        Label label1 = new Label("Password:");

        TextField textField = new TextField();
        TextField textField1 = new TextField();

        Button button = new Button("Login");
        button.setMinWidth(100);

        button.setOnAction(e -> {
            if (textField.getText().equals("") || textField1.getText().equals("")) {
                invalidinputprompt();

            } else {
                current.login(textField.getText(), textField1.getText());
                if (current.getCurrent() != null) {
                    menu();

                } else {
                    authfailprompt();
                }

            }


        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label, textField);

        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(label1, textField1);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(hBox, hBox1, button);

        Scene scene = new Scene(vBox, 500, 200);
        primary_window.setScene(scene);
        primary_window.show();


    }

    public void menu() {


        primary_window.setTitle("Finchat menu");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        VBox menu_buttons = new VBox(10);
        menu_buttons.setAlignment(Pos.CENTER);
        menu_buttons.setPadding(new Insets(20, 20, 20, 20));

        Button shop = new Button("Buy");
        shop.setOnAction(e -> buy_items());
        shop.setMinWidth(100);
        Button sell = new Button("Sell");
        sell.setOnAction(event -> additem());
        sell.setMinWidth(100);
        Button addcredit = new Button("Add credit");
        addcredit.setOnAction(e -> addcredit());
        addcredit.setMinWidth(100);
        Button epi = new Button("Edit personal");
        epi.setOnAction(e -> edit_personal());
        epi.setMinWidth(100);
        Button ds = new Button("Edit selling");
        ds.setOnAction(e -> edit__del_selling());
        ds.setMinWidth(100);
        Button bought = new Button("View bought");
        bought.setOnAction(e -> view_bought());
        bought.setMinWidth(100);
        Button sold = new Button("View sold");
        sold.setOnAction(e -> view_sold());
        sold.setMinWidth(100);
        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            current.logout();
            authenticationmenu();
        });
        logout.setMinWidth(100);

        menu_buttons.getChildren().addAll(shop, sell, addcredit, epi, ds, bought, sold, logout);
        Scene menu = new Scene(menu_buttons, 800, 500);
        primary_window.setScene(menu);
        primary_window.show();

    }

    public void view_bought() {
        VBox boughtbox = new VBox(10);
        boughtbox.setAlignment(Pos.CENTER);
        boughtbox.setPadding(new Insets(20, 20, 20, 20));
        ObservableList<Transaction> boughtoblist = FXCollections.observableArrayList(current.getbought());

        primary_window.setTitle("Bought items");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        TableView<Transaction> boughttable = new TableView<>();
        boughttable.setPlaceholder(new Label("You haven't bought anything"));
        boughttable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Transaction, String> item_name_column = new TableColumn<>("Item name");
        item_name_column.setMinWidth(100);
        item_name_column.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<Transaction, String> description_column = new TableColumn<>("Description");
        description_column.setMinWidth(100);
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, Double> price_sold_column = new TableColumn<>("Price sold");
        price_sold_column.setMinWidth(100);
        price_sold_column.setCellValueFactory(new PropertyValueFactory<>("pricesold"));

        TableColumn<Transaction, Date> date_column = new TableColumn<>("Date");
        date_column.setMinWidth(100);
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> status_column = new TableColumn<>("Status");
        status_column.setMinWidth(100);
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));

        boughttable.setItems(boughtoblist);
        boughttable.getColumns().addAll(item_name_column, description_column, price_sold_column, date_column, status_column);

        Button back_button = new Button("Back");
        back_button.setMinWidth(100);
        back_button.setOnAction(e -> menu());

        boughtbox.getChildren().addAll(boughttable, back_button);
        Scene boughtscene = new Scene(boughtbox, 800, 500);
        primary_window.setScene(boughtscene);
        primary_window.show();
    }

    public void view_sold() {

        VBox boughtbox = new VBox(10);
        boughtbox.setAlignment(Pos.CENTER);
        boughtbox.setPadding(new Insets(20, 20, 20, 20));
        ObservableList<Transaction> soldoblist = FXCollections.observableArrayList(current.getsold());

        primary_window.setTitle("Sold items");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        TableView<Transaction> soldtable = new TableView<>();
        soldtable.setPlaceholder(new Label("You haven't sold anything."));
        soldtable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Transaction, String> item_name_column = new TableColumn<>("Item name");
        item_name_column.setMinWidth(100);
        item_name_column.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<Transaction, String> description_column = new TableColumn<>("Description");
        description_column.setMinWidth(100);
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, Double> price_sold_column = new TableColumn<>("Price sold");
        price_sold_column.setMinWidth(100);
        price_sold_column.setCellValueFactory(new PropertyValueFactory<>("pricesold"));

        TableColumn<Transaction, Date> date_column = new TableColumn<>("Date");
        date_column.setMinWidth(100);
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> status_column = new TableColumn<>("Status");
        status_column.setMinWidth(100);
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));

        soldtable.setItems(soldoblist);
        soldtable.getColumns().addAll(item_name_column, description_column, price_sold_column, date_column, status_column);

        Button back_button = new Button("Back");
        back_button.setMinWidth(100);
        back_button.setOnAction(e -> menu());

        boughtbox.getChildren().addAll(soldtable, back_button);
        Scene boughtscene = new Scene(boughtbox, 800, 500);
        primary_window.setScene(boughtscene);
        primary_window.show();

    }

    public void addcredit() {
        primary_window.setTitle("Add credit");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        Label creditlabel = new Label("Enter the amount you want to add:");
        TextField creditinput = new TextField();
        Button creditbutton = new Button("Add credit");
        creditbutton.setMinWidth(100);
        Button backbutton = new Button("Back");
        backbutton.setMinWidth(100);


        HBox credithbox = new HBox(10);
        credithbox.setAlignment(Pos.CENTER);
        credithbox.getChildren().addAll(creditlabel, creditinput);

        HBox creditbuttonshbox = new HBox(10);
        creditbuttonshbox.setAlignment(Pos.CENTER);
        creditbuttonshbox.getChildren().addAll(backbutton, creditbutton);

        VBox creditvbox = new VBox(10);
        creditvbox.setAlignment(Pos.CENTER);
        creditvbox.setPadding(new Insets(20, 20, 20, 20));
        creditvbox.getChildren().addAll(credithbox, creditbuttonshbox);

        creditbutton.setOnAction(e -> {
            Double amount;
            try {
                amount = Double.parseDouble(creditinput.getText());
                if (!current.addcredit(amount)) {
                    requestfailprompt();
                } else {
                    operationsuccessfulprompt();
                }

            } catch (NumberFormatException e1) {
                invalidinputprompt();

            }
        });

        backbutton.setOnAction((e -> menu()));

        Scene creditscene = new Scene(creditvbox, 800, 500);
        primary_window.setScene(creditscene);
        primary_window.show();
    }

    public void additem() {

        primary_window.setTitle("Sell");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });

        Label namelabel = new Label("Item name:  ");
        TextField nameinput = new TextField();
        Label pricelabel = new Label("Price:           ");
        TextField priceinput = new TextField();
        Label descriptionlabel = new Label("Description:");
        TextField descriptioninput = new TextField();
        Button itembutton = new Button("Sell");
        itembutton.setMinWidth(100);
        Button backbutton = new Button("Back");
        backbutton.setMinWidth(100);


        HBox namehbox = new HBox(10);
        namehbox.setAlignment(Pos.CENTER);
        namehbox.getChildren().addAll(namelabel, nameinput);

        HBox pricehbox = new HBox(10);
        pricehbox.setAlignment(Pos.CENTER);
        pricehbox.getChildren().addAll(pricelabel, priceinput);

        HBox descriptionhbox = new HBox(10);
        descriptionhbox.setAlignment(Pos.CENTER);
        descriptionhbox.getChildren().addAll(descriptionlabel, descriptioninput);

        HBox buttonshbox = new HBox(10);
        buttonshbox.setAlignment(Pos.CENTER);
        buttonshbox.getChildren().addAll(backbutton, itembutton);

        VBox newitemvbox = new VBox(10);
        newitemvbox.setAlignment(Pos.CENTER);
        newitemvbox.setPadding(new Insets(20, 20, 20, 20));
        newitemvbox.getChildren().addAll(namehbox, pricehbox, descriptionhbox, buttonshbox);

        itembutton.setOnAction(e -> {
            String itemname, description;
            Double price;

            try {

                itemname = nameinput.getText();
                description = descriptioninput.getText();
                price = Double.parseDouble(priceinput.getText());

                Item new_item = new Item(itemname, description, price, 0);

                if (!current.additem(new_item)) {
                    requestfailprompt();
                } else {
                    operationsuccessfulprompt();
                    nameinput.clear();
                    priceinput.clear();
                    descriptioninput.clear();
                }

            } catch (NumberFormatException e1) {
                invalidinputprompt();

            }
        });

        backbutton.setOnAction((e -> menu()));

        Scene newitemscene = new Scene(newitemvbox, 800, 500);
        primary_window.setScene(newitemscene);
        primary_window.show();

    }

    public void edit__del_selling() {
        VBox del_items = new VBox(10);
        del_items.setAlignment(Pos.CENTER);
        del_items.setPadding(new Insets(20, 20, 20, 20));
        ObservableList<Item> deloblist = FXCollections.observableArrayList(current.getsellitems());


        primary_window.setTitle("Edit selling items");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        TableView<Item> deltable = new TableView<>();
        deltable.setPlaceholder(new Label("You aren't selling anything"));
        deltable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Item, String> item_name_column = new TableColumn<>("Item name");
        item_name_column.setMinWidth(100);
        item_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> description_column = new TableColumn<>("Description");
        description_column.setMinWidth(100);
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Double> price_sold_column = new TableColumn<>("Price");
        price_sold_column.setMinWidth(100);
        price_sold_column.setCellValueFactory(new PropertyValueFactory<>("price_posted"));

        deltable.setItems(deloblist);
        deltable.getColumns().addAll(item_name_column, description_column, price_sold_column);

        Button back_button = new Button("Back");
        back_button.setMinWidth(100);
        back_button.setOnAction(e -> menu());

        Button delete_button = new Button("Delete");
        delete_button.setMinWidth(100);
        delete_button.setOnAction(e -> {
            ObservableList<Item> temp;
            Item item = deltable.getSelectionModel().getSelectedItem();
            temp = deltable.getSelectionModel().getSelectedItems();

            if (item == null) {

                invalidinputprompt();
            } else {
                if (current.deleteitem(item.getItem_code())) {
                    temp.forEach(deloblist::remove);
                    operationsuccessfulprompt();

                } else {
                    requestfailprompt();
                }

            }

        });

        Button edit_button = new Button("Edit");
        edit_button.setMinWidth(100);

        edit_button.setOnAction(e -> {
            Item item = deltable.getSelectionModel().getSelectedItem();

            if (item == null) {
                invalidinputprompt();
            } else {
                item_edit_window(item, deloblist, deltable);

            }
        });

        HBox delbuttons = new HBox(10);
        delbuttons.setAlignment(Pos.CENTER);
        delbuttons.getChildren().addAll(back_button, delete_button, edit_button);

        del_items.getChildren().addAll(deltable, delbuttons);
        Scene deletescene = new Scene(del_items, 800, 500);
        primary_window.setScene(deletescene);
        primary_window.show();


    }

    public void edit_personal() {

        primary_window.setTitle("Edit personal information");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        ArrayList<String> info = current.getcurrent();

        Label fnlabel = new Label("Firstname:   ");
        TextField fninput = new TextField();
        fninput.setText(info.get(0));

        Label lnlabel = new Label("Lastname:   ");
        TextField lninput = new TextField();
        lninput.setText(info.get(1));

        Label pwlabel = new Label("Password:   ");
        TextField pwinput = new TextField();
        pwinput.setText(info.get(2));

        Label streetlabel = new Label("Street:         ");
        TextField streetinput = new TextField();
        streetinput.setText(info.get(3));

        Label citylabel = new Label("City:            ");
        TextField cityinput = new TextField();
        cityinput.setText(info.get(4));

        Label provincelabel = new Label("Province:    ");
        TextField provinceinput = new TextField();
        provinceinput.setText(info.get(5));

        Label postallabel = new Label("Postalcode:");
        TextField postalinput = new TextField();
        postalinput.setText(info.get(6));

        Label countrylabel = new Label("Country:     ");
        TextField countryinput = new TextField();
        countryinput.setText(info.get(7));

        Button donebutton = new Button("Done");
        donebutton.setMinWidth(100);


        Button backbutton = new Button("Back");
        backbutton.setMinWidth(100);


        HBox fnhb = new HBox(10);
        fnhb.setAlignment(Pos.CENTER);
        fnhb.getChildren().addAll(fnlabel, fninput);

        HBox lnhb = new HBox(10);
        lnhb.setAlignment(Pos.CENTER);
        lnhb.getChildren().addAll(lnlabel, lninput);

        HBox pwhb = new HBox(10);
        pwhb.setAlignment(Pos.CENTER);
        pwhb.getChildren().addAll(pwlabel, pwinput);

        HBox sthb = new HBox(10);
        sthb.setAlignment(Pos.CENTER);
        sthb.getChildren().addAll(streetlabel, streetinput);

        HBox chb = new HBox(10);
        chb.setAlignment(Pos.CENTER);
        chb.getChildren().addAll(citylabel, cityinput);

        HBox prhb = new HBox(10);
        prhb.setAlignment(Pos.CENTER);
        prhb.getChildren().addAll(provincelabel, provinceinput);

        HBox pohb = new HBox(10);
        pohb.setAlignment(Pos.CENTER);
        pohb.getChildren().addAll(postallabel, postalinput);

        HBox cohb = new HBox(10);
        cohb.setAlignment(Pos.CENTER);
        cohb.getChildren().addAll(countrylabel, countryinput);

        HBox infoeditbuttons = new HBox(10);
        infoeditbuttons.setAlignment(Pos.CENTER);
        infoeditbuttons.getChildren().addAll(backbutton, donebutton);

        VBox newitemvbox = new VBox(10);
        newitemvbox.setAlignment(Pos.CENTER);
        newitemvbox.setPadding(new Insets(20, 20, 20, 20));
        newitemvbox.getChildren().addAll(fnhb, lnhb, pwhb, sthb, chb, prhb, pohb, cohb, infoeditbuttons);


        donebutton.setOnAction(e -> {

            try {
                String fn = "", ln = "", pw = "", street = "", city = "", prov = "", postal = "", country = "";

                if (fninput.getText().equals("") || lninput.getText().equals("") || pwinput.getText().equals("") ||
                        streetinput.getText().equals("") || streetinput.getText().equals("") || cityinput.getText().equals("")
                        || provinceinput.getText().equals("") || postalinput.getText().equals("") || countryinput.getText().equals("")) {
                    invalidinputprompt();
                } else {
                    fn = fninput.getText();
                    ln = lninput.getText();
                    pw = pwinput.getText();
                    street = streetinput.getText();
                    city = cityinput.getText();
                    prov = provinceinput.getText();
                    postal = postalinput.getText();
                    country = countryinput.getText();

                    if (!current.updatefn(fn) || !current.updateln(ln) || !current.updatepw(pw)
                            || !current.updateaddress(new Address(street, city, prov, country, postal))) {
                        requestfailprompt();
                    } else {
                        operationsuccessfulprompt();

                    }

                }

            } catch (NumberFormatException e1) {
                invalidinputprompt();

            }

        });

        backbutton.setOnAction((e -> menu()));

        Scene newitemscene = new Scene(newitemvbox, 800, 500);
        primary_window.setScene(newitemscene);
        primary_window.show();

    }

    public void item_edit_window(Item item, ObservableList<Item> itemsol, TableView<Item> itemtable) {
        Stage edititemswindow = new Stage();
        edititemswindow.initModality(Modality.APPLICATION_MODAL);
        edititemswindow.setTitle("Edit item fields");

        Label inl = new Label("Item name:   ");
        TextField ininput = new TextField();
        ininput.setText(item.getName());

        Label dlabel = new Label("Description: ");
        TextField dinput = new TextField();
        dinput.setText(item.getDescription());

        Label plabel = new Label("Price:           ");
        TextField pinput = new TextField();
        pinput.setText(Double.toString(item.getPrice_posted()));

        Button donebutton = new Button("Done");
        donebutton.setMinWidth(100);

        Button backbutton = new Button("Back");
        backbutton.setMinWidth(100);


        HBox inhb = new HBox(10);
        inhb.setAlignment(Pos.CENTER);
        inhb.getChildren().addAll(inl, ininput);

        HBox deshb = new HBox(10);
        deshb.setAlignment(Pos.CENTER);
        deshb.getChildren().addAll(dlabel, dinput);

        HBox phb = new HBox(10);
        phb.setAlignment(Pos.CENTER);
        phb.getChildren().addAll(plabel, pinput);

        HBox buttonshb = new HBox(10);
        buttonshb.setAlignment(Pos.CENTER);
        buttonshb.getChildren().addAll(backbutton, donebutton);


        VBox edititemvb = new VBox(10);
        edititemvb.setAlignment(Pos.CENTER);
        edititemvb.setPadding(new Insets(20, 20, 20, 20));
        edititemvb.getChildren().addAll(inhb, deshb, phb, buttonshb);

        donebutton.setOnAction(e -> {

            try {
                String name = "", desciption = "", price = "";

                if (ininput.getText().equals("") || dinput.getText().equals("") || pinput.getText().equals("")) {
                    invalidinputprompt();
                } else {
                    name = ininput.getText();
                    desciption = dinput.getText();
                    price = pinput.getText();

                    Item tempi = new Item(name, desciption, Double.parseDouble(price), item.getItem_code());

                    if (!current.updatesellitems(tempi)) {
                        requestfailprompt();
                    } else {
                        operationsuccessfulprompt();
                        ObservableList<Item> temp = itemtable.getSelectionModel().getSelectedItems();
                        temp.forEach(itemsol::remove);
                        itemsol.add(tempi);
                        edititemswindow.close();

                    }

                }

            } catch (NumberFormatException e1) {
                invalidinputprompt();

            }

        });

        backbutton.setOnAction((e -> edititemswindow.close()));


        Scene operationsuccessfulscene = new Scene(edititemvb, 800, 500);
        edititemswindow.setScene(operationsuccessfulscene);
        edititemswindow.show();
    }

    public void buy_items() {

        VBox buy_itemsvb = new VBox(10);
        buy_itemsvb.setAlignment(Pos.CENTER);
        buy_itemsvb.setPadding(new Insets(20, 20, 20, 20));
        ObservableList<Item> buyoblist = FXCollections.observableArrayList(current.returnbuyitems());

        primary_window.setTitle("Buy");
        primary_window.setOnCloseRequest(e -> {
            e.consume();
            current.logout();
            primary_window.close();
        });
        TableView<Item> buytable = new TableView<>();
        buytable.setPlaceholder(new Label("No item avaliable to buy"));
        buytable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label amount = new Label("Your credit: " + current.getusercredit());

        TableColumn<Item, String> item_name_column = new TableColumn<>("Item name");
        item_name_column.setMinWidth(100);
        item_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> description_column = new TableColumn<>("Description");
        description_column.setMinWidth(100);
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Double> price_sold_column = new TableColumn<>("Price");
        price_sold_column.setMinWidth(100);
        price_sold_column.setCellValueFactory(new PropertyValueFactory<>("price_posted"));

        buytable.setItems(buyoblist);
        buytable.getColumns().addAll(item_name_column, description_column, price_sold_column);

        Button back_button = new Button("Back");
        back_button.setMinWidth(100);
        back_button.setOnAction(e -> menu());

        Button buy_button = new Button("Buy");
        buy_button.setMinWidth(100);
        buy_button.setOnAction(e -> {
            ObservableList<Item> temp;
            Item item = buytable.getSelectionModel().getSelectedItem();
            temp = buytable.getSelectionModel().getSelectedItems();

            if (item == null) {

                invalidinputprompt();
            } else {
                Double credit = current.getusercredit();

                if (credit < item.getPrice_posted()) {
                    notenoughcreditprompt();

                } else {
                    if (current.buy(item)) {
                        temp.forEach(buyoblist::remove);
                        amount.setText("Your credit: " + current.getusercredit());
                        operationsuccessfulprompt();

                    } else {
                        requestfailprompt();
                    }
                }

            }

        });

        Button neg_button = new Button("Negotiate with seller");
        neg_button.setMinWidth(100);

        neg_button.setOnAction(e -> {
            Item item = buytable.getSelectionModel().getSelectedItem();

            if (item == null) {
                invalidinputprompt();
            } else {
                if (!current.check_seller_online(item.getItem_code())) {
                    sellernotonlineprompt();
                } else {
                    //current.getCcl().startsession(item);
                }


            }
        });


        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(amount, back_button, buy_button, neg_button);

        buy_itemsvb.getChildren().addAll(buytable, buttons);
        Scene buyscene = new Scene(buy_itemsvb, 800, 500);
        primary_window.setScene(buyscene);
        primary_window.show();


    }


    /*public void chatsession() {
        Session session = current.getSession();
        Stage chatwindow = new Stage();
        chatwindow.initModality(Modality.APPLICATION_MODAL);
        chatwindow.setTitle("Chat session for item " + session.getItem().getName());
        BorderPane borderpane = new BorderPane();


        chatwindow.setOnCloseRequest(e -> {
            e.consume();
            chatwindow.close();
        });

        Button sendbutton = new Button("Send");
        sendbutton.setMinWidth(100);
        Button transaction = new Button("Complete");
        transaction.setMinWidth(100);


        Label message = new Label();
        message.setText("");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(20, 20, 20, 20));
        scrollPane.setContent(message);

        TextField textField = new TextField();
        textField.setMinWidth(600);
        textField.setMinHeight(100);

        VBox buttons = new VBox(10);
        buttons.setPadding(new Insets(20, 20, 20, 20));
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(sendbutton, transaction);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(scrollPane);

        VBox vBox1 = new VBox(10);
        vBox1.setPadding(new Insets(20, 20, 20, 20));
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(textField);

        sendbutton.setOnAction(e -> {
            current.getCcl().send_message_to_server(new Chatmessage(session, Chatmessage.MESSAGE, textField.getText()));
            //textField.clear();
        });





        borderpane.setRight(buttons);
        borderpane.setCenter(scrollPane);
        borderpane.setBottom(textField);

        Scene chatscene = new Scene(borderpane, 800, 800);
        chatwindow.setScene(chatscene);
        chatwindow.show();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (true) {

                    if (current.getCcl().getMsg() != null) {
                        Platform.runLater(() -> {
                            message.setText(message.getText() + "\n" + current.getCcl().getMsg().getMsg());

                        });

                    }

                        try{
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            break;
                        }



                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();


    }*/



}
