package com.shabaya.powertips;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SinglesFragment extends Fragment {
    private ArrayList<Prediction> predictionsList;
    private RecyclerView prediction_recycler_view;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CollectionReference predictionsCollectionReference;
    private FirebaseFirestore firestore;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_singles, container, false);

        setupFirebaseFirestore();
        predictionsCollectionReference = firestore.collection("Predictions");
        initialiseRecyclerView();

        return view;
    }

    private void initialiseRecyclerView() {
        prediction_recycler_view= view.findViewById(R.id.singles_recycler_view);
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
        Query fetch = predictionsCollectionReference.orderBy("time").limit(25);
        fetch.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() > 1){
                    /// DocumentSnapshot documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                        Prediction p = d.toObject(Prediction.class);
                        predictionsList.add(p);
                    }
                    recyclerAdapter.setPredictionsList(predictionsList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ERROR",e.getMessage());
            }
        });

    }

    public void setupFirebaseFirestore() {
        // [START get_firestore_instance]
        firestore = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        firestore.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

}
