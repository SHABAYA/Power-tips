package com.shabaya.powertips;

public class SinglesPrediction {
    private String country;
    private String time;
    private String match;
    private String predict;
    private double odds;

    public SinglesPrediction(String country, String time, String match, String predict, double odds) {
        this.country = country;
        this.time = time;
        this.match = match;
        this.predict = predict;
        this.odds = odds;
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

    public void setPredict(String predict) {
        this.predict = predict;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }
}
