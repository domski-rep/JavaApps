package travel_data;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.util.*;


public class Main extends Application {
    private static final String[] columnNames = {"ID", "LOCALE", "COUNTRY", "DATE FROM", "DATE TO", "REALM", "COST", "CURR"};
    private static VBox tableBoxView;
    private static Scene scene;
    private static boolean tableVisible;
    private static Button tableButton;
    private static Database db;
    private static TableColumn idCol, localeCol, countryCol, dateFromCol, dateToCol, realmCol, costCol, currCol;
    private static TableView<Table_TRAVELDATA> dataTable;
    private static TravelData travelData;

    @Override
    public void start(Stage primaryStage) {

        //DB AND TRAVELDATA INSTANCES ===================
        String url = "jdbc:derby:C:\\TravelerZ Co.\\Database\\TravelData;create=true;user=test;password=test";
        File dataDir = new File("data");
        travelData = new TravelData(dataDir);
        db = new Database(url, travelData);
        tableVisible = false;
        //Canvas ======================================
        BorderPane layout = new BorderPane();
        scene = new Scene(layout, 750, 500);
        VBox menu = new VBox();
        layout.getStyleClass().add("layout-canvas");
        scene.getStylesheets().add("travel_data/styles/AppStyles.css");
        ImageView logo = new ImageView();
        Image mainImage = new Image("travel_data/images/brand_logo_incomplete02_2.png");
        logo.setImage(mainImage);
        logo.setFitHeight(150);
        logo.setFitWidth(230);
        logo.setY(layout.getHeight() / 2.6);
        logo.setX(layout.getWidth() / 2.6);
        layout.getChildren().add(logo);
        // ============================================
        //Buttons =======================
        Button readFileButton = new Button("Read file(s)");
        readFileButton.setPrefSize(100, 30);
        Button insertIntoDbButton = new Button("Insert into DB");
        insertIntoDbButton.setPrefSize(100, 30);
        Button loadFromDbButton = new Button("Load from DB");
        loadFromDbButton.setPrefSize(100, 30);
        tableButton = new Button("Show Table");
        tableButton.setPrefSize(100, 30);
        // ==============================
        // Buttons disposing =======================================
        menu.getChildren().addAll(tableButton, readFileButton, insertIntoDbButton, loadFromDbButton);
        menu.setAlignment(Pos.BASELINE_LEFT);
        menu.setSpacing(10);
        menu.setPadding(new Insets(10, 0, 0, 10));
        tableButton.setTranslateY(30);
        readFileButton.setTranslateY(50);
        insertIntoDbButton.setTranslateY(50);
        loadFromDbButton.setTranslateY(50);
        //==========================================================
        //Buttons settings =====================================
        readFileButton.setOnAction(Main::startButtonAction);
        insertIntoDbButton.setOnAction(e -> db.insertIntoDb());
        loadFromDbButton.setOnAction(e -> selectAndShowFromDB("SELECT * FROM TRAVELDATA"));
        tableButton.setOnAction(e -> dataTable(primaryStage, scene, layout));
        // ======================================================
        // Table settings ===============
        tableBoxView = new VBox();
        tableBoxView.setVisible(tableVisible);
        tableSchema();
        layout.setCenter(tableBoxView);
        // ==============================
        // some more settings...
        Label signature = new Label("Created and developed by Bartosz Domański.");
        signature.setPadding(new Insets(0, 0, 0, 30));
        signature.setFont(new Font("Calibri", 12));
        layout.setLeft(menu);
        layout.setBottom(signature);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("travel_data/images/icon.jpg"));
        primaryStage.setTitle("TravelerZ Co.");
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(450);
        primaryStage.setMaxWidth(750);
        primaryStage.setMaxHeight(500);
        primaryStage.show();
        //!confirmation window
        primaryStage.setOnCloseRequest(Main::exitAction);
        //-----------------------------------------------
    }


    // Table button action
    private static void dataTable(Stage primaryStage, Scene scene, BorderPane layout) {
        if (!tableVisible) {
            tableVisible = true;
            tableBoxView.setVisible(tableVisible);
            tableButton.setText("Hide Table");
        } else {
            tableButton.setText("Show Table");
            tableVisible = false;
            tableBoxView.setVisible(false);
        }

    }

    // Reading data from directory
    private static void startButtonAction(ActionEvent actionEvent) {
        travelData.startReadingFiles();
        // Main task form my academy ==============================================
        for (String locale : Arrays.asList("pl_PL", "en_GB")) {
            List<String> odlist = travelData.getOffersDescriptionsList(locale);
            for (String od : odlist) System.out.println(od);
        }
        //========================================================================

        if (!db.isDatabaseFound()) {
            db.createDb();
            System.out.println("Creating database...");
        } else System.out.println("Database already exist!");

    }

    //Reading data directly from database
    private void selectAndShowFromDB(String query) {
        tableSchema();
        viewRecords(query);
    }

    //Basic schema of the table
    private static void tableSchema() {

        tableBoxView.getChildren().clear();
        dataTable = new TableView<>();
        dataTable.setEditable(true);
        Label tableLabel = new Label("TravelerZ Co. Data From Database by Derby");
        tableLabel.setFont(new Font("Comic Sans", 18));
        idCol = new TableColumn(columnNames[0]);
        localeCol = new TableColumn(columnNames[1]);
        countryCol = new TableColumn(columnNames[2]);
        dateFromCol = new TableColumn(columnNames[3]);
        dateToCol = new TableColumn(columnNames[4]);
        realmCol = new TableColumn(columnNames[5]);
        costCol = new TableColumn(columnNames[6]);
        currCol = new TableColumn(columnNames[7]);
        dataTable.getColumns().addAll(idCol, localeCol, countryCol, dateFromCol, dateToCol, realmCol, costCol, currCol);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 30, 0, 10));
        vbox.getChildren().addAll(tableLabel, dataTable);
        tableBoxView.getChildren().addAll(vbox);

    }

    // View readed records
    private static void viewRecords(String query) {

        ObservableList<Table_TRAVELDATA> resultListFromDb = db.selectFromDB(query);

        idCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("id"));
        localeCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("locale"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("country"));
        dateFromCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("dateFrom"));
        dateToCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("dateTo"));
        realmCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("realm"));
        costCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("cost"));
        currCol.setCellValueFactory(new PropertyValueFactory<Table_TRAVELDATA, String>("curr"));
        dataTable.setItems(resultListFromDb);
    }

    // Quit confirmation
    private static void exitAction(WindowEvent windowEvent) {

        Stage quit = new Stage();
        quit.initModality(Modality.APPLICATION_MODAL);
        quit.setTitle("SURE?");
        quit.setMinWidth(300);
        quit.setMinHeight(200);
        Button y = new Button("YES!");
        y.setPrefSize(100, 50);
        y.setId("button_y");
        Button n = new Button("NOOO!");
        n.setPrefSize(100, 50);
        n.setId("button_n");
        Label quitLabel = new Label("Confirm! Are you sure you want to quit now?");
        quitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        y.setOnAction(o -> System.exit(0));
        n.setOnAction(o -> quit.close());
        VBox basicLayout = new VBox(10);
        HBox layoutOnBasicLayout = new HBox();
        basicLayout.setSpacing(40);
        layoutOnBasicLayout.setSpacing(20);
        layoutOnBasicLayout.getChildren().addAll(y, n);
        layoutOnBasicLayout.setAlignment(Pos.CENTER);
        basicLayout.getChildren().addAll(quitLabel, layoutOnBasicLayout);
        basicLayout.setAlignment(Pos.CENTER);
        basicLayout.getStyleClass().add("basicLayout-confirmation");
        Scene confirm = new Scene(basicLayout);
        confirm.getStylesheets().add("travel_data/styles/ConfirmationWindowStyles.css");
        quit.setScene(confirm);
        quit.showAndWait();
        windowEvent.consume();

    }

    //Warning Action
    protected static void errorAction(String msg) {

        Stage error = new Stage();
        error.initModality(Modality.APPLICATION_MODAL);
        error.setTitle("Warning!");
        error.setMinWidth(200);
        error.setMinHeight(200);
        Button ok = new Button("OK!");
        ok.setPrefSize(100, 50);
        Label errorLabel = new Label(msg);
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        ok.setOnAction(o -> error.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(errorLabel, ok);
        layout.setAlignment(Pos.CENTER);
        Scene confirm = new Scene(layout);
        error.setScene(confirm);
        error.showAndWait();

    }

}
