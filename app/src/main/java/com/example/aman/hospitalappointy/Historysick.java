package com.example.aman.hospitalappointy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Historysick extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;

    private String BookedAPKey = "", Appointment_date, slot , Appointment_time , doctorID, currentUID;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        currentUID = mAuth.getCurrentUser().getUid().toString();

        mToolbar = (Toolbar) findViewById(R.id.show_Historysick);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lịch sử khám");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.show_Historysick_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query = mDatabase.child("Sick_detail").child(currentUID);

        FirebaseRecyclerOptions<Historylist> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Historylist>().setQuery(query,Historylist.class).build();
        FirebaseRecyclerAdapter<Historylist,HistoryVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter < Historylist, HistoryVH >(firebaseRecyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull HistoryVH holder, int position, @NonNull Historylist model) {
                mDatabase.child("Sick_detail").child(currentUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        holder.setDoctorName(model.getDoctor_name());
                        holder.setPrescription(model.getPrescription());
                        holder.setSick(model.getSick());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_history,parent,false);
                return new HistoryVH(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }



    public class HistoryVH extends RecyclerView.ViewHolder{

        View mView;

        public HistoryVH(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDoctorName(String doctorName) {
            TextView DoctorName = (TextView) mView.findViewById(R.id.text_namedoctor);
            DoctorName.setText(doctorName);
        }

        public void setSick(String sick) {
            TextView Sick = (TextView) mView.findViewById(R.id.text_sick);
            Sick.setText(sick);
        }

        public void setPrescription(String prescription) {

            TextView Prescription = (TextView) mView.findViewById(R.id.text_donthuoc);
            Prescription.setText(prescription);
        }

    }

}
