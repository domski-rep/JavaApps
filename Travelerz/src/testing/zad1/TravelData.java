package testing.zad1;



import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TravelData {
    private List<File> filesPath;
    //private List<Map<String,String>> readedData;
    private List<String>readedData;
    public TravelData(File file) {
        listOfFilesInFolder(file);
        readedData = new ArrayList<>();

        for (File files : filesPath) {
            Scanner sc = null;
            try {
                sc = new Scanner(files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        //    Map <String,String>readed = new LinkedHashMap<>();

            while (sc.hasNextLine()) {

                String reading = sc.nextLine();
                //String[] splitted = reading.split("\\t");
                readedData.add(reading);
                /*readed.put("locale",splitted[0]);
                readed.put("country",splitted[1]);
                readed.put("dateFrom",splitted[2]);
                readed.put("dateTo",splitted[3]);
                readed.put("place",splitted[4]);
                readed.put("cost",splitted[5]);
                readed.put("curr",splitted[6]);*/


            }
            //readetData.add(readed);
        }

    }


    //Metoda do wczytywania ścierzek plików w danym folderze
    private void listOfFilesInFolder(File folder) {
        filesPath = new ArrayList<>();
        for (File f : folder.listFiles())
            if (f.isDirectory()) listOfFilesInFolder(f);
            else filesPath.add(f);


    }

    private String countryTranslator(String country, String langTagFrom, String langTagTo) {

        for (Locale loc : Locale.getAvailableLocales())
            if (loc.getDisplayCountry(new Locale(langTagFrom)).equals(country))
                return loc.getDisplayCountry(new Locale(langTagTo));

        return country;
    }

    private String currencyTranslator(String money, String localeWeHave, String localeWeNeed) {

        NumberFormat format = NumberFormat.getInstance(new Locale(localeWeHave));
        ((DecimalFormat) format).setParseBigDecimal(true);

        try {
            BigDecimal  moneyAsDecimal = (BigDecimal) format.parse(money);
            NumberFormat outputFormat = NumberFormat.getNumberInstance(new Locale(localeWeNeed));

            return outputFormat.format(moneyAsDecimal);

        } catch (ParseException e) {
            return money;
        }


    }
    private String realmsTranslator(String realm, String localeWeHave, String localeWeNeed){
        ResourceBundle lands = ResourceBundle.getBundle("zad1.RealmTranslate",new Locale(localeWeHave.split("_")[0]));
        ResourceBundle toReturn = ResourceBundle.getBundle("zad1.RealmTranslate",new Locale(localeWeNeed.split("_")[0]));
        for(String r : lands.keySet())
            if (realm.equals(lands.getString(r)))
                return toReturn.getString(r);

        return realm;
    }


    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        List<String> toOutput = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        for(String s : readedData) {
            StringBuffer buff = new StringBuffer();
            String[] splitted =s.split("\\t");
            String locale = splitted[0];
            String country = splitted[1];
            String dateFrom = splitted[2];
            String dateTo = splitted[3];
            String land = splitted[4];
            String cost = currencyTranslator(splitted[5],locale.split("_")[0],loc.split("_")[0]);
            String curr = splitted[6];

            buff.append(countryTranslator(country,locale.split("_")[0],loc.split("_")[0]))
                    .append(" ").append(dateFrom).append(" ")
                    .append(dateTo).append(" ").append(realmsTranslator(land,locale,loc))
                    .append(" ").append(cost).append(" ").append(curr);
            toOutput.add(buff.toString());

            /*for (Map map : readetData){

             *//* buff.append(map.get("locale")).append(" ")
                    .append(countryTranslator((String)map.get("country"),loc,(String)map.get("locale")))
                    .append(" ").append(map.get("dateFrom")).append(" ").append(map.get("dateTo"))
                    .append(" ").append(map.get("place")).append(" ").append(map.get("cost"))
                    .append(" ").append(map.get("curr"));
            toOutput.add(buff.toString());*//*
        }*/

        }
        return toOutput;
    }

    public List<String> getReadedData(){

        return readedData;
    }

}
