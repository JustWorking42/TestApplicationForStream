package com.example.testapplicationforstream.util;

import android.os.Handler;
import android.util.Log;

import com.example.testapplicationforstream.callbacks.LoadCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private LoadCallback loadCallback;
    private URL url;
    private HttpURLConnection connection;

    public HttpClient(LoadCallback loadCallback) {
        this.loadCallback = loadCallback;
    }

    public void setConnection() {
        final Handler handler = new Handler();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    url = new URL("https://images-api.nasa.gov/search?q=milky way&media_type=image");
                    connection = (HttpURLConnection) url.openConnection();
                    Log.d("HTTP", String.valueOf(connection.getResponseCode()));

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader inputStreamReader =
                                new InputStreamReader(connection.getInputStream());

                        BufferedReader in = new BufferedReader(inputStreamReader);
                        String line = in.readLine();
                        JSONParser jsonParser = new JSONParser();
                        jsonParser.parse(line);
                        inputStreamReader.close();
                        in.close();

                    } else {
                        Log.d("HTTP", connection.getResponseMessage());
                    }
                } catch (IOException e) {
                    Log.d("HTTP", String.valueOf(e));
                    e.printStackTrace();
                }
                finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadCallback.onDataLoad();
                        }
                    });
                }
            }
        });
        thread.start();
    }
}
