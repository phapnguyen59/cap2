package com.example.aman.hospitalappointy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Patient_BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

    private String date, time = "", shift;
    private TextView selectDate;
    private Toolbar mToolbar;
    private Button mConfirm;
    private EditText mSympon;
    private  int flagChecked=0;
    private LinearLayout morningLayout, eveningLayout;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,
            c22,c23,c24,c25,c26,c27,c28,c29,c30,c31,c32,c33,c34,c35,c36,c37,c38,c39,c40,c41,c42;
    private DatabaseReference mDataBaseRef = FirebaseDatabase.getInstance().getReference().child("Appointment");
    private DatabaseReference mPatientDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__book_appointment);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.patient_bookAppointment);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Đặt lịch");

        String a = mAuth.getCurrentUser().getUid().toString();

        mSympon =(EditText)findViewById(R.id.edit_sympon);


        morningLayout = (LinearLayout) findViewById(R.id.morning_shift);
        eveningLayout = (LinearLayout) findViewById(R.id.evening_shift);
        shift = getIntent().getStringExtra("Shift").toString();

        if(shift.equals("Morning")){
            morningLayout.setVisibility(View.VISIBLE);
            eveningLayout.setVisibility(View.GONE);
        }else {
            eveningLayout.setVisibility(View.VISIBLE);
            morningLayout.setVisibility(View.GONE);
        }

        mConfirm = (Button) findViewById(R.id.confirm_appointment);
        mConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {

                    if(flagChecked!=0)
                    {
                        //Toast.makeText(Patient_BookAppointmentActivity.this, "Selected Time "+flagChecked, Toast.LENGTH_SHORT).show();
                        mDataBaseRef.child(getIntent().getStringExtra("DoctorUserId")).child(date).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int i = 1;
                                for(i=1;i<=42;i++)
                                {
                                    if(dataSnapshot.hasChild(String.valueOf(i)))
                                    {
                                        if(dataSnapshot.child(String.valueOf(i)).child("PatientID").getValue().toString().equals(mAuth.getCurrentUser().getUid()))
                                        {
                                            Toast.makeText(Patient_BookAppointmentActivity.this, "You Have Already An Appointment ", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }

                                if(i>42) {

                                    setTime(flagChecked);
                                    String sympon =mSympon.getText().toString();
                                    //đường dẫn và thứ tự lưu trên firebase "Appointment"
                                    mDataBaseRef.child(getIntent().getStringExtra("DoctorUserId")).child(date).child(String.valueOf(flagChecked)).child("PatientID").setValue(a);
                                    mDataBaseRef.child(getIntent().getStringExtra("DoctorUserId")).child(date).child(String.valueOf(flagChecked)).child("Sympon").setValue(sympon);

                                    //lấy id của doctor để lưu
                                    mPatientDatabase.child("Doctor_Details").child(getIntent().getStringExtra("DoctorUserId")).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            //String doctorName = dataSnapshot.child("Name").getValue().toString();

                                            HashMap < String, String > details = new HashMap <>();
                                            details.put("Doctor_ID", getIntent().getStringExtra("DoctorUserId"));
                                            details.put("Date", date);
                                            details.put("Time", time);

                                            //đường dẫn và thứ tự lưu trên firebase "Book_Appointment"
                                            mPatientDatabase.child("Booked_Appointments").child(mAuth.getCurrentUser().getUid()).push().setValue(details);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                    startActivity(new Intent(Patient_BookAppointmentActivity.this, Patient_ShowBookedAppointmentActivity.class));

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                    else{
                        Toast.makeText(Patient_BookAppointmentActivity.this, "Please Select Time Slot", Toast.LENGTH_SHORT).show();
                    }
                }


        });

        c1 = (CardView) findViewById(R.id.time1);
        c2 = (CardView) findViewById(R.id.time2);
        c3 = (CardView) findViewById(R.id.time3);
        c4 = (CardView) findViewById(R.id.time4);
        c5 = (CardView) findViewById(R.id.time5);
        c6 = (CardView) findViewById(R.id.time6);
        c7 = (CardView) findViewById(R.id.time7);
        c8 = (CardView) findViewById(R.id.time8);
        c9 = (CardView) findViewById(R.id.time9);
        c10 = (CardView) findViewById(R.id.time10);
        c11 = (CardView) findViewById(R.id.time11);
        c12 = (CardView) findViewById(R.id.time12);
        c13 = (CardView) findViewById(R.id.time13);
        c14 = (CardView) findViewById(R.id.time14);
        c15 = (CardView) findViewById(R.id.time15);
        c16 = (CardView) findViewById(R.id.time16);
        c17 = (CardView) findViewById(R.id.time17);
        c18 = (CardView) findViewById(R.id.time18);
        c19 = (CardView) findViewById(R.id.time19);
        c20 = (CardView) findViewById(R.id.time20);
        c21 = (CardView) findViewById(R.id.time21);
        c22 = (CardView) findViewById(R.id.time22);
        c23 = (CardView) findViewById(R.id.time23);
        c24 = (CardView) findViewById(R.id.time24);
        c25 = (CardView) findViewById(R.id.time25);
        c26 = (CardView) findViewById(R.id.time26);
        c27 = (CardView) findViewById(R.id.time27);
        c28 = (CardView) findViewById(R.id.time28);
        c29 = (CardView) findViewById(R.id.time29);
        c30 = (CardView) findViewById(R.id.time30);
        c31 = (CardView) findViewById(R.id.time31);
        c32 = (CardView) findViewById(R.id.time32);
        c33 = (CardView) findViewById(R.id.time33);
        c34 = (CardView) findViewById(R.id.time34);
        c35 = (CardView) findViewById(R.id.time35);
        c36 = (CardView) findViewById(R.id.time36);
        c37 = (CardView) findViewById(R.id.time37);
        c38 = (CardView) findViewById(R.id.time38);
        c39 = (CardView) findViewById(R.id.time39);
        c40 = (CardView) findViewById(R.id.time40);
        c41 = (CardView) findViewById(R.id.time41);
        c42 = (CardView) findViewById(R.id.time42);

        selectDate = (TextView) findViewById(R.id.bookAppointment_selectDate);

        date = getIntent().getStringExtra("Date").toString();
        selectDate.setText(date);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Patient_BookAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = dayOfMonth +"-"+ (month+1) +"-"+ year;
                       // Toast.makeText(Patient_BookAppointmentActivity.this, date , Toast.LENGTH_SHORT).show();
                        selectDate.setText(date);
                        onStart();


                    }
                },day,month,year);
                datePickerDialog.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (3 * 60 * 60 * 1000));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.time1:
                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                checkIsBooked(1);
                break;
            case R.id.time2:
                //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                checkIsBooked(2);
                break;
            case R.id.time3:
                //Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                checkIsBooked(3);
                break;
            case R.id.time4:
                //Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                checkIsBooked(4);
                break;
            case R.id.time5:
                //Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
                checkIsBooked(5);
                break;
            case R.id.time6:
                //Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
                checkIsBooked(6);
                break;
            case R.id.time7:
                //Toast.makeText(this, "7", Toast.LENGTH_SHORT).show();
                checkIsBooked(7);
                break;
            case R.id.time8:
                //Toast.makeText(this, "8", Toast.LENGTH_SHORT).show();
                checkIsBooked(8);
                break;
            case R.id.time9:
                //Toast.makeText(this, "9", Toast.LENGTH_SHORT).show();
                checkIsBooked(9);
                break;
            case R.id.time10:
                //Toast.makeText(this, "10", Toast.LENGTH_SHORT).show();
                checkIsBooked(10);
                break;
            case R.id.time11:
                //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
                checkIsBooked(11);
                break;
            case R.id.time12:
                //Toast.makeText(this, "12", Toast.LENGTH_SHORT).show();
                checkIsBooked(12);
                break;
            case R.id.time13:
                //Toast.makeText(this, "13", Toast.LENGTH_SHORT).show();
                checkIsBooked(13);
                break;
            case R.id.time14:
                //Toast.makeText(this, "14", Toast.LENGTH_SHORT).show();
                checkIsBooked(14);
                break;
            case R.id.time15:
                //Toast.makeText(this, "15", Toast.LENGTH_SHORT).show();
                checkIsBooked(15);
                break;
            case R.id.time16:
                //Toast.makeText(this, "16", Toast.LENGTH_SHORT).show();
                checkIsBooked(16);
                break;
            case R.id.time17:
                //Toast.makeText(this, "17", Toast.LENGTH_SHORT).show();
                checkIsBooked(17);
                break;
            case R.id.time18:
                //Toast.makeText(this, "18", Toast.LENGTH_SHORT).show();
                checkIsBooked(18);
                break;
            case R.id.time19:
                //Toast.makeText(this, "19", Toast.LENGTH_SHORT).show();
                checkIsBooked(19);
                break;
            case R.id.time20:
                //Toast.makeText(this, "20", Toast.LENGTH_SHORT).show();
                checkIsBooked(20);
                break;
            case R.id.time21:
                //Toast.makeText(this, "21", Toast.LENGTH_SHORT).show();
                checkIsBooked(21);
                break;
            case R.id.time22:
                //Toast.makeText(this, "22", Toast.LENGTH_SHORT).show();
                checkIsBooked(22);
                break;
            case R.id.time23:
                //Toast.makeText(this, "23", Toast.LENGTH_SHORT).show();
                checkIsBooked(23);
                break;
            case R.id.time24:
                //Toast.makeText(this, "24", Toast.LENGTH_SHORT).show();
                checkIsBooked(24);
                break;
            case R.id.time25:
                //Toast.makeText(this, "25", Toast.LENGTH_SHORT).show();
                checkIsBooked(25);
                break;
            case R.id.time26:
                //Toast.makeText(this, "26", Toast.LENGTH_SHORT).show();
                checkIsBooked(26);
                break;
            case R.id.time27:
                //Toast.makeText(this, "27", Toast.LENGTH_SHORT).show();
                checkIsBooked(27);
                break;
            case R.id.time28:
                //Toast.makeText(this, "28", Toast.LENGTH_SHORT).show();
                checkIsBooked(28);
                break;
            case R.id.time29:
                //Toast.makeText(this, "29", Toast.LENGTH_SHORT).show();
                checkIsBooked(29);
                break;
            case R.id.time30:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(30);
                break;
            case R.id.time31:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(31);
                break;
            case R.id.time32:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(32);
                break;
            case R.id.time33:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(33);
                break;
            case R.id.time34:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(34);
                break;
            case R.id.time35:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(35);
                break;
            case R.id.time36:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(36);
                break;
            case R.id.time37:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(37);
                break;
            case R.id.time38:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(38);
                break;
            case R.id.time39:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(39);
                break;
            case R.id.time40:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(40);
                break;
            case R.id.time41:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(41);
                break;
            case R.id.time42:
                //Toast.makeText(this, "30", Toast.LENGTH_SHORT).show();
                checkIsBooked(42);
                break;

                
            default:
                break;
        }
    }

    private void checkIsBooked(int i) {


        if(flagChecked!=0) {
          setDefaultColor(flagChecked);
          flagChecked = i;
          setColorGreen(i);
        }
        else {
            flagChecked=i;
            setColorGreen(i);
        }


    }

    private void setDefaultColor(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c1.setEnabled(true);
                break;
            case 2:
                c2.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c2.setEnabled(true);
                break;
            case 3:
                c3.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c3.setEnabled(true);
                break;
            case 4:
                c4.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c4.setEnabled(true);
                break;
            case 5:
                c5.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c5.setEnabled(true);
                break;
            case 6:
                c6.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c6.setEnabled(true);
                break;
            case 7:
                c7.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c7.setEnabled(true);
                break;
            case 8:
                c8.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c8.setEnabled(true);
                break;
            case 9:
                c9.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c9.setEnabled(true);
                break;
            case 10:
                c10.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c10.setEnabled(true);
                break;
            case 11:
                c11.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c11.setEnabled(true);
                break;
            case 12:
                c12.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c12.setEnabled(true);
                break;
            case 13:
                c13.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c13.setEnabled(true);
                break;
            case 14:
                c14.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c14.setEnabled(true);
                break;
            case 15:
                c15.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c15.setEnabled(true);
                break;
            case 16:
                c16.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c16.setEnabled(true);
                break;
            case 17:
                c17.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c17.setEnabled(true);
                break;
            case 18:
                c18.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c18.setEnabled(true);
                break;
            case 19:
                c19.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c19.setEnabled(true);
                break;
            case 20:
                c20.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c20.setEnabled(true);
                break;
            case 21:
                c21.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c21.setEnabled(true);
                break;
            case 22:
                c22.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c22.setEnabled(true);
                break;
            case 23:
                c23.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c23.setEnabled(true);
                break;
            case 24:
                c24.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c24.setEnabled(true);
                break;
            case 25:
                c25.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c25.setEnabled(true);
                break;
            case 26:
                c26.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c26.setEnabled(true);
                break;
            case 27:
                c27.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c27.setEnabled(true);
                break;
            case 28:
                c28.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c28.setEnabled(true);
                break;
            case 29:
                c29.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c29.setEnabled(true);
                break;
            case 30:
                c30.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c30.setEnabled(true);
                break;
            case 31:
                c31.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c31.setEnabled(true);
                break;
            case 32:
                c32.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c32.setEnabled(true);
                break;
            case 33:
                c33.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c33.setEnabled(true);
                break;
            case 34:
                c34.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c34.setEnabled(true);
                break;
            case 35:
                c35.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c35.setEnabled(true);
                break;
            case 36:
                c36.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c36.setEnabled(true);
                break;
            case 37:
                c37.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c37.setEnabled(true);
                break;
            case 38:
                c38.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c38.setEnabled(true);
                break;
            case 39:
                c39.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c39.setEnabled(true);
                break;
            case 40:
                c40.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c40.setEnabled(true);
                break;
            case 41:
                c41.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c41.setEnabled(true);
                break;
            case 42:
                c42.setCardBackgroundColor(getResources().getColor(R.color.skyBlue));
                c42.setEnabled(true);
                break;


            default:
                break;
        }
    }

    private void setColorRed(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(Color.RED);
                c1.setEnabled(false);
                break;
            case 2:
                c2.setCardBackgroundColor(Color.RED);
                c2.setEnabled(false);
                break;
            case 3:
                c3.setCardBackgroundColor(Color.RED);
                c3.setEnabled(false);
                break;
            case 4:
                c4.setCardBackgroundColor(Color.RED);
                c4.setEnabled(false);
                break;
            case 5:
                c5.setCardBackgroundColor(Color.RED);
                c5.setEnabled(false);
                break;
            case 6:
                c6.setCardBackgroundColor(Color.RED);
                c6.setEnabled(false);
                break;
            case 7:
                c7.setCardBackgroundColor(Color.RED);
                c7.setEnabled(false);
                break;
            case 8:
                c8.setCardBackgroundColor(Color.RED);
                c8.setEnabled(false);
                break;
            case 9:
                c9.setCardBackgroundColor(Color.RED);
                c9.setEnabled(false);
                break;
            case 10:
                c10.setCardBackgroundColor(Color.RED);
                c10.setEnabled(false);
                break;
            case 11:
                c11.setCardBackgroundColor(Color.RED);
                c11.setEnabled(false);
                break;
            case 12:
                c12.setCardBackgroundColor(Color.RED);
                c12.setEnabled(false);
                break;
            case 13:
                c13.setCardBackgroundColor(Color.RED);
                c13.setEnabled(false);
                break;
            case 14:
                c14.setCardBackgroundColor(Color.RED);
                c14.setEnabled(false);
                break;
            case 15:
                c15.setCardBackgroundColor(Color.RED);
                c15.setEnabled(false);
                break;
            case 16:
                c16.setCardBackgroundColor(Color.RED);
                c16.setEnabled(false);
                break;
            case 17:
                c17.setCardBackgroundColor(Color.RED);
                c17.setEnabled(false);
                break;
            case 18:
                c18.setCardBackgroundColor(Color.RED);
                c18.setEnabled(false);
                break;
            case 19:
                c19.setCardBackgroundColor(Color.RED);
                c19.setEnabled(false);
                break;
            case 20:
                c20.setCardBackgroundColor(Color.RED);
                c20.setEnabled(false);
                break;
            case 21:
                c21.setCardBackgroundColor(Color.RED);
                c21.setEnabled(false);
                break;
            case 22:
                c22.setCardBackgroundColor(Color.RED);
                c22.setEnabled(false);
                break;
            case 23:
                c23.setCardBackgroundColor(Color.RED);
                c23.setEnabled(false);
                break;
            case 24:
                c24.setCardBackgroundColor(Color.RED);
                c24.setEnabled(false);
                break;
            case 25:
                c25.setCardBackgroundColor(Color.RED);
                c25.setEnabled(false);
                break;
            case 26:
                c26.setCardBackgroundColor(Color.RED);
                c26.setEnabled(false);
                break;
            case 27:
                c27.setCardBackgroundColor(Color.RED);
                c27.setEnabled(false);
                break;
            case 28:
                c28.setCardBackgroundColor(Color.RED);
                c28.setEnabled(false);
                break;
            case 29:
                c29.setCardBackgroundColor(Color.RED);
                c29.setEnabled(false);
                break;
            case 30:
                c30.setCardBackgroundColor(Color.RED);
                c30.setEnabled(false);
                break;
            case 31:
                c31.setCardBackgroundColor(Color.RED);
                c31.setEnabled(false);
                break;
            case 32:
                c32.setCardBackgroundColor(Color.RED);
                c32.setEnabled(false);
                break;
            case 33:
                c33.setCardBackgroundColor(Color.RED);
                c33.setEnabled(false);
                break;
            case 34:
                c34.setCardBackgroundColor(Color.RED);
                c34.setEnabled(false);
                break;
            case 35:
                c35.setCardBackgroundColor(Color.RED);
                c35.setEnabled(false);
                break;
            case 36:
                c36.setCardBackgroundColor(Color.RED);
                c36.setEnabled(false);
                break;
            case 37:
                c37.setCardBackgroundColor(Color.RED);
                c37.setEnabled(false);
                break;
            case 38:
                c38.setCardBackgroundColor(Color.RED);
                c38.setEnabled(false);
                break;
            case 39:
                c39.setCardBackgroundColor(Color.RED);
                c39.setEnabled(false);
                break;
            case 40:
                c40.setCardBackgroundColor(Color.RED);
                c40.setEnabled(false);
                break;
            case 41:
                c41.setCardBackgroundColor(Color.RED);
                c41.setEnabled(false);
                break;
            case 42:
                c42.setCardBackgroundColor(Color.RED);
                c42.setEnabled(false);
                break;

            default:
                break;
        }
    }

    private void setColorGreen(int i) {

        switch (i) {
            case 1: c1.setCardBackgroundColor(Color.GREEN);
                break;
            case 2:
                c2.setCardBackgroundColor(Color.GREEN);
                break;
            case 3:
                c3.setCardBackgroundColor(Color.GREEN);
                break;
            case 4:
                c4.setCardBackgroundColor(Color.GREEN);
                break;
            case 5:
                c5.setCardBackgroundColor(Color.GREEN);
                break;
            case 6:
                c6.setCardBackgroundColor(Color.GREEN);
                break;
            case 7:
                c7.setCardBackgroundColor(Color.GREEN);
                break;
            case 8:
                c8.setCardBackgroundColor(Color.GREEN);
                break;
            case 9:
                c9.setCardBackgroundColor(Color.GREEN);
                break;
            case 10:
                c10.setCardBackgroundColor(Color.GREEN);
                break;
            case 11:
                c11.setCardBackgroundColor(Color.GREEN);
                break;
            case 12:
                c12.setCardBackgroundColor(Color.GREEN);
                break;
            case 13:
                c13.setCardBackgroundColor(Color.GREEN);
                break;
            case 14:
                c14.setCardBackgroundColor(Color.GREEN);
                break;
            case 15:
                c15.setCardBackgroundColor(Color.GREEN);
                break;
            case 16:
                c16.setCardBackgroundColor(Color.GREEN);
                break;
            case 17:
                c17.setCardBackgroundColor(Color.GREEN);
                break;
            case 18:
                c18.setCardBackgroundColor(Color.GREEN);
                break;
            case 19:
                c19.setCardBackgroundColor(Color.GREEN);
                break;
            case 20:
                c20.setCardBackgroundColor(Color.GREEN);
                break;
            case 21:
                c21.setCardBackgroundColor(Color.GREEN);
                break;
            case 22:
                c22.setCardBackgroundColor(Color.GREEN);
                break;
            case 23:
                c23.setCardBackgroundColor(Color.GREEN);
                break;
            case 24:
                c24.setCardBackgroundColor(Color.GREEN);
                break;
            case 25:
                c25.setCardBackgroundColor(Color.GREEN);
                break;
            case 26:
                c26.setCardBackgroundColor(Color.GREEN);
                break;
            case 27:
                c27.setCardBackgroundColor(Color.GREEN);
                break;
            case 28:
                c28.setCardBackgroundColor(Color.GREEN);
                break;
            case 29:
                c29.setCardBackgroundColor(Color.GREEN);
                break;
            case 30:
                c30.setCardBackgroundColor(Color.GREEN);
                break;
            case 31:
                c31.setCardBackgroundColor(Color.GREEN);
                c31.setEnabled(false);
                break;
            case 32:
                c32.setCardBackgroundColor(Color.GREEN);
                c32.setEnabled(false);
                break;
            case 33:
                c33.setCardBackgroundColor(Color.GREEN);
                c33.setEnabled(false);
                break;
            case 34:
                c34.setCardBackgroundColor(Color.GREEN);
                c34.setEnabled(false);
                break;
            case 35:
                c35.setCardBackgroundColor(Color.GREEN);
                c35.setEnabled(false);
                break;
            case 36:
                c36.setCardBackgroundColor(Color.GREEN);
                c36.setEnabled(false);
                break;
            case 37:
                c37.setCardBackgroundColor(Color.GREEN);
                c37.setEnabled(false);
                break;
            case 38:
                c38.setCardBackgroundColor(Color.GREEN);
                c38.setEnabled(false);
                break;
            case 39:
                c39.setCardBackgroundColor(Color.GREEN);
                c39.setEnabled(false);
                break;
            case 40:
                c40.setCardBackgroundColor(Color.GREEN);
                c40.setEnabled(false);
                break;
            case 41:
                c41.setCardBackgroundColor(Color.GREEN);
                c41.setEnabled(false);
                break;
            case 42:
                c42.setCardBackgroundColor(Color.GREEN);
                c42.setEnabled(false);
                break;

            default:
                break;
        }
    }

    private void setTime(int i) {

        switch (i) {
            case 1:
                time = "08:00 AM";
                break;
            case 2:
                time = "08:10 AM";
                break;
            case 3:
                time = "08:20 AM";
                break;
            case 4:
                time = "08:30 AM";
                break;
            case 5:
                time = "08:40 AM";
                break;
            case 6:
                time = "08:50 AM";
                break;
            case 7:
                time = "09:00 AM";
                break;
            case 8:
                time = "09:10 AM";
                break;
            case 9:
                time = "09:20 AM";
                break;
            case 10:
                time = "09:30 AM";
                break;
            case 11:
                time = "09:40 AM";
                break;
            case 12:
                time = "09:50 AM";
                break;
            case 13:
                time = "10:00 PM";
                break;
            case 14:
                time = "10:10 PM";
                break;
            case 15:
                time = "10:20 PM";
                break;
            case 16:
                time = "10:30 PM";
                break;
            case 17:
                time = "10:40 PM";
                break;
            case 18:
                time = "10:50 PM";
                break;
            case 19:
                time = "11:00 PM";
                break;
            case 20:
                time = "11:10 PM";
                break;
            case 21:
                time = "11:20 PM";
                break;
            case 22:
                time = "02:00 PM";
                break;
            case 23:
                time = "02:10 PM";
                break;
            case 24:
                time = "02:20 PM";
                break;
            case 25:
                time = "02:30 PM";
                break;
            case 26:
                time = "02:40 PM";
                break;
            case 27:
                time = "02:50 PM";
                break;
            case 28:
                time = "03:00 PM";
                break;
            case 29:
                time = "03:10 PM";
                break;
            case 30:
                time = "03:20 PM";
                break;
            case 31:
                time = "03:30 PM";
                break;
            case 32:
                time = "03:40 PM";
                break;
            case 33:
                time = "03:50 PM";
                break;
            case 34:
                time = "04:00 PM";
                break;
            case 35:
                time = "04:10 PM";
                break;
            case 36:
                time = "04:20 PM";
                break;
            case 37:
                time = "04:30 PM";
                break;
            case 38:
                time = "04:40 PM";
                break;
            case 39:
                time = "04:50 PM";
                break;
            case 40:
                time = "05:00 PM";
                break;
            case 41:
                time = "05:10 PM";
                break;
            case 42:
                time = "05:20 PM";
                break;
            default:
                break;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "You are not Logged In.....Login first for further process", Toast.LENGTH_SHORT).show();

            Intent login_Intent = new Intent(Patient_BookAppointmentActivity.this,LoginActivity.class);
            startActivity(login_Intent);
        }else {
            flagChecked=0;
            mDataBaseRef.child(getIntent().getStringExtra("DoctorUserId").toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(date)){

                        mDataBaseRef.child(getIntent().getStringExtra("DoctorUserId").toString()).child(date).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (int i =1;i<=42;i++) {

                                    if(dataSnapshot.hasChild(String.valueOf(i)))
                                    {
                                        setColorRed(i);

                                    }
                                    else
                                    {
                                        setDefaultColor(i);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else {
                        for (int i =1;i<=42;i++)
                        {
                            setDefaultColor(i);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
}
