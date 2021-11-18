package com.shabaya.powertips;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class CombosFragment extends Fragment {
    private ArrayList<ComboPrediction> comboPredictionsList;
    private RecyclerView comboPrediction_recycler_view;
    private ComboRecyclerAdapter comboRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CollectionReference predictionsCollectionReference,comboCollectionReference;
    private FirebaseFirestore firestore;
    View view;

    @Nullable //w-144 h-192
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_combos, container, false);

        setupFirebaseFirestore();
        comboCollectionReference = firestore.collection("combos");
        predictionsCollectionReference = firestore.collection("Predictions");
        initialiseRecyclerView();
        return view;

    }

    private void initialiseRecyclerView() {
        comboPrediction_recycler_view = view.findViewById(R.id.comboPrediction_recycler_view);
        //comboPrediction_recycler_view.hasFixedSize();
        //comboPrediction_recycler_view.hasNestedScrollingParent();
        //comboPrediction_recycler_view.setHasFixedSize(true);
        comboPrediction_recycler_view.setNestedScrollingEnabled(false);
        comboPrediction_recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        comboPrediction_recycler_view.setLayoutManager(layoutManager);
        comboRecyclerAdapter = new ComboRecyclerAdapter(getContext());
        comboPrediction_recycler_view.setAdapter(comboRecyclerAdapter);
        getComboPrediction();
    }

    public void getComboPrediction() {
        comboPredictionsList = new ArrayList<>();
       ArrayList<Prediction> predictionsList = new ArrayList<>();
        Query fetch = comboCollectionReference.limit(25);
        fetch.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() > 0){
                    /// DocumentSnapshot documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                        String id = d.getId();
                        String title = d.getString("title");
                        ComboPrediction comboPrediction = new ComboPrediction();
                        comboPrediction.setId(id);
                        comboPrediction.setTitle(title);
                        //Toast.makeText(getContext(), comboPrediction.getId()+"", Toast.LENGTH_SHORT).show();
                        getPrediction(comboPrediction);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ERROR",e.getMessage());
            }
        });

    }

    public void getPrediction(ComboPrediction comboPrediction) {
        //Toast.makeText(getContext(), comboPrediction.getId()+"", Toast.LENGTH_SHORT).show();
        ArrayList<Prediction> predictionsList = new ArrayList<>();
        Query fetch = predictionsCollectionReference
                .whereEqualTo("comboId",comboPrediction.getId())
                .limit(25);
        fetch.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() > 0){
                    /// DocumentSnapshot documentSnapshots = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                        Prediction p = d.toObject(Prediction.class);
                        //Toast.makeText(getContext(), d.getString("time")+"", Toast.LENGTH_SHORT).show();
                        predictionsList.add(p);
                    }
                    comboPrediction.setPredictions(predictionsList);
                    comboRecyclerAdapter.addData(comboPrediction);
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
