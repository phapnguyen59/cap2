package com.example.aman.hospitalappointy;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doctor_EditProfileActivity extends AppCompatActivity {

    private TextView mName, mEmail, mSpecialization, mExperiance, mAge, mContact, mAddress, mEducation;
    private Toolbar mToolbar;
    private String name,specialization,experiance,education,email,age,contact,address,update;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__edit_profile);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.doctor_editProfile_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mName = (TextView) findViewById(R.id.edit_doctor_name);
        mSpecialization = (TextView) findViewById(R.id.edit_doctor_specialization);
        mExperiance = (TextView) findViewById(R.id.edit_doctor_experiance);
        mEducation = (TextView) findViewById(R.id.edit_doctor_education);
        mEmail = (TextView) findViewById(R.id.edit_doctor_email);
        mAge = (TextView) findViewById(R.id.edit_doctor_age);
        mContact = (TextView) findViewById(R.id.edit_doctor_contact);
        mAddress = (TextView) findViewById(R.id.edit_doctor_address);


    }

    public void update(View view){

        switch (view.getId()){

            case R.id.edit_name:
                alertDialog(name,"Name");
                break;

            case R.id.edit_experiance:
                alertDialog(experiance,"Experience");
                break;

            case R.id.edit_education:
                alertDialog(education,"Education");
                break;
            case R.id.edit_address:
                alertDialog(address,"Address");
                break;
            case R.id.edit_age:
                alertDialog(age,"Age");
                break;
            case R.id.edit_contact:
                alertDialog(contact,"Contact");
                break;
            case R.id.final_update:
                updateDoctorProfile();
                break;
            default:
                break;
        }

    }

    private void updateDoctorProfile() {

        String currentUser = mAuth.getCurrentUser().getUid().toString();

        mDatabase.child(currentUser).child("Name").setValue(mName);
        mDatabase.child(currentUser).child("Experiance").setValue(mExperiance);
        mDatabase.child(currentUser).child("Education").setValue(mEducation);
        mDatabase.child(currentUser).child("Address").setValue(mAddress);
        mDatabase.child(currentUser).child("Contact").setValue(mContact);
        mDatabase.child(currentUser).child("Age").setValue(mAge);

        startActivity(new Intent(Doctor_EditProfileActivity.this,Doctor_ProfileActivity.class));



    }

    private void alertDialog(String text, final String detail){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.udate_dialog, null);


        TextView textView = (TextView) view.findViewById(R.id.update_textView);
        final EditText editText = (EditText) view.findViewById(R.id.editText);

        textView.setText(detail);
        editText.setText(text, TextView.BufferType.EDITABLE);

        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                update = editText.getText().toString();

                if(detail.equals("Name")){
                    mName.setText(update);
                    name = mName.getText().toString();
                }
                else if(detail.equals("Experience")){
                    mExperiance.setText(update);
                    experiance = mExperiance.getText().toString();
                }
                else if(detail.equals("Education")){
                    mEducation.setText(update);
                    education = mEducation.getText().toString();
                }
                else if(detail.equals("Address")){
                    mAddress.setText(update);
                    address = mAddress.getText().toString();
                }
                else if(detail.equals("Age")){
                    mAge.setText(update);
                    age = mAge.getText().toString();
                }
                else if(detail.equals("Contact")){
                    mContact.setText(update);
                    contact = mContact.getText().toString();
                }


            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("Doctor_Details").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("Name").getValue().toString();
                email = dataSnapshot.child("Email").getValue().toString();
                contact = dataSnapshot.child("Contact").getValue().toString();
                education = dataSnapshot.child("Education").getValue().toString();
                specialization = dataSnapshot.child("Specialization").getValue().toString();
                experiance = dataSnapshot.child("Experiance").getValue().toString();
                age = dataSnapshot.child("Age").getValue().toString();
                address = dataSnapshot.child("Address").getValue().toString();

                mName.setText(name);
                mSpecialization.setText(specialization);
                mExperiance.setText(experiance);
                mEducation.setText(education);
                mEmail.setText(email);
                mAge.setText(age);
                mContact.setText(contact);
                mAddress.setText(address);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
