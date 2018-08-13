/**
 *
 *  @author Domański Bartosz S16014
 *
 */

package testing.zad1;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Main extends Application {

  public static void main(String[] args)  {
    /*File dataDir = new File("data");
    TravelData travelData = new TravelData(dataDir);
    String dateFormat = "yyyy-MM-dd";
    for (String locale : Arrays.asList("pl_PL", "en_GB")) {
      List<String> odlist = travelData.getOffersDescriptionsList(locale, dateFormat);
      for (String od : odlist) System.out.println(od);
    }
    // --- część bazodanowa
    String url = "jdbc:derby:C:\\DerbyDatabase\\TravelData;create=true;user=test;password=test";
    Database db = new Database(url, travelData);
    db.create();
    db.showGui();*/

    launch(args);


 }

  @Override
  public void start(Stage primaryStage)  {

      List<Integer> intValues = Arrays.asList(1, 2, 3, 4, 5);
      List<String> stringValues = Arrays.asList("One", "Two", "Three", "Four", "Five");

      TableView<Integer> table = new TableView<>();
      for (int i = 0; i < intValues.size() && i < stringValues.size(); i++) {
        table.getItems().add(i);
      }

      TableColumn<Integer, Number> intColumn = new TableColumn<>("Value");
      intColumn.setCellValueFactory(cellData -> {
        Integer rowIndex = cellData.getValue();
        return new ReadOnlyIntegerWrapper(intValues.get(rowIndex));
      });

      TableColumn<Integer, String> nameColumn = new TableColumn<>("Name");
      nameColumn.setCellValueFactory(cellData -> {
        Integer rowIndex = cellData.getValue();
        return new ReadOnlyStringWrapper(stringValues.get(rowIndex));
      });

      table.getColumns().add(intColumn);
      table.getColumns().add(nameColumn);

      primaryStage.setScene(new Scene(new BorderPane(table), 600, 600));
      primaryStage.show();
    }
  }

