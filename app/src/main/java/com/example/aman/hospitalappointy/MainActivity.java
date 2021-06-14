package com.example.aman.hospitalappointy;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private String Type = "", status = "";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment_SectionPagerAdapter mFragment_SectionPagerAdapter;

    //Firebase Auth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Doctor Clinic");

        //DrawerLayout and ToggleButton
        mDrawerLayout = findViewById(R.id.main_drawerLayout);
        mToggle = new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        //TabLayout , SectionPagerAdapter & ViewPager
        mViewPager = (ViewPager) findViewById(R.id.main_ViewPager);
        mFragment_SectionPagerAdapter = new Fragment_SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragment_SectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }



    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, currentUser.getUid().toString(), Toast.LENGTH_SHORT).show();

        Menu menuNav = mNavigationView.getMenu();
        final MenuItem nav_profile = menuNav.findItem(R.id.nav_profile);
        final MenuItem nav_ShowAppointment = menuNav.findItem(R.id.nav_showAppointment);
        final MenuItem nav_BookedAppointment = menuNav.findItem(R.id.nav_bookedAppointment);
        final MenuItem nav_feedback = menuNav.findItem(R.id.nav_feedback);
        final MenuItem nav_declare = menuNav.findItem(R.id.nav_declare);
        final MenuItem nav_history = menuNav.findItem(R.id.nav_history);

        MenuItem nav_logOut = menuNav.findItem(R.id.nav_logout);
        MenuItem nav_logIn = menuNav.findItem(R.id.nav_login);

        nav_profile.setVisible(false);
        nav_ShowAppointment.setVisible(false);
        nav_BookedAppointment.setVisible(false);
        nav_logIn.setVisible(false);
        nav_logOut.setVisible(false);
        nav_feedback.setVisible(false);
        nav_declare.setVisible(false);
        nav_history.setVisible(false);




        // Check if user is signed in  or not
        if(currentUser == null){
            nav_logIn.setVisible(true);

            View mView = mNavigationView.getHeaderView(0);
            TextView userName = (TextView) mView.findViewById(R.id.header_userName);
            TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);

            userName.setText("User Name");
            userEmail.setText("User Email");

            Toast.makeText(getBaseContext(),"Your Account is not Logged In ",Toast.LENGTH_LONG).show();
        }else {
            nav_logOut.setVisible(true);
            chechType();
        }
    }

    private void chechType() {

        Menu menuNav = mNavigationView.getMenu();
        final MenuItem nav_profile = menuNav.findItem(R.id.nav_profile);
        final MenuItem nav_ShowAppointment = menuNav.findItem(R.id.nav_showAppointment);
        final MenuItem nav_BookedAppointment = menuNav.findItem(R.id.nav_bookedAppointment);
        final MenuItem nav_feedback = menuNav.findItem(R.id.nav_feedback);
        final MenuItem nav_declare = menuNav.findItem(R.id.nav_declare);
        final MenuItem nav_history = menuNav.findItem(R.id.nav_history);

        nav_profile.setVisible(false);
        nav_ShowAppointment.setVisible(false);
        nav_BookedAppointment.setVisible(false);
        nav_feedback.setVisible(false);
        nav_declare.setVisible(false);
        nav_history.setVisible(false);

        final String uid = mAuth.getUid().toString();

        mUserDatabase.child("User_Type").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Type = (String) dataSnapshot.child("Type").getValue();
                status = (String) dataSnapshot.child("Status").getValue();
//                Toast.makeText(MainActivity.this, status+" -"+Type, Toast.LENGTH_SHORT).show();

                if(Type.equals("Patient")){

                    nav_BookedAppointment.setVisible(true);
                    nav_history.setVisible(true);
                    nav_feedback.setVisible(true);
                    nav_declare.setVisible(true);



                    mUserDatabase.child("Patient_Details").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("Name").getValue().toString();
                            String email = dataSnapshot.child("Email").getValue().toString();

                            View mView = mNavigationView.getHeaderView(0);
                            TextView userName = (TextView) mView.findViewById(R.id.header_userName);
                            TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);

                            userName.setText(name);
                            userEmail.setText(email);

                            Toast.makeText(MainActivity.this, "Your Are Logged In", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    if (Type.equals("Doctor")) {
                        nav_profile.setVisible(true);
                        nav_ShowAppointment.setVisible(true);
                        nav_feedback.setVisible(true);
                        nav_BookedAppointment.setVisible(true);
                        nav_declare.setVisible(true);
                        nav_history.setVisible(true);



                        mUserDatabase.child("Doctor_Details").child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String name = dataSnapshot.child("Name").getValue().toString();
                                String email = dataSnapshot.child("Email").getValue().toString();

                                View mView = mNavigationView.getHeaderView(0);
                                TextView userName = (TextView) mView.findViewById(R.id.header_userName);
                                TextView userEmail = (TextView) mView.findViewById(R.id.header_userEmail);

                                userName.setText(name);
                                userEmail.setText(email);

                                Toast.makeText(MainActivity.this, "Your Are Logged In", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "You are not authorized for this facility or Account Under Pending", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        onStart();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_profile:
                //Toast.makeText(getBaseContext(),"Profile Clicked",Toast.LENGTH_LONG).show();
                Intent profile_Intent = new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
                startActivity(profile_Intent);

                break;

            case R.id.nav_showAppointment:
//                Toast.makeText(getBaseContext(),"Show Appointment Clicked",Toast.LENGTH_LONG).show();
                Intent showAppointment_Intent = new Intent(getApplicationContext(),Doctor_ShowAppointmentActivity.class);
                startActivity(showAppointment_Intent);
                break;

            case R.id.nav_bookedAppointment:
//                Toast.makeText(getBaseContext(),"Booked Appointment Clicked",Toast.LENGTH_LONG).show();
                Intent bookedAppointment_Intent = new Intent(getApplicationContext(),Patient_ShowBookedAppointmentActivity.class);
                startActivity(bookedAppointment_Intent);
                break;

            case R.id.nav_login:
                Intent login_Intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login_Intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                onStart();
                Toast.makeText(getBaseContext(),"Successfully Logged Out",Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_feedback:

                Intent feedback_Intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(feedback_Intent);
                break;
            case R.id.nav_declare:

                Intent declare_Intent = new Intent(getApplicationContext(), DeclareActivity.class);
                startActivity(declare_Intent);
                break;
            case R.id.nav_history:

                Intent history_Intent = new Intent(getApplicationContext(), Historysick.class);
                startActivity(history_Intent);
                break;
            default:

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
