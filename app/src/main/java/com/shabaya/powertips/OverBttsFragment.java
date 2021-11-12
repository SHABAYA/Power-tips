package com.shabaya.powertips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OverBttsFragment extends Fragment {

    private ArrayList<Prediction> predictionsList;
    private RecyclerView prediction_recycler_view;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_over_btts, container, false);

        initialiseRecyclerView();
        return view;

    }

    private void initialiseRecyclerView() {
        prediction_recycler_view= view.findViewById(R.id.over_recycler_view);
        prediction_recycler_view.hasFixedSize();
        prediction_recycler_view.hasNestedScrollingParent();
        prediction_recycler_view.setHasFixedSize(true);
        prediction_recycler_view.setNestedScrollingEnabled(false);
        prediction_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        prediction_recycler_view.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getContext());
        prediction_recycler_view.setAdapter(recyclerAdapter);
        getPrediction();
    }

    public void getPrediction() {
        predictionsList = new ArrayList<>();
        predictionsList.add(new Prediction("England","13:00", "Arsenal vs Chelsea", "over 2.5", 1.87));
        predictionsList.add(new Prediction("Germany","14:00", "Leverkusen vs Hertha Berlin", "btts", 1.51));

        recyclerAdapter.setPredictionsList(predictionsList);
    }


}
