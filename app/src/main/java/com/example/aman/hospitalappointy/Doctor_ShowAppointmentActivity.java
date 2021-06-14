package com.example.aman.hospitalappointy;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Doctor_ShowAppointmentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private String userID, date = "", time, type,Date;
    private TextView selectDate, selectedDate;
    private int count = 0;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__show_appointment);


        mToolbar = (Toolbar) findViewById(R.id.doctor_appointment_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lịch nhận");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID = mAuth.getCurrentUser().getUid().toString();



        selectDate = (TextView) findViewById(R.id.showAppointment_selecteDate);
        selectedDate = (TextView) findViewById(R.id.showAppointment_selectedDate);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(Doctor_ShowAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth +"-"+ (month+1) +"-"+ year;
                        selectedDate.setVisibility(View.VISIBLE);
                        selectDate.setText(date);

                        showAppointment();

                    }
                },day,month,year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.appointments_show);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showAppointment() {
        Query query = mDatabase.child("Appointment").child(userID).child(date);

        FirebaseRecyclerOptions<BookedAppointmentList> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookedAppointmentList>()
                .setQuery(query, BookedAppointmentList.class)
                .build();

        FirebaseRecyclerAdapter<BookedAppointmentList, DoctorShowAppointmentVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BookedAppointmentList, DoctorShowAppointmentVH>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final DoctorShowAppointmentVH holder, final int position, @NonNull final BookedAppointmentList model) {

                        final String patientID = model.getPatientID().toString();
                        final String slot = getRef(position).getKey().toString();
                        final String[] name = new String[1];

                        holder.mView.setOnClickListener((View.OnClickListener) view -> {

                            Intent intent=new Intent(getApplicationContext(), Detail_sympon.class);

                            String PatientID=model.getPatientID().toString();
                            String sympon=model.getSympon().toString();
                            String date =model.getDate().toString();
                            String Slot= getRef(position).getKey().toString();

                            intent.putExtra("Slot",Slot);
                            intent.putExtra("PatientID",PatientID);
                            intent.putExtra("Sympon",sympon);
                            intent.putExtra("Date",date);

                            startActivity(intent);
                        });

                        mDatabase.child("User_Type").child(patientID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                type = dataSnapshot.child("Type").getValue().toString();
                                if(type.equals("Patient")){
                                    mDatabase.child("Patient_Details").child(patientID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name[0] = dataSnapshot.child("Name").getValue().toString();

                                            changeSlotToTime(slot);
                                            holder.setName(name[0]);
                                            holder.setTime(time);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }else {
                                    mDatabase.child("Doctor_Details").child(patientID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name[0] = dataSnapshot.child("Name").getValue().toString();

                                            changeSlotToTime(slot);
                                            holder.setName(name[0]);
                                            holder.setTime(time);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public DoctorShowAppointmentVH onCreateViewHolder(ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_show_appointment,parent,false);
                        return new DoctorShowAppointmentVH(view);
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

   /* private void alertDialog(final String patientID, final String slot) {

        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(Doctor_ShowAppointmentActivity.this);
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
                            if(value.equals(date)){

                                count = count + 1;

                            }
                        }
                        if(count == 2){

                            mDatabase.child("Appointment").child(userID).child(date).child(slot).removeValue();
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
    }*/
    private void changeSlotToTime(String slot) {

        switch (slot) {
            case "1":
                time = "08:00 AM";
                break;
            case "2":
                time = "08:10 AM";
                break;
            case "3":
                time = "08:20 AM";
                break;
            case "4":
                time = "08:30 AM";
                break;
            case "5":
                time = "08:40 AM";
                break;
            case "6":
                time = "08:50 AM";
                break;
            case"7" :
                time = "09:00 AM";
                break;
            case "8":
                time = "09:10 AM";
                break;
            case "9":
                time = "09:20 AM";
                break;
            case "10":
                time = "09:30 AM";
                break;
            case "11":
                time = "09:40 AM";
                break;
            case "12":
                time = "09:50 AM";
                break;
            case "13":
                time = "10:00 PM";
                break;
            case "14":
                time = "10:10 PM";
                break;
            case "15":
                time = "10:20 PM";
                break;
            case "16":
                time = "10:30 PM";
                break;
            case "17":
                time = "10:40 PM";
                break;
            case "18":
                time = "10:50 PM";
                break;
            case "19":
                time = "11:00 PM";
                break;
            case "20":
                time = "11:10 PM";
                break;
            case "21":
                time = "11:20 PM";
                break;
            case "22":
                time = "02:00 PM";
                break;
            case "23":
                time = "02:10 PM";
                break;
            case "24":
                time = "02:20 PM";
                break;
            case "25":
                time = "02:30 PM";
                break;
            case "26":
                time = "02:40 PM";
                break;
            case "27":
                time = "02:50 PM";
                break;
            case "28":
                time = "03:00 PM";
                break;
            case "29":
                time = "03:10 PM";
                break;
            case "30":
                time = "03:20 PM";
                break;
            case "31":
                time = "03:30 PM";
                break;
            case "32":
                time = "03:40 PM";
                break;
            case "33":
                time = "03:50 PM";
                break;
            case "34":
                time = "04:00 PM";
                break;
            case "35":
                time = "04:10 PM";
                break;
            case "36":
                time = "04:20 PM";
                break;
            case "37":
                time = "04:30 PM";
                break;
            case "38":
                time = "04:40 PM";
                break;
            case "39":
                time = "04:50 PM";
                break;
            case "40":
                time = "05:00 PM";
                break;
            case "41":
                time = "05:10 PM";
                break;
            case "42":
                time = "05:20 PM";
                break;
            default:
                break;
        }
    }

    public class DoctorShowAppointmentVH extends RecyclerView.ViewHolder{

        View mView;

        public DoctorShowAppointmentVH(View itemView) {
            super(itemView);

            mView =itemView;
        }

        public void setTime(String time) {
            TextView textView = (TextView) mView.findViewById(R.id.single_patient_time);
            textView.setText(time);
        }

        public void setName(String name) {
            TextView mName = (TextView) mView.findViewById(R.id.single_patientName);
            mName.setText(name);
        }
        public void setSympon(String sympon){
            TextView mSympon=(TextView)mView.findViewById(R.id.text_sympon);
            mSympon.setText(sympon);
        }
    }
    //show lịch của ngày hiện tại
    public void onStart()
    {
        super.onStart();
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Date =day+"-"+(month+1)+"-"+year ;
        Query query = mDatabase.child("Appointment").child(userID).child(Date);

        FirebaseRecyclerOptions<BookedAppointmentList> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<BookedAppointmentList>()
                .setQuery(query, BookedAppointmentList.class)
                .build();

        FirebaseRecyclerAdapter<BookedAppointmentList, DoctorShowAppointmentVH> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<BookedAppointmentList, DoctorShowAppointmentVH>(firebaseRecyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final DoctorShowAppointmentVH holder, final int position, @NonNull final BookedAppointmentList model) {

                        final String patientID = model.getPatientID().toString();
                        final String slot = getRef(position).getKey().toString();
                        final String[] name = new String[1];

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //alertDialog(patientID,slot);
                                Intent intent=new Intent(getApplicationContext(), Detail_sympon.class);

                                String PatientID=model.getPatientID().toString();
                                String sympon=model.getSympon().toString();
                                String date =Date;
                                String Slot= getRef(position).getKey().toString();

                                intent.putExtra("Slot",Slot);
                                intent.putExtra("PatientID",PatientID);
                                intent.putExtra("Sympon",sympon);
                                intent.putExtra("Date",date);

                                startActivity(intent);
                            }
                        });

                        mDatabase.child("User_Type").child(patientID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                type = dataSnapshot.child("Type").getValue().toString();
                                if(type.equals("Patient")){
                                    mDatabase.child("Patient_Details").child(patientID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name[0] = dataSnapshot.child("Name").getValue().toString();

                                            changeSlotToTime(slot);
                                            holder.setName(name[0]);
                                            holder.setTime(time);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }else {
                                    mDatabase.child("Doctor_Details").child(patientID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            name[0] = dataSnapshot.child("Name").getValue().toString();

                                            changeSlotToTime(slot);
                                            holder.setName(name[0]);
                                            holder.setTime(time);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public DoctorShowAppointmentVH onCreateViewHolder(ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_show_appointment,parent,false);
                        return new DoctorShowAppointmentVH(view);
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

}
