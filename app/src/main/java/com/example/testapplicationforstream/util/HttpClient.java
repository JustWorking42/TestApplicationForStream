package com.example.testapplicationforstream.util;

import android.content.Context;
import android.util.Log;

import com.example.testapplicationforstream.entity.NASAItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpClient {
    private URL url;
    private Context context;
    private HttpURLConnection connection;

    public void setConnection() {
        ArrayList<NASAItem> nasaItems;
        final StringBuilder stringBuilder = new StringBuilder();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    url = new URL("https://images-api.nasa.gov/search?q=moon&media_type=image&page=1");
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("TAG", String.valueOf(connection.getResponseCode()));
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                        BufferedReader in = new BufferedReader(inputStreamReader);
                        String line = in.readLine();
                        stringBuilder.append(line);
                        while (line != null){
                            Log.d("TAG", "KUKU");
                            line = in.readLine();
                            Log.d("TAG", String.valueOf(line));
                            stringBuilder.append(line);
                        }

                        String res = new String(stringBuilder);
                        JSONParser jsonParser = new JSONParser();
                        jsonParser.parse(res);
                        System.out.println(res);
                        Log.d("TAG", res);
                        //Если запрос выполнен удачно, читаем полученные данные и далее, делаем что-то
                    } else {
                        Log.d("TAG", "JOPA");
                        //Если запрос выполнен не удачно, делаем что-то другое
                    }
                } catch (IOException e) {
                    Log.d("TAG", String.valueOf(e));

                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
