package travel_data;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private static final String[] columnNames = {"NR","LOCALE", "COUNTRY", "DATE FROM", "DATE TO", "REALM", "COST", "CURR"};
    private static VBox tableBoxView;
    private static Scene scene;
    private static boolean tableVisible;
    private static Button tableButton;
    private static Database db;
    private static List<TableColumn<Integer, String>> tableColumnList;
    private static int rowsInTable = 0;



    @Override
    public void start(Stage primaryStage)  {

        tableVisible = false;

        //Canvas ======================================
        BorderPane layout = new BorderPane();
        scene = new Scene(layout, 750, 500);
        VBox menu = new VBox();

        // ============================================


        //Buttons =======================
        Button button1 = new Button("Read file(s)");
        button1.setPrefSize(100, 30);
        Button button2 = new Button("Insert into DB");
        button2.setPrefSize(100, 30);
        Button button3 = new Button("Load from DB");
        button3.setPrefSize(100, 30);
        tableButton = new Button("Show Table");
        tableButton.setPrefSize(100, 30);


        // ==============================

        // Buttons disposing =======================================
        menu.getChildren().addAll(tableButton,button1, button2, button3);
        menu.setAlignment(Pos.BASELINE_LEFT);
        menu.setSpacing(10);
        tableButton.setTranslateY(30);
        button1.setTranslateY(50);
        button2.setTranslateY(50);
        button3.setTranslateY(50);
        //==========================================================


        //Buttons settings =====================================
        button1.setOnAction(Main::startButtonAction);
        button2.setOnAction(e -> db.insertIntoDb());
        button3.setOnAction(e -> selectAndShowFromDB("SELECT * FROM TRAVELDATA"));
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
        signature.setFont(new Font("Calibri", 12));
        layout.setLeft(menu);
        layout.setBottom(signature);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TravelerZ Co.");
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
        File dataDir = new File("data");
        TravelData travelData = new TravelData(dataDir);

    // Main task form my academy ==============================================
        for (String locale : Arrays.asList("pl_PL", "en_GB")) {
            List<String> odlist = travelData.getOffersDescriptionsList(locale);
            for (String od : odlist) System.out.println(od);
        }
    //========================================================================
        String url = "jdbc:derby:C:\\TravelerZ Co.\\Database\\TravelData;create=true;user=test;password=test";
        db = new Database(url, travelData);
        db.createDb();

    }

    //Reading data directly from database
    private void selectAndShowFromDB(String query) {
        tableSchema();
        viewRecords(query);
    }

    //Basic schema of the table
    private static void tableSchema() {
        tableColumnList = new ArrayList<>();
        tableBoxView.getChildren().clear();


        TableView table = new TableView();
        table.setEditable(true);

        for (int i = 0; i < rowsInTable; i++) {
            table.getItems().add(i);
        }

        Label tableLabel = new Label("TravelerZ Co. Data From Database by Derby");
        tableLabel.setFont(new Font("Comic Sans", 18));


        tableColumnList.add(new TableColumn<Integer, String>(columnNames[0]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[1]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[2]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[3]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[4]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[5]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[6]));
        tableColumnList.add(new TableColumn<Integer, String>(columnNames[7]));

        table.getColumns().addAll(tableColumnList);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(tableLabel, table);

        tableBoxView.getChildren().addAll(vbox);

    }
    // View readed records
    private static void viewRecords(String query) {

        List<List<String>> resultListFromDb = db.selectFromDB(query);


        rowsInTable = resultListFromDb.get(0).size();
        for (int i = 0; i < tableColumnList.size(); i++) {
            int count = i;
               tableColumnList.get(i).setCellValueFactory(e -> {
                int rowIdx = e.getValue();
                return new ReadOnlyStringWrapper(resultListFromDb.get(count).get(rowIdx));
            });
        }

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

        Button n = new Button("NOOO!");
        n.setPrefSize(100, 50);


        Label quitLabel = new Label("Confirm! Are you sure you want to quit now?");
        quitLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        y.setOnAction(o -> System.exit(0));
        n.setOnAction(o -> quit.close());
        VBox layout = new VBox(10);
        HBox layout2 = new HBox();
        layout.setSpacing(40);
        layout2.setSpacing(20);
        layout2.getChildren().addAll(y, n);
        layout2.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(quitLabel, layout2);
        layout.setAlignment(Pos.CENTER);

        Scene confirm = new Scene(layout);

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