package com.example.aman.hospitalappointy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;


public class DeclareActivity extends AppCompatActivity {
    private CountryCodePicker country;
    private Toolbar mToolbar;
    private EditText mName,mCMT,mAge,mPhone,mTinh,mHuyen,mTT,mThon,mEmail,mSex,mTrip;
    private Button mSubmit;
    private RadioGroup mSot,mHo,mKhotho,mViemphoi,mDauhong,mMetmoi;
    private RadioGroup mGan,mMau,mThan,mTim,mHuyet,mSuy,mNguoi,mTieu,mUng,mDang;
    private RadioGroup mTruong,mDi,mTiep;

    private DatabaseReference mDeclareDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        country = findViewById(R.id.ccp);

        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Khai báo y tế");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName =(EditText)findViewById(R.id.name_declare);
        mCMT =(EditText)findViewById(R.id.passport_declare);
        mAge =(EditText)findViewById(R.id.born_declare);
        mSex =(EditText)findViewById(R.id.sex_declare);
        mTinh =(EditText)findViewById(R.id.city_declare);
        mHuyen =(EditText)findViewById(R.id.district_declare);
        mTT =(EditText)findViewById(R.id.town_declare);
        mThon =(EditText)findViewById(R.id.village_declare);
        mEmail =(EditText)findViewById(R.id.email_declare);
        mPhone=(EditText)findViewById(R.id.phone_declare);
        mTrip=(EditText)findViewById(R.id.trip_declare);

        mSubmit=(Button)findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= mName.getText().toString();
                String cmt= mCMT.getText().toString();
                String age= mAge.getText().toString();
                String sex= mSex.getText().toString();
                String tinh= mTinh.getText().toString();
                String huyen= mHuyen.getText().toString();
                String tt= mTT.getText().toString();
                String thon= mThon.getText().toString();
                String email= mEmail.getText().toString();
                String phone= mPhone.getText().toString();
                String trip= mTrip.getText().toString();
                String conun=country.getSelectedCountryName().toString();
                String sympon="";
                String sick="";
                String contact="";



                mHo = (RadioGroup) findViewById(R.id.ho_radiogroup) ;
                int check1 =mHo.getCheckedRadioButtonId();
                if(check1==R.id.ho1_radiobtn)
                {
                    sympon="Ho";
                }
                mSot = (RadioGroup) findViewById(R.id.sot_radiogroup) ;
                int check2 =mSot.getCheckedRadioButtonId();
                if(check2==R.id.sot1_radiobtn)
                {
                    sympon=sympon+", sốt";
                }
                mKhotho = (RadioGroup) findViewById(R.id.khotho_radiogroup) ;
                int check3 =mKhotho.getCheckedRadioButtonId();
                if(check3==R.id.khotho1_radiobtn)
                {
                    sympon=sympon+", khó thở";
                }
                mDauhong = (RadioGroup) findViewById(R.id.dauhong_radiogroup) ;
                int check4 =mDauhong.getCheckedRadioButtonId();
                if(check4==R.id.dauhong1_radiobtn)
                {
                    sympon=sympon+", đau họng";
                }
                mMetmoi = (RadioGroup) findViewById(R.id.metmoi_radiogroup) ;
                int check5 =mMetmoi.getCheckedRadioButtonId();
                if(check5==R.id.metmoi1_radiobtn)
                {
                    sympon=sympon+", mệt mỏi";
                }
                mViemphoi = (RadioGroup) findViewById(R.id.viemphoi_radiogroup) ;
                int check6 =mViemphoi.getCheckedRadioButtonId();
                if(check6==R.id.viemphoi1_radiobtn)
                {
                    sympon=sympon+", viêm phổi";
                }

                String finalSympon = sympon;


