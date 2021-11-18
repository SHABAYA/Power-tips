package com.shabaya.powertips;

public class Prediction {
    private String id;
    private String country;
    private String time;
    private String match;
    private String predict;
    private String comboId;
    private double odds;

    public Prediction(){

    }

    public Prediction(String country, String time, String match, String predict, String combo_id, double odds) {
        this.country = country;
        this.time = time;
        this.match = match;
        this.predict= predict;
        this.comboId = combo_id;
        this.odds = odds;
    }

    public String getComboId(){
        return comboId;
    }

    public void setCombo_id(String combo_id){
        this.comboId = combo_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getPredict() {
        return predict;
    }

    public void setPrediction(String predict) {
        this.predict = predict;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
