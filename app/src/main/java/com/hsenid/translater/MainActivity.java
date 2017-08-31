package com.hsenid.translater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private String inputLanguage;
    private String outputLanguage;
    private RadioButton yandexRadioButton;
    private RadioButton googleButton;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private Language_Services languageServices;
    private String translateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLanguages(View view) {
        googleButton = (RadioButton) findViewById(R.id.radioGoogle);
        yandexRadioButton = (RadioButton) findViewById(R.id.radioYandX);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        if (yandexRadioButton.isChecked()) {
            translateMode = "yandex";
        } else {
            translateMode = "google";
        }
        //googleButton.setEnabled(false);

        languageServices = new Language_Services(spinnerFrom, spinnerTo, MainActivity.this, translateMode);
        languageServices.execute();
    }

    public void doTranslate(View view) {
        display = (EditText) findViewById(R.id.txtDisplay);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFromLang);
        spinnerTo = (Spinner) findViewById(R.id.spinnerToLang);

        inputLanguage = languageServices.getKeyFromValue(spinnerFrom.getSelectedItem()).toString();
        outputLanguage = languageServices.getKeyFromValue(spinnerTo.getSelectedItem()).toString();
        String displayValue = display.getText().toString();

        Translation_Services translation_services = new Translation_Services(inputLanguage, outputLanguage, displayValue, display, translateMode);
        translation_services.execute();
    }
}

