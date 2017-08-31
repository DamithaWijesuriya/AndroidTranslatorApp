package com.hsenid.translater;

import android.os.AsyncTask;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hsenid.model.TranslationData;
import com.hsenid.model.TranslateWords;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Translation_Services extends AsyncTask<Void, Void, String[]> {
    private String inputLanguage;
    private String outputLanguage;
    private String display;
    private TranslateWords convertedWord = new TranslateWords();
    private TranslationData translationData;
    private Gson gson = new Gson();
    private EditText editText;
    private String translateType;

    public Translation_Services(String inputLanguage, String outputLanguage, String display, EditText editText, String translateType) {
        this.inputLanguage = inputLanguage;
        this.outputLanguage = outputLanguage;
        this.display = display;
        this.editText = editText;
        this.translateType = translateType;
    }

    public String[] doTranslateYandex(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07" +
                    "cddb27fd1cd769dd2b43d5c765&text=" + wordToConvert + "&lang=" + inputLang + "-" + outputLang);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            convertedWord = gson.fromJson(br.readLine(), TranslateWords.class);
            conn.disconnect();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedWord.getText();
    }

    public String[] doTranslateGoogle(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL("https://translation.googleapis.com/language/translate/v2?key=AIzaSyAvzIwL1PW3q3_TyKOCREHMV0sxGtmFpQQ&q=" + wordToConvert +
                    "&target=" + outputLang + "&source=" + inputLang);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            translationData = gson.fromJson(br, TranslationData.class);
            translationData.getData().getTranslations();

            conn.disconnect();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] strArray = new String[]{translationData.getData().getTranslations().get(0).getTranslatedText().toString()};
        return strArray;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        if ("yandex".equals(translateType)) {
            return doTranslateYandex(inputLanguage, outputLanguage, display);
        } else {
            return doTranslateGoogle(inputLanguage, outputLanguage, display);
        }
    }

    @Override
    protected void onPostExecute(String[] s) {
        editText.setText(s[0]);
    }
}
