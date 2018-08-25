package travel_data;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class TravelData {

    private List<String> readedData;


    public TravelData(File file) {
        readedData = new ArrayList<>();

        for (File files : getListOfFilesInDirectory(file)) {
            Scanner sc = null;
            try {
                sc = new Scanner(files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (sc.hasNextLine()) {
                String reading = sc.nextLine();
                readedData.add(reading);
            }
        }
    }


    //Recursive method to get directory structure
    private List<File> getListOfFilesInDirectory(File dir) {

        List<File> filesPath = new ArrayList<>();
        for (File f : dir.listFiles())
            if (f.isDirectory()) getListOfFilesInDirectory(f);
            else filesPath.add(f);

        return filesPath;
    }

    private String currencyTranslator(String money, String localeWeHave, String localeWeNeed) {

        NumberFormat format = NumberFormat.getInstance(new Locale(localeWeHave));
        ((DecimalFormat) format).setParseBigDecimal(true);

        try {
            BigDecimal moneyAsDecimal = (BigDecimal) format.parse(money);
            NumberFormat outputFormat = NumberFormat.getNumberInstance(new Locale(localeWeNeed));

            return outputFormat.format(moneyAsDecimal);

        } catch (ParseException e) {
            return money;
        }


    }
    private String countryTranslator(String country, String langTagFrom, String langTagTo) {

        for (Locale loc : Locale.getAvailableLocales())
            if (loc.getDisplayCountry(new Locale(langTagFrom)).equals(country))
                return loc.getDisplayCountry(new Locale(langTagTo));

        return country;
    }

    private String realmsTranslator(String realm, String localeWeHave, String localeWeNeed){
        ResourceBundle lands = ResourceBundle.getBundle("travel_data.RealmTranslate",new Locale(localeWeHave.split("_")[0]));
        ResourceBundle toReturn = ResourceBundle.getBundle("travel_data.RealmTranslate",new Locale(localeWeNeed.split("_")[0]));
        for(String r : lands.keySet())
            if (realm.equals(lands.getString(r)))
                return toReturn.getString(r);

        return realm;
    }

    protected List<String> getOffersDescriptionsList(String loc) {
        List<String> toOutput = new ArrayList<>();


        for (String s : readedData) {
            StringBuffer buff = new StringBuffer();
            String[] splitted = s.split("\\t");
            String locale = splitted[0];
            String country = splitted[1];
            String dateFrom = splitted[2];
            String dateTo = splitted[3];
            String land = splitted[4];
            String cost = currencyTranslator(splitted[5], locale.split("_")[0], loc.split("_")[0]);
            String curr = splitted[6];

            buff.append(countryTranslator(country, locale.split("_")[0], loc.split("_")[0]))
                    .append(" ").append(dateFrom).append(" ")
                    .append(dateTo).append(" ").append(realmsTranslator(land, locale, loc))
                    .append(" ").append(cost).append(" ").append(curr);
            toOutput.add(buff.toString());
        }
        return toOutput;
    }

    public List<String> getReadedData(){

        return readedData;
    }
}