                mGan = (RadioGroup) findViewById(R.id.gan_radiogroup) ;
                int check7 =mGan.getCheckedRadioButtonId();
                if(check7==R.id.gan1_radiobtn)
                {
                    sick="Gan mãn tính";
                }
                mMau = (RadioGroup) findViewById(R.id.mau_radiogroup) ;
                int check8 =mMau.getCheckedRadioButtonId();
                if(check8==R.id.mau1_radiobtn)
                {
                    sick=sick+", máu mãn tính";
                }
                mTim = (RadioGroup) findViewById(R.id.tim_radiogroup) ;
                int check9 =mTim.getCheckedRadioButtonId();
                if(check9==R.id.tim1_radiobtn)
                {
                    sick=sick+", tim mạch";
                }
                mHuyet = (RadioGroup) findViewById(R.id.cao_radiogroup) ;
                int check10 =mHuyet.getCheckedRadioButtonId();
                if(check10==R.id.cao1_radiobtn)
                {
                    sick=sick+", cao huyết áp";
                }
                mSuy = (RadioGroup) findViewById(R.id.suy_radiogroup) ;
                int check11 =mSuy.getCheckedRadioButtonId();
                if(check5==R.id.suy1_radiobtn)
                {
                    sick=sick+", suy giảm miễn dịch";
                }
                mNguoi = (RadioGroup) findViewById(R.id.nguoi_radiogroup) ;
                int check12 =mNguoi.getCheckedRadioButtonId();
                if(check12==R.id.nguoi1_radiobtn)
                {
                    sick=sick+", người nhận ghép tạng, tủy xương";
                }
                mTieu = (RadioGroup) findViewById(R.id.tieu_radiogroup) ;
                int check13 =mTieu.getCheckedRadioButtonId();
                if(check13==R.id.tieu1_radiobtn)
                {
                    sick=sick+", tiểu đường";
                }

                mUng = (RadioGroup) findViewById(R.id.ung_radiogroup) ;
                int check14 =mUng.getCheckedRadioButtonId();
                if(check14==R.id.ung1_radiobtn)
                {
                    sick=sick+", ung thư";
                }

                mDang = (RadioGroup) findViewById(R.id.dang_radiogroup) ;
                int check15 =mDang.getCheckedRadioButtonId();
                if(check15==R.id.dang1_radiobtn)
                {
                    sick=sick+", đang trong thai kỳ";
                }
                mThan = (RadioGroup) findViewById(R.id.than_radiogroup) ;
                int check16 =mThan.getCheckedRadioButtonId();
                if(check16==R.id.than1_radiobtn)
                {
                    sick=sick+", thận mãn tính";
                }


                String finalSick = sick;
                mTruong = (RadioGroup) findViewById(R.id.truong_radiogroup) ;
                int check17 =mTruong.getCheckedRadioButtonId();
                if(check17==R.id.truong1_radiobtn)
                {
                    contact=contact+"Có tiếp xúc với trường hợp nghi nhiễm hoặc nhiễm covid-19";
                }

                mDi = (RadioGroup) findViewById(R.id.di_radiogroup) ;
                int check18 =mDi.getCheckedRadioButtonId();
                if(check18==R.id.di1_radiobtn)
                {
                    contact=contact+" có đi về từ vùng dịch";
                }
                mTiep = (RadioGroup) findViewById(R.id.tiep_radiogroup) ;
                int check19 =mTiep.getCheckedRadioButtonId();
                if(check19==R.id.tiep1_radiobtn)
                {
                    contact=contact+", có tiếp xúc với người về từ vùng dịch";
                }


                String finalContact = contact;

                mDeclareDatabase.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String,String> detail= new HashMap <>();
                        detail.put("Name",name);
                        detail.put("CMT",cmt);
                        detail.put("Age",age);
                        detail.put("Conuntry",conun);
                        detail.put("Address",thon +" "+ tt + " " + huyen +" " + tinh);
                        detail.put("Email",email);
                        detail.put("Phone",phone);
                        detail.put("Trip",trip);
                        detail.put("Sympon", finalSympon);
                        detail.put("Sick", finalSick);
                        detail.put("Contact", finalContact);
                        mDeclareDatabase.child("Declare").child(mAuth.getCurrentUser().getUid()).push().setValue(detail);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(DeclareActivity.this, DeclareActivity.class));

            }
        });
    }


}
