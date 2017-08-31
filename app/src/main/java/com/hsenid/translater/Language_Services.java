package com.hsenid.translater;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.hsenid.model.LanguageData;
import com.hsenid.model.LangugeDetails;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;



public class Language_Services extends AsyncTask<Void, Void, String[]> {
    protected HashMap<String, Object> result;
    private JSONObject jsonObject;
    private String[] languageArray;
    private Spinner fromLanguage;
    private Spinner toLanguage;
    private Context context;
    private String translateType;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;
    private LanguageData langs;


    public Language_Services(Spinner fromLanguage, Spinner toLanguage, Context context, String translateType) {
        this.fromLanguage = fromLanguage;
        this.context = context;
        this.toLanguage = toLanguage;
        this.translateType = translateType;
    }

    public String[] getLanguages() {
        try {
            if ("google".equals(translateType)) {
                url = new URL("https://translation.googleapis.com/language/translate/v2/languages?key=AIzaSyAvzIwL1PW3q3_TyKOCREHMV0sxGtmFpQQ&target=en");

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                Gson gson = new Gson();
                langs = gson.fromJson(bufferedReader, LanguageData.class);
                List<LangugeDetails> languages = langs.getData().getLanguages();

                result = new HashMap<String, Object>();
                for (int i = 0; i < languages.size(); i++) {
                    result.put(languages.get(i).getLanguage(), languages.get(i).getName());
                }
                languageArray = result.values().toArray(new String[0]);

            } else {
                url = new URL("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca" +
                        "07e07cddb27fd1cd769dd2b43d5c765&ui=en");

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                jsonObject = new JSONObject(bufferedReader.readLine()).getJSONObject("langs");
                result = new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
                languageArray = result.values().toArray(new String[0]);

            }
            httpURLConnection.disconnect();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return languageArray;
    }

    public Object getKeyFromValue(Object value) {
        for (Object o : result.keySet()) {
            if (result.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        return getLanguages();
    }


    @Override
    protected void onPostExecute(String[] langs) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, langs);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fromLanguage.setAdapter(spinnerArrayAdapter);
        toLanguage.setAdapter(spinnerArrayAdapter);
    }
}
