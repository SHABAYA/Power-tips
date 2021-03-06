package com.shabaya.powertips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OverPredictionAdapter extends RecyclerView.Adapter<OverPredictionAdapter.MyViewHolder> {
    private ArrayList<OverPrediction> predictionsList;
    private View view;
    private Context context;
    private LayoutInflater inflater;

    public OverPredictionAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        predictionsList = new ArrayList<>();
    }

    public void setPredictionsList(ArrayList<OverPrediction> predictionsList) {
        this.predictionsList = predictionsList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView countryText;
        private TextView timeText;
        private TextView predictionText;
        private TextView oddsText;
        private TextView matchText;

        public MyViewHolder(final View view){
            super(view);
            countryText = view.findViewById(R.id.textView0);
            timeText = view.findViewById(R.id.textView1);
            matchText = view.findViewById(R.id.textView2);
            predictionText = view.findViewById(R.id.textView3);
            oddsText = view.findViewById(R.id.textView4);
        }
    }

    @NonNull
    @Override
    public OverPredictionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.over_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverPredictionAdapter.MyViewHolder holder, int position) {
        OverPrediction prediction = predictionsList.get(position);

        String country = prediction.getCountry();
        holder.countryText.setText(country);

        String time = prediction.getTime();
        holder.timeText.setText(time);
        String match = prediction.getMatch();
        holder.matchText.setText(match);
        String predict = prediction.getPredict();
        holder.predictionText.setText(predict);
        double odds = prediction.getOdds();
        holder.oddsText.setText(String.valueOf(odds));
    }

    @Override
    public int getItemCount() {
        return predictionsList.size();
    }

}
