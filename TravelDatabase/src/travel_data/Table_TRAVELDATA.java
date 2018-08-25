package travel_data;

import javafx.beans.property.SimpleStringProperty;

public class Table_TRAVELDATA {

    private final SimpleStringProperty locale, country, dateFrom, dateTo, realm, cost, curr;
    private static int rows = 0;


    public Table_TRAVELDATA(
            String locale,
            String country,
            String dateFrom,
            String dateTo,
            String realm,
            String cost,
            String curr
    ) {
       this.locale= new SimpleStringProperty(locale);
       this.country  = new SimpleStringProperty(country);
       this.dateFrom = new SimpleStringProperty(dateFrom);
       this.dateTo = new SimpleStringProperty(dateTo);
       this.realm = new SimpleStringProperty(realm);
       this.cost = new SimpleStringProperty(cost);
       this.curr=new SimpleStringProperty(curr);
    rows++;
    }

    public String getLocale() {
        return locale.get();
    }


    public void setLocale(String locale) {
        this.locale.set(locale);
    }

    public String getCountry() {
        return country.get();
    }


    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getDateFrom() {
        return dateFrom.get();
    }


    public String getDateTo() {
        return dateTo.get();
    }

    public String getRealm() {
        return realm.get();
    }


    public String getCost() {
        return cost.get();
    }

    public String getCurr() {
        return curr.get();
    }

    public int getRows() {
        return rows;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom.set(dateFrom);
    }


    public void setDateTo(String dateTo) {
        this.dateTo.set(dateTo);
    }


    public void setRealm(String realm) {
        this.realm.set(realm);
    }


    public void setCost(String cost) {
        this.cost.set(cost);
    }


    public void setCurr(String curr) {
        this.curr.set(curr);
    }
}
