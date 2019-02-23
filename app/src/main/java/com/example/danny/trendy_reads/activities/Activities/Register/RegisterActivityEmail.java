package com.example.danny.trendy_reads.activities.Activities.Register;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danny.trendy_reads.activities.CustomViewPagerAdapter;
import com.example.danny.trendy_reads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivityEmail extends AppCompatActivity {
/*


//VERIFICAR TODO ESTE CÓDIGO


    private Button mailContinue;
    private EditText userEmail;
    private ProgressBar mailProgress;
    private Intent registerActivityPassword;

    //Image Slider
    private static final long SLIDER_TIMER = 2000; // change slider interval
    private int currentPage = 0;
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
        setContentView(R.layout.activity_registermail);
        registerActivityPassword= new Intent(this, RegisterActivityPassword.class);
*/
        //userEmail=findViewById(R.id.registerMail1);
        //mailContinue=findViewById(R.id.registerButton1);
        //mailProgress=findViewById(R.id.registerProgress1);
        //registerActivityPassword= new Intent(this, RegisterActivityPassword.class);
        /*mailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailContinue.setVisibility(View.INVISIBLE);
                mailProgress.setVisibility(View.VISIBLE);
            }
        });*/
  /*      handler = new Handler();
        handler.postDelayed(runnable, 1000);
        runnable.run();
        viewPager = findViewById(R.id.view_pager_slider);
        CustomViewPagerAdapter viewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
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

    public void sendMessage(View view){

        Intent myIntent = new Intent(this, RegisterActivityPassword.class);
        EditText mEdittext = (EditText)findViewById(R.id.registerMailuser);
        String str = mEdittext.getText().toString();
        myIntent.putExtra("myExtra", str);
        startActivity(myIntent);
        /*EditText editText = findViewById(R.id.registerMailuser);
        mailContinue=findViewById(R.id.registerButton1);
        mailProgress=findViewById(R.id.registerProgress1);
        userEmail=findViewById(R.id.registerMailuser);
        String message = editText.getText().toString();
        Intent intent = new Intent(this, RegisterActivityPassword.class);
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
        mailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailContinue.setVisibility(View.INVISIBLE);
                mailProgress.setVisibility(View.VISIBLE);
                final String message=userEmail.getText().toString();
        if (message.isEmpty()){
            showMessage("Ingrese un correo electrónico");
            mailProgress.setVisibility(View.INVISIBLE);
            mailContinue.setVisibility(View.VISIBLE);
        }
        else{
            IrASiguiente();
        }

        }});*/


/*

    }


    private void IrASiguiente() {
        startActivity(registerActivityPassword);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
}
*/


    static int PReqCode = 1;
    static int REQUESCODE = 1;

    private EditText userEmail, userPassword, userPassword2;
    private ProgressBar loadingProgress;
    private Button regBtn;

    private static final long SLIDER_TIMER = 2000; // change slider interval
    private int currentPage = 0;

    private boolean isCountDownTimerActive = false;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Handler handler;
    private ViewPager viewPager;
    DatabaseReference databaseReference;

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registermail);

        //inu
        userEmail= findViewById(R.id.RegMail);
        userPassword= findViewById(R.id.RegPassword);
        userPassword2=findViewById(R.id.RegPassword2);
        loadingProgress=findViewById(R.id.registerProgress1);
        loadingProgress.setVisibility(View.INVISIBLE);
        mAuth= FirebaseAuth.getInstance();
        regBtn=findViewById(R.id.RegBttn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);

                final String email=userEmail.getText().toString();
                final String password=userPassword.getText().toString();
                final String password2=userPassword2.getText().toString();

                if( password.isEmpty() || email.isEmpty() || !password.equals(password2) ){

                    showMessage("Verifique que todos los campos se llenaron correctamente.");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    //Todo lo que el usuario ingreso es correcto y puede continuar con el proceso
                    CreateUserAccount(email,password);
                }


            }
        });

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

     /*   //Actualizar la imagen del usuario.
        ImgUserPhoto= findViewById(R.id.regUserPhoto);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22){
                    checkAndRequestForPermission();

                }
                else{
                    openGallery();
                }
            }
        });*/




    private void CreateUserAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    showMessage("Cuenta creada.");
                    UpdateUI();
                }

                else{
                    showMessage("Ocurrió un error en la creación de la cuenta." + task.getException().getMessage());
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //Actualizar la imagen y el nombre del usuario.


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

    private void UpdateUI() {
        //Cambio de vista o de activity
        Intent homeActivity = new Intent(getApplicationContext(), UpdateUserInfo.class);
        startActivity(homeActivity);
        finish();

    }

    //Manera más sencilla de realizar un toast message.
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
/*
    private void openGallery() {

        //Dejar que el usuario suba a una imagen a su perfil.
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }*/

  /*  private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(RegisterActivityEmail.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivityEmail.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivityEmail.this, "Por favor acepta para poder otorgar permiso.", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(RegisterActivityEmail.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }

        else{
            openGallery();
        }

    }*/

  /*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            //El usuario seleccionó con éxito una imagen.
            //Se guardará su referencia a una variable URI.

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }
}
*/

}