package com.shabaya.powertips;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class ComboPrediction {
    private String id;
    private String title;


    @Exclude
    private transient ArrayList<Prediction> predictions;

    public ComboPrediction() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Prediction> getPredictions() {
        if (predictions == null) {
            predictions = new ArrayList<>();
        }
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalOdds() {
        double result = 0;
        if (this.predictions.size() > 0) {
            result = 1D;
            for (Prediction p : this.predictions) {
                result = result * p.getOdds();
            }
        }
        return result;
    }
}
