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
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OverBttsFragment extends Fragment {

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
        view = inflater.inflate(R.layout.fragment_over_btts, container, false);

        setupFirebaseFirestore();
        predictionsCollectionReference = firestore.collection("Predictions");
        initialiseRecyclerView();
        return view;

    }

    private void initialiseRecyclerView() {
        prediction_recycler_view = view.findViewById(R.id.over_recycler_view);
        prediction_recycler_view.hasFixedSize();
        prediction_recycler_view.hasNestedScrollingParent();
        prediction_recycler_view.setHasFixedSize(true);
        prediction_recycler_view.setNestedScrollingEnabled(false);
        prediction_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        prediction_recycler_view.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getContext());
        prediction_recycler_view.setAdapter(recyclerAdapter);
        getPrediction();
    }

    public void getPrediction() {
        predictionsList = new ArrayList<>();
        Query fetchBtts = predictionsCollectionReference.whereEqualTo("predict", "btts")
                //.whereEqualTo("predict", "over 2.5")
                .limit(25);

        Query fetchOver = predictionsCollectionReference
                //.whereEqualTo("predict", "btts")
                .whereEqualTo("predict", "over 2.5")
                .limit(25);

        Task task1 = fetchBtts.get();
        Task task2 = fetchOver.get();

        Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2);

        allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
            @Override
            public void onSuccess(List<QuerySnapshot> querySnapshots) {
                for (QuerySnapshot q : querySnapshots) {
                    for (DocumentSnapshot d : q.getDocuments()) {
                        Prediction p = d.toObject(Prediction.class);
                        predictionsList.add(p);
                    }

                    recyclerAdapter.setPredictionsList(predictionsList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ERROR", e.getMessage());
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
