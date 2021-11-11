package com.shabaya.powertips;

import java.util.ArrayList;

public class ComboPrediction {
    private String title;
   private ArrayList<Prediction> predictions;

    public ComboPrediction(String title, ArrayList<Prediction> predictions) {
        this.title = title;
        this.predictions = predictions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Prediction> getPredictions() {
        if(predictions == null){
            predictions = new ArrayList<>();
        }
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }

    public double getTotalOdds(){
        double result = 0;
        if(this.predictions.size()>0) {
            result = 1D;
            for (Prediction p : this.predictions) {
                result = result *p.getOdds();
            }
        }
        return result ;
    }
}
