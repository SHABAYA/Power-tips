package com.shabaya.powertips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ComboRecyclerAdapter extends RecyclerView.Adapter<ComboRecyclerAdapter.MyViewHolder> {
    private ArrayList<ComboPrediction> comboPredictionsList;
    private View view;
    private Context context;
    private LayoutInflater inflater;
    RecyclerAdapter recyclerAdapter;

    public ComboRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        comboPredictionsList = new ArrayList<>();
    }

    public void setComboPredictionsList(ArrayList<ComboPrediction> comboPredictionsList) {
        this.comboPredictionsList = comboPredictionsList;
        notifyDataSetChanged();
    }

    public void addData(ComboPrediction comboPrediction) {
        this.comboPredictionsList.add(0,comboPrediction);
        notifyItemInserted(0);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView combo_title;
        RecyclerView predictions_recycler_view;
        private TextView total_odds;

        public MyViewHolder(final View view) {
            super(view);
            combo_title = view.findViewById(R.id.combo_title);
            total_odds = view.findViewById(R.id.total_odds);
            predictions_recycler_view = view.findViewById(R.id.predictions_recycler_view);
            predictions_recycler_view.hasFixedSize();
            predictions_recycler_view.hasNestedScrollingParent();
            predictions_recycler_view.setHasFixedSize(true);
            predictions_recycler_view.setNestedScrollingEnabled(false);
            predictions_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            predictions_recycler_view.setLayoutManager(layoutManager);

            recyclerAdapter = new RecyclerAdapter(context);
            predictions_recycler_view.setAdapter(recyclerAdapter);

        }
    }

    @NonNull
    @Override
    public ComboRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.combos_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboRecyclerAdapter.MyViewHolder holder, int position) {
        ComboPrediction prediction = comboPredictionsList.get(position);
        String title = prediction.getTitle();
        holder.combo_title.setText(title);
        double total = round(prediction.getTotalOdds(),2);
        holder.total_odds.setText(String.valueOf(total));
        ArrayList<Prediction> comboPredictions = prediction.getPredictions();
        recyclerAdapter.setPredictionsList(comboPredictions);



    }

    @Override
    public int getItemCount() {
        return comboPredictionsList.size();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
