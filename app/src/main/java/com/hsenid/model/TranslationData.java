package com.hsenid.model;



public class TranslationData {

    private Translations data;

    public Translations getData() {
        return data;
    }

    public void setData(Translations data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TranslationData{" +
                "data=" + data +
                '}';
    }
}
