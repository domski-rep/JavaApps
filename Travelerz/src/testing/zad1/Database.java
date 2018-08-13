package testing.zad1;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database extends JFrame {

    private TravelData travelData;
    private String url;

    public Database(String url, TravelData travelData) {
        this.travelData = travelData;
        this.url = url;
    }

    private void createDb() {


        try {

            Connection connection = DriverManager.getConnection(url);
            Statement state = connection.createStatement();


            state.execute("CREATE TABLE TRAVELDATA(" +
                    "ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                    "LOCALE VARCHAR(100), COUNTRY VARCHAR(100), " +
                    "DATE_FROM DATE, DATE_TO DATE, REALM VARCHAR(100), COST VARCHAR(100)," +
                    "CURR VARCHAR(100))");
            state.close();

            for (String data : travelData.getReadedData()) {
                String[] val = data.split("\\t");
                state = connection.createStatement();
                state.execute("INSERT INTO TRAVELDATA(LOCALE, COUNTRY, DATE_FROM, DATE_TO, REALM, COST, CURR) VALUES("
                        + "'" + val[0] + "', " + "'" + val[1] + "', " + "'" + val[2] + "', " + "'"
                        + val[3] + "', " + "'" + val[4] + "', "
                        + "'" + val[5] + "', " + "'" + val[6] + "') ");
                state.close();

            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

       }
    }


    public void create() {
        createDb();
    }

    public void showGui() {
    String [] columnNames = { "NR","COUNTRY","DATE FROM","DATE TO","REALM","COST","CURR"   };

    Object [][] data= new Object[10][10];
    JTable table = new JTable(data,columnNames);
    Container container = new Container();
    TableColumn column = null;

        JScrollPane scrollPane = new JScrollPane(table);




        for(int i = 0; i<columnNames.length;i++){

            column=table.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }
        add(scrollPane);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
}
