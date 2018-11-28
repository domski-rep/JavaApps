package travel_data;

import javafx.beans.property.SimpleStringProperty;

public class Table_TRAVELDATA {

    private final SimpleStringProperty LOCALE, COUNTRY, DATE_FROM,
            DATE_TO, REALM, COST, CURR, ID;
    private static int rows = 0;


    public Table_TRAVELDATA(
            String id,
            String locale,
            String country,
            String dateFrom,
            String dateTo,
            String realm,
            String cost,
            String curr
    ) {
        this.ID = new SimpleStringProperty(id);
        this.LOCALE = new SimpleStringProperty(locale);
        this.COUNTRY = new SimpleStringProperty(country);
        this.DATE_FROM = new SimpleStringProperty(dateFrom);
        this.DATE_TO = new SimpleStringProperty(dateTo);
        this.REALM = new SimpleStringProperty(realm);
        this.COST = new SimpleStringProperty(cost);
        this.CURR = new SimpleStringProperty(curr);

    }

    public String getLocale() {
        return LOCALE.get();
    }

    public String getCountry() {
        return COUNTRY.get();
    }

    public String getDateFrom() {
        return DATE_FROM.get();
    }

    public String getDateTo() {
        return DATE_TO.get();
    }

    public String getRealm() {
        return REALM.get();
    }

    public String getCost() {
        return COST.get();
    }

    public String getCurr() {
        return CURR.get();
    }
}
