package travel_data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    protected List<List<String>> selectFromDB(String query) {
        Statement state = null;
        ResultSet result = null;
        List<List<String>> resultList = new ArrayList<>();
        List<String> nr_col = new ArrayList<>();
        List<String> loc_col = new ArrayList<>();
        List<String> country_col = new ArrayList<>();
        List<String> date_from_col = new ArrayList<>();
        List<String> date_to_col = new ArrayList<>();
        List<String> realm_col = new ArrayList<>();
        List<String> cost_col = new ArrayList<>();
        List<String> curr_col = new ArrayList<>();
        try {

            Connection connection = DriverManager.getConnection(url);
            state = connection.createStatement();
            result = state.executeQuery(query);

            while (result.next()) {

                nr_col.add(result.getString(1));
                loc_col.add(result.getString(2));
                country_col.add(result.getString(3));
                date_from_col.add(result.getString(4));
                date_to_col.add(result.getString(5));
                realm_col.add(result.getString(6));
                cost_col.add(result.getString(7));
                curr_col.add(result.getString(8));


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

        resultList.addAll(
                Arrays.asList(nr_col,loc_col, country_col, date_from_col, date_to_col, realm_col, cost_col, curr_col));
      /*  for(List<String>res : resultList)
            System.out.println(res);

      */
        return resultList;
    }

    public String[] getColumns() {
        return columns;
    }

}
