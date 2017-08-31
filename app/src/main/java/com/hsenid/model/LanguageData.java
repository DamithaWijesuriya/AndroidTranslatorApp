package com.hsenid.model;



public class LanguageData {
    private Languages data;

    public Languages getData() {
        return data;
    }

    public void setData(Languages data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LanguageData{" +
                "data=" + data +
                '}';
    }
}
