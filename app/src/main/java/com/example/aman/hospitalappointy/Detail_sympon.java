package com.example.aman.hospitalappointy;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Detail_sympon extends AppCompatActivity {

    //private Toolbar mToolbar;
    private String userID,type,name,Name;
    private TextView mName;
    private TextView mSympon;
    private EditText mToa,mBenh;
    private Button mCancel,mDone;

    private int count = 0;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_sympon);

        userID = mAuth.getCurrentUser().getUid().toString();

        mDatabase.child("Doctor_Details").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Name = dataSnapshot.child("Name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mName = (TextView) findViewById(R.id.text_name);
        mSympon = (TextView) findViewById(R.id.text_sympon);
        mToa = (EditText)findViewById(R.id.edt_toa);
        mBenh=(EditText)findViewById(R.id.edt_benh);
        mDone =(Button)findViewById(R.id.btn_done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1(getIntent().getStringExtra("PatientID"),getIntent().getStringExtra("Slot"));
                startActivity(new Intent(Detail_sympon.this, Doctor_ShowAppointmentActivity.class));

                String nameDoctor = Name;
                String namePatient = mName.getText().toString();
                String sympon = mSympon.getText().toString();
                String mbenh = mBenh.getText().toString();
                String mtoa = mToa.getText().toString();
                String date = getIntent().getStringExtra("Date");
                mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap < String, String > details = new HashMap <>();

                        details.put("Doctor_name",nameDoctor);
                        details.put("Patient_name", namePatient);
                        details.put("Sympon",sympon);
                        details.put("Sick", mbenh);
                        details.put("Prescription", mtoa);
                        details.put("Date",date);
                        mDatabase.child("Sick_detail").child(getIntent().getStringExtra("PatientID")).push().setValue(details);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        mCancel = (Button)findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog(getIntent().getStringExtra("PatientID"),getIntent().getStringExtra("Slot"));
                startActivity(new Intent(Detail_sympon.this, Doctor_ShowAppointmentActivity.class));

            }
        });


    }
    private void alertDialog(final String patientID, final String slot) {


        AlertDialog.Builder builder = new AlertDialog.Builder(Detail_sympon.this);
        builder.setIcon(R.drawable.question).setTitle("Cancel Appointment");
        builder.setMessage("Are You Sure! Want to Cancel Appointment");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();



                Query query = mDatabase.child("Booked_Appointments").child(patientID).orderByChild("Date");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String myParentNode = dataSnapshot.getKey();

                        for (DataSnapshot child: dataSnapshot.getChildren())
                        {
                            String key = child.getKey().toString();
                            String value = child.getValue().toString();

                            if(value.equals(userID)){

                                count = count + 1;

                            }
                            if(value.equals(getIntent().getStringExtra("Date"))){

                                count = count + 1;

                            }
                        }
                        if(count == 2){

                            mDatabase.child("Appointment").child(userID).child(getIntent().getStringExtra("Date")).child(slot).removeValue();
                            mDatabase.child("Booked_Appointments").child(patientID).child(myParentNode).removeValue();

                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void alertDialog1(final String patientID, final String slot) {


        AlertDialog.Builder builder = new AlertDialog.Builder(Detail_sympon.this);
        //builder.setIcon(R.drawable.question).setTitle("Cancel Appointment");
        builder.setMessage("Are You Sure! Want to Cancel Appointment");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();



                Query query = mDatabase.child("Booked_Appointments").child(patientID).orderByChild("Date");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String myParentNode = dataSnapshot.getKey();

                        for (DataSnapshot child: dataSnapshot.getChildren())
                        {
                            String key = child.getKey().toString();
                            String value = child.getValue().toString();

                            if(value.equals(userID)){

                                count = count + 1;

                            }
                            if(value.equals(getIntent().getStringExtra("Date"))){

                                count = count + 1;

                            }
                        }
                        if(count == 2){

                            mDatabase.child("Appointment").child(userID).child(getIntent().getStringExtra("Date")).child(slot).removeValue();
                            mDatabase.child("Booked_Appointments").child(patientID).child(myParentNode).removeValue();

                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void onStart(){
        super.onStart();

        mDatabase.child("User_Type").child(getIntent().getStringExtra("PatientID")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                type = snapshot.child("Type").getValue().toString();;
                if(type.equals("Patient")){
                    mDatabase.child("Patient_Details").child(getIntent().getStringExtra("PatientID").toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            name = snapshot.child("Name").getValue().toString();
                            mName.setText(name);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    mDatabase.child("Doctor_Details").child(getIntent().getStringExtra("PatientID")).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            name = snapshot.child("Name").getValue().toString();
                            mName.setText(name);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        String sympon = getIntent().getStringExtra("Sympon").toString();

        mSympon.setText(sympon);

    }
}
