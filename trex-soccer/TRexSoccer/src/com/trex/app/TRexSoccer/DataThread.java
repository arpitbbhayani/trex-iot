package com.trex.app.TRexSoccer;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by devilo on 11/4/14.
 */
public class DataThread extends Observable implements Runnable {

    private static final String USER_AGENT = "TRexIOT/1.0";

    private static String META_URL = "http://192.168.1.8:4567/meta";
    private static String DATA_URL = "http://192.168.1.8:4567/data";

    JsonValue metaValueJson = null;
    JsonValue dataValueJson = null;

    static JsonReader jsonReader = null;
    URL url;


    public DataThread() {
        jsonReader = new JsonReader();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }



    public static JsonValue getMetaData() throws Exception {

        URL url = new URL(META_URL);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return jsonReader.parse(response.toString());
    }


    public static JsonValue getData() throws Exception {

        URL url = new URL(DATA_URL);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return jsonReader.parse(response.toString());
    }

    public void run() {

        try {

            while (true) {

                dataValueJson = getData();
                setChanged();
                notifyObservers(dataValueJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
