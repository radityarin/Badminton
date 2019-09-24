package com.radityarin.badminton.penyewa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radityarin.badminton.R;
import com.radityarin.badminton.adapter.AdapterPenyedia;
import com.radityarin.badminton.pojo.Penyedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String kordinatuser;
    private Location userlocation;
    private RecyclerView recyclerView;
    private ArrayList<Penyedia> listtempat;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        userlocation = new Location("");
        getCurrentLocation();

        SearchView svlapangan = view.findViewById(R.id.sv_lapangan);
        svlapangan.setOnQueryTextListener(this);
        recyclerView = view.findViewById(R.id.recycler_view_lokasi);
        listtempat = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Detail Penyedia");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listtempat.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Penyedia mLokasi = dt.getValue(Penyedia.class);
                    if (mLokasi.getActive()) {
                        listtempat.add(mLokasi);
                    }
                }

                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button refresh = view.findViewById(R.id.refreshbutton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listtempat.clear();
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            Penyedia mLokasi = dt.getValue(Penyedia.class);
                            if (!Objects.requireNonNull(mLokasi).getFotolapangan().equals("") && mLokasi.getActive()) {
                                listtempat.add(mLokasi);
                            }
                        }

                        Log.d("cek kordinat saya", userlocation.getLatitude() + ", " + userlocation.getLongitude());
                        Location loc = new Location("");
                        HashMap<Float, Penyedia> penyediaHashMap = new HashMap<>();
                        for (int i = 0; i < listtempat.size(); i++) {
                            String[] latilang = listtempat.get(i).getKordinatlapangan().split(", ");
                            loc.setLatitude(Double.parseDouble(latilang[0]));
                            loc.setLongitude(Double.parseDouble(latilang[1]));
                            Log.d("cek jarak", listtempat.get(i).getNamalapangan() + listtempat.get(i).getAlamatlapangan() + " = " + userlocation.distanceTo(loc));
                            penyediaHashMap.put(userlocation.distanceTo(loc), listtempat.get(i));
                        }

                        ArrayList<Float> sortedkeys = new ArrayList<>(penyediaHashMap.keySet());
                        Collections.sort(sortedkeys);
                        listtempat.clear();
                        for (Float key : sortedkeys) {
                            listtempat.add(penyediaHashMap.get(key));
                        }
                        for (int i = 0; i < listtempat.size(); i++) {
                            Log.d("cek jarak", listtempat.get(i).getNamalapangan() + listtempat.get(i).getAlamatlapangan() + " = " + userlocation.distanceTo(loc));
                        }
                        for (Penyedia a : listtempat) {
                            Log.d("cek",a.getNamalapangan());
                        }
                        recyclerView.setAdapter(new AdapterPenyedia(listtempat, getContext()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


        return view;
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    kordinatuser = location.getLatitude() + ", " + location.getLongitude();
//                    // Display in Toast
                    userlocation = new Location("");
                    String[] latlang = kordinatuser.split(", ");
                    userlocation.setLatitude(Double.parseDouble(latlang[0]));
                    userlocation.setLongitude(Double.parseDouble(latlang[1]));
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        query = query.toLowerCase();
        database.getReference().child("Detail Penyedia").orderByChild("namalapangansmallcase").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listtempat.clear();
                for(DataSnapshot dt : dataSnapshot.getChildren()){
                    Penyedia penyedia = dt.getValue(Penyedia.class);
                    Log.d("cek", Objects.requireNonNull(penyedia).getNamalapangan());
                    listtempat.add(penyedia);
                }
                recyclerView.setAdapter(new AdapterPenyedia(listtempat, getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
