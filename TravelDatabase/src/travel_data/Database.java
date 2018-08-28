package travel_data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private TravelData travelData;
    private String url;
    private Connection connection;
    private final String[] columns = {"LOCALE", "COUNTRY", "DATE_FROM", "DATE_TO", "REALM", "COST", "CURR"};

    public Database(String url, TravelData travelData) {
        this.travelData = travelData;
        this.url = url;
    }

    protected void createDb() {

        try {

            connection = DriverManager.getConnection(url);
            Statement state = connection.createStatement();


            state.execute("CREATE TABLE TRAVELDATA(" +
                    "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                    "LOCALE VARCHAR(100), COUNTRY VARCHAR(100), " +
                    "DATE_FROM DATE, DATE_TO DATE, REALM VARCHAR(100), COST VARCHAR(100)," +
                    "CURR VARCHAR(100))");
            state.close();


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    // Insert data readed from file into database
    protected void insertIntoDb() {

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement state = connection.createStatement();

            for (String data : travelData.getReadedData()) {
                String[] val = data.split("\\t");
                state = connection.createStatement();
                state.execute("INSERT INTO TRAVELDATA(LOCALE, COUNTRY, DATE_FROM, DATE_TO, REALM, COST, CURR) VALUES("
                        + "'" + val[0] + "', " + "'" + val[1] + "', " + "'" + val[2] + "', " + "'"
                        + val[3] + "', " + "'" + val[4] + "', "
                        + "'" + val[5] + "', " + "'" + val[6] + "') ");
            }
            state.close();
            connection.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Main.errorAction("No data loaded from directory! Please load data.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected ObservableList<Table_TRAVELDATA> selectFromDB(String query) {
        Statement state = null;
        ResultSet result = null;
        List<Table_TRAVELDATA> resultList = new ArrayList<>();

        try {

            Connection connection = DriverManager.getConnection(url);
            state = connection.createStatement();
            result = state.executeQuery(query);

            while (result.next()) {
                Table_TRAVELDATA record = new Table_TRAVELDATA(
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8)
                );

                resultList.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (state != null) {
                try {
                    state.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        //console log
        for (Table_TRAVELDATA t : resultList)
            System.out.println(t.getLocale() + " "
                    + t.getCountry() + " " + t.getDateFrom() + " "
                    + t.getDateTo() + " " + t.getRealm() + " "
                    + t.getCost() + " " + t.getCurr());
        //========================================================

        
        ObservableList<Table_TRAVELDATA> toObserve = FXCollections.observableList(resultList);


        return toObserve;
    }

    public String[] getColumns() {
        return columns;
    }

}
