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

public class BetOfTheDayFragment extends Fragment {
    private ArrayList<BetOfTheDayPrediction> predictionsList;
    private RecyclerView prediction_recycler_view;
    private BetOfTheDayAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bet_of_the_day, container, false);

        initialiseRecyclerView();
        return view;
    }

    private void initialiseRecyclerView() {
        prediction_recycler_view= view.findViewById(R.id.betofthedayPrediction_recycler_view);
        prediction_recycler_view.hasFixedSize();
        prediction_recycler_view.hasNestedScrollingParent();
        prediction_recycler_view.setHasFixedSize(true);
        prediction_recycler_view.setNestedScrollingEnabled(false);
        prediction_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        prediction_recycler_view.setLayoutManager(layoutManager);
        recyclerAdapter = new BetOfTheDayAdapter(getContext());
        prediction_recycler_view.setAdapter(recyclerAdapter);

        getBetOfTheDayPrediction();
    }

    public void getBetOfTheDayPrediction() {
        predictionsList = new ArrayList<>();
        predictionsList.add(new BetOfTheDayPrediction("Germany", "13:30", "Leverkusen vs hertha Berlin", "btts", 1.58));

        recyclerAdapter.setPredictionsList(predictionsList);
    }

}
