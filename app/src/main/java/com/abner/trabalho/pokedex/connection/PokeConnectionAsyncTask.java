package com.abner.trabalho.pokedex.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.abner.trabalho.pokedex.model.Pokemon;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PokeConnectionAsyncTask extends AsyncTask<String, Void, String> {

    private OnRequestListener listener;

    public PokeConnectionAsyncTask(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                return IOUtils.toString(inputStream, "UTF-8");
            } else return null;
        } catch (Exception e) {
            Log.v("APP_POKEDEX", "Something went wrong inside doInBackground");
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject object = new JSONObject(s);
            listener.onRequestFinished(object);
        } catch(Exception e) {
            listener.onRequestFinished(null);
        }
    }

    public interface OnRequestListener {
        void onRequestFinished(JSONObject object);
    }
}
