package com.example.danny.trendy_reads.activities.Activities.Register;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danny.trendy_reads.R;
import com.example.danny.trendy_reads.activities.Activities.Register.RegisterActivityEmail;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivityPassword  extends AppCompatActivity {

    private ImageView Backbutton;
    private EditText userPassword, userPassword2;
    private ProgressBar loadingProgress;
    private Button regBtn;
    String useremail;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpassword);

        //Tomar los datos del Email desde la activity pasada.
        Intent myIntent = getIntent();
        /*if(myIntent.hasExtra("myExtra")) {
            useremail = myIntent.getStringExtra("myExtra");
        }
        */

        userPassword = findViewById(R.id.password1);
        userPassword2 = findViewById(R.id.password2);
        loadingProgress = findViewById(R.id.registerProgress1);
        regBtn = findViewById(R.id.RegBttn);
        mAuth = FirebaseAuth.getInstance();

        //Botón para regresar a la otra Activity
        Backbutton = findViewById(R.id.backbutton);
        Backbutton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registeractivitypassback = new Intent(getApplicationContext(), RegisterActivityEmail.class);
                startActivity(registeractivitypassback);
                finish();
            }
        }));

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                //final String email=useremail;
                final String password= userPassword.getText().toString();
                final String password2=userPassword2.getText().toString();

                if (password.isEmpty() || !password.equals((password2))) {
                    showMessage("Ingrese la misma contraseña");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    showMessage("Hola");
                    //CreateUserAccount(email, password);
                }
            }
        });


        //Put code about users photo here.
       // Intent i = getIntent();
        //String email = i.getExtras().getString("Your_KEY");


    }

  /*  private void CreateUserAccount(String email, String password) {
        //Proceso para mandar datos a firebase.
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    showMessage("Cuenta creada.");
                }
                else{
                    showMessage("La cuenta no ha sido creada" + task.getException().getMessage());
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }

            }
        });

    }*/
    //ENVIAR MENSAJES

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}

