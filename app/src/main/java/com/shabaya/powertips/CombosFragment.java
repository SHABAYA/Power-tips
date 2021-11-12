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

public class CombosFragment extends Fragment {
    private ArrayList<ComboPrediction> comboPredictionsList;
    private RecyclerView comboPrediction_recycler_view;
    private ComboRecyclerAdapter comboRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    View view;

    @Nullable //w-144 h-192
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_combos, container, false);

        initialiseRecyclerView();
        return view;

    }

    private void initialiseRecyclerView() {
        comboPrediction_recycler_view = view.findViewById(R.id.comboPrediction_recycler_view);
        comboPrediction_recycler_view.hasFixedSize();
        comboPrediction_recycler_view.hasNestedScrollingParent();
        comboPrediction_recycler_view.setHasFixedSize(true);
        comboPrediction_recycler_view.setNestedScrollingEnabled(false);
        comboPrediction_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        comboPrediction_recycler_view.setLayoutManager(layoutManager);
        comboRecyclerAdapter = new ComboRecyclerAdapter(getContext());
        comboPrediction_recycler_view.setAdapter(comboRecyclerAdapter);
        getComboPrediction();
    }

    public void getComboPrediction() {
        comboPredictionsList = new ArrayList<ComboPrediction>();
        ArrayList<Prediction> predictionsList = new ArrayList<>();
        predictionsList.add(new Prediction("England", "13:00", "Arsenal vs Chelsea", "over 2.5", 1.87));
        predictionsList.add(new Prediction("Germany", "14:00", "Leverkusen vs Hertha Berlin", "btts", 1.51));
        ComboPrediction comboPrediction = new ComboPrediction("Super saver", predictionsList);
        comboPredictionsList.add(comboPrediction);

        ArrayList<Prediction> predictionsList1 = new ArrayList<>();
        predictionsList1.add(new Prediction("England", "13:00", "Arsenal vs Chelsea", "over 2.5", 1.87));
        predictionsList1.add(new Prediction("Germany", "14:00", "Leverkusen vs Hertha Berlin", "btts", 1.51));
        ComboPrediction comboPrediction1 = new ComboPrediction("Lucy", predictionsList);
        comboPredictionsList.add(comboPrediction1);

        ArrayList<Prediction> predictionsList2 = new ArrayList<>();
        predictionsList2.add(new Prediction("England", "13:00", "Arsenal vs Chelsea", "over 2.5", 1.87));
        predictionsList2.add(new Prediction("Germany", "14:00", "Leverkusen vs Hertha Berlin", "btts", 1.51));
        ComboPrediction comboPrediction2 = new ComboPrediction("Hustle", predictionsList);
        comboPredictionsList.add(comboPrediction2);

        ArrayList<Prediction> predictionsList3 = new ArrayList<>();
        predictionsList3.add(new Prediction("England", "13:00", "Arsenal vs Chelsea", "over 2.5", 1.87));
        predictionsList3.add(new Prediction("England", "13:00", "Arsenal vs Chelsea", "btts", 1.71));
        predictionsList3.add(new Prediction("Germany", "14:00", "Leverkusen vs Hertha Berlin", "btts", 1.51));
        ComboPrediction comboPrediction3 = new ComboPrediction("Super saver", predictionsList);
        ComboPrediction comboPrediction5 = new ComboPrediction("Super time", predictionsList2);
        ComboPrediction comboPrediction4 = new ComboPrediction("Super bowl", predictionsList3);
        comboPredictionsList.add(comboPrediction3);
        comboPredictionsList.add(comboPrediction4);
        comboPredictionsList.add(comboPrediction5);


        comboRecyclerAdapter.setComboPredictionsList(comboPredictionsList);
    }


}
