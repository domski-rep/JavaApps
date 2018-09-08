package travel_data;

import javafx.beans.property.SimpleStringProperty;

public class Table_TRAVELDATA {

    private final SimpleStringProperty  LOCALE, COUNTRY, DATE_FROM,
            DATE_TO, REALM, COST, CURR,ID;
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
        this.LOCALE= new SimpleStringProperty(locale);
        this.COUNTRY= new SimpleStringProperty(country);
        this.DATE_FROM= new SimpleStringProperty(dateFrom);
        this.DATE_TO= new SimpleStringProperty(dateTo);
        this.REALM = new SimpleStringProperty(realm);
        this.COST = new SimpleStringProperty(cost);
        this.CURR = new SimpleStringProperty(curr);


    }

    // GETTERS...
    public String getId() { return ID.get(); }

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


    // SETTERS... for future usage

    public void setCountry(String country) {
        this.COUNTRY.set(country);
    }

    public void setDateFrom(String dateFrom) {
        this.DATE_FROM.set(dateFrom);
    }

    public void setLocale(String locale) {
        this.LOCALE.set(locale);
    }

    public void setDateTo(String dateTo) {
        this.DATE_TO.set(dateTo);
    }

    public void setRealm(String realm) {
        this.REALM.set(realm);
    }

    public void setCost(String cost) {
        this.COST.set(cost);
    }

    public void setCurr(String curr) {
        this.CURR.set(curr);
    }
}
