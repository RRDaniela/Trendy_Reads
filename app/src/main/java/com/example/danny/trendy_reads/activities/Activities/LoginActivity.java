package com.example.danny.trendy_reads.activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.danny.trendy_reads.activities.Activities.Register.RegisterActivityEmail;
import com.example.danny.trendy_reads.activities.CustomViewPagerAdapter;
import com.example.danny.trendy_reads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin, btnRegister;
    private ProgressBar loginProgress,registerProgress;
    private FirebaseAuth mAuth;
    private Intent HomeaActivity;

    private static final long SLIDER_TIMER = 2000; // change slider interval
    private int currentPage = 0; // this will tell us the current page available on the view pager
    // please see ViewPager Listener on the onPageSelected method to see how we are updating
    // currentPage variable

    private boolean isCountDownTimerActive = false; // let the timer start if and only if it has completed previous task


    private Handler handler;
    private ViewPager viewPager;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (!isCountDownTimerActive) {
                automateSlider();
            }

            handler.postDelayed(runnable, 1000);
            // our runnable should keep running for every 1000 milliseconds (1 seconds)
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userMail=findViewById(R.id.loginMail);
        userPassword=findViewById(R.id.loginPassword);
        btnLogin=findViewById(R.id.loginButton);
        loginProgress=findViewById(R.id.loginprogress);
        registerProgress=findViewById(R.id.registerprogress);
        mAuth= FirebaseAuth.getInstance();
        HomeaActivity= new Intent( this, Home.class);
        registerProgress.setVisibility(View.INVISIBLE);
        btnRegister = findViewById(R.id.registerbtn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerProgress.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.INVISIBLE);
                loginProgress.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                Intent registerActivity=new Intent(getApplicationContext(), RegisterActivityEmail.class);
                startActivity(registerActivity);
                finish();
            }
        });



        registerProgress.setVisibility(View.INVISIBLE);
        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                registerProgress.setVisibility(View.INVISIBLE);
                btnRegister.setVisibility(View.INVISIBLE);

                final String mail=userMail.getText().toString();
                final String password=userPassword.getText().toString();


                if(mail.isEmpty() || password.isEmpty()){

                    showMessage("Verifique los datos ingresados.");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    registerProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    SignIn(mail,password);
                }


            }
        });


        //toolbar

        handler = new Handler();

        handler.postDelayed(runnable, 1000);
        runnable.run();

        viewPager = findViewById(R.id.view_pager_slider);

        CustomViewPagerAdapter viewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // now it's time to think about our sliders


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    currentPage = 0;
                } else if (position == 1) {
                    currentPage = 1;
                } else {
                    currentPage = 2;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void SignIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loginProgress.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                    registerProgress.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.INVISIBLE);
                    UpdateUI();
                }
                else{
                    showMessage(task.getException().getMessage());
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void UpdateUI() {

        startActivity(HomeaActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void automateSlider() {
        isCountDownTimerActive = true;
        new CountDownTimer(SLIDER_TIMER, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                int nextSlider = currentPage + 1;



                if (nextSlider == 3) {
                    nextSlider = 0; // if it's last Image, let it go to the first image
                }

                viewPager.setCurrentItem(nextSlider);
                isCountDownTimerActive = false;
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Kill this background task once the activity has been killed
        handler.removeCallbacks(runnable);
    }

    protected void onStart(){
    super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        if(user!=null){
            UpdateUI();
        }
    }
}