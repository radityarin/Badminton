package com.radityarin.badminton.penyewa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterOrder;
import com.radityarin.badminton.pojo.Penyedia;
import com.radityarin.badminton.pojo.Rating;
import com.radityarin.badminton.pojo.Sewa;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class OrderFragment extends Fragment implements RatingDialogListener {

    private ArrayList<Sewa> listsewa;
    private FirebaseAuth auth;
    private boolean rating;
    private Sewa sewa;
    private double ratingPenyedia;
    private double inputRate;

    public OrderFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        auth = FirebaseAuth.getInstance();
        listsewa = new ArrayList<>();
        rating = false;

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference sewaRef = FirebaseDatabase.getInstance().getReference().child("Detail Sewa");
        sewaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listsewa.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Sewa mSewa = dt.getValue(Sewa.class);
                    if ((mSewa.getStatussewa().equals("Pesanan selesai")) && mSewa.getIdpenyewa().equalsIgnoreCase(auth.getUid())) {
                        sewa = mSewa;
                        showDialog(mSewa.getNamalapangan());
                    }
                    if (mSewa.getIdpenyewa().equals(auth.getUid()) && (mSewa.getStatussewa().equalsIgnoreCase("Belum dikonfirmasi") || mSewa.getStatussewa().equalsIgnoreCase("Pesanan dikonfirmasi"))) {
                        listsewa.add(mSewa);
                    }
                }
                recyclerView.setAdapter(new AdapterOrder(listsewa, getContext(), false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void showDialog(String namalapangan) {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Sangat Buruk", "Buruk", "Lumayan", "Baik", "Sangat Baik"))
                .setDefaultRating(2)
                .setTitle("Beri rating dan komentar untuk " + namalapangan)
                .setDescription("Pilih rating dan tulis komentar dibawah")
                .setCommentInputEnabled(true)
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.contentTextColor)
                .setHint("Tulis komen anda disini")
                .setHintTextColor(R.color.hintTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.commentBackgroundColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(getActivity())
                .setTargetFragment(this, 0) // only if listener is implemented by fragment
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(final int rate, String comment) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();
        final Rating rating = new Rating(sewa.getIdlapangan(), sewa.getNamalapangan(), sewa.getIdpenyewa(), sewa.getNamapenyewa(), sewa.getIdsewa(), String.valueOf((double) rate), comment);
        mDatabaseRef.child("Rating").child(sewa.getIdlapangan()).child(sewa.getIdsewa()).setValue(rating);

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        final DatabaseReference myref = database2.getReference().child("Detail Sewa");
        myref.child(sewa.getIdsewa()).child("statussewa").setValue("Pesanan Selesai");
        inputRate = (double) rate;
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference().child("Detail Penyedia").child(sewa.getIdlapangan());
        ratingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Penyedia penyedia = dataSnapshot.getValue(Penyedia.class);
                ratingPenyedia = penyedia.getRating();
                Log.d("cek dari firebase", String.valueOf(ratingPenyedia));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(sewa != null) {
            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
            final DatabaseReference myref2 = database3.getReference().child("Detail Penyedia");
            double finalRating = (ratingPenyedia + inputRate) / 2d;
            myref2.child(sewa.getIdlapangan()).child("rating").setValue(finalRating);
        }
    }
}
