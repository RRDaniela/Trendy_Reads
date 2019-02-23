package com.example.danny.trendy_reads.activities.Activities.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.danny.trendy_reads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private TextInputEditText mstatusinput;
    private Button mbutton;

    //Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;


    //Progress
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String name = mCurrentUser.getDisplayName();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(name);


        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Descripción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_value = getIntent().getStringExtra("status_value");

        //Progress
        mProgress = new ProgressDialog(this);


        mstatusinput = (TextInputEditText)findViewById(R.id.status_input);
        mbutton = findViewById(R.id.button);

        mstatusinput.setText(status_value);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progress
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Guardando los cambios.");
                mProgress.setTitle("Por favor espera mientras se guardan los datos.");
                mProgress.show();

             //   String status = mstatusinput.getEditText().getText().toString();
                String status = mstatusinput.getText().toString();
                mStatusDatabase.child("descripción").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                            Intent Back = new Intent(StatusActivity.this, SettingsActivity.class);
                            startActivity(Back);

                        } else {

                            Toast.makeText(getApplicationContext(), "Ocurrió un error al guardar los datos.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }

}
