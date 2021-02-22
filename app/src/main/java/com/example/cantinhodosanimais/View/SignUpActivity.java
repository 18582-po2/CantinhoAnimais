package com.example.cantinhodosanimais.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for sign-ups before the user logs-in
 * Here, any user can register to have an account and therefore
 * use the application any time they want.
 * The data generated in the sign-up is kept in the database for later log-ins.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register;
    private TextView tv_login;
    private TextInputEditText text_input_email, text_input_password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private ProgressBar sign_up_progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        sign_up_progressBar = findViewById(R.id.progressBar_signup);
        text_input_email = findViewById(R.id.input_username);
        text_input_password = findViewById(R.id.input_password);
        tv_login = findViewById(R.id.textView_login);
        tv_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_registar);
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_registar) {
            registerNewUser();
        }

        if (v.getId() == R.id.textView_login) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Receives the user´s input and validates them according to the restrictions bellow.
     * Calls the method to register new user into firebase so it saves.
     */
    private void registerNewUser() {

        String email = text_input_email.getText().toString();
        String password = text_input_password.getText().toString();

        if (email.isEmpty()) {
            text_input_email.setError("Preencha o seu  email!");
            text_input_email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            text_input_email.setError("Por favor, insira um email válido!"); //when it doesn't have a "@"
            text_input_email.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            text_input_password.setError("Preencha a password!");
            text_input_password.requestFocus();
            return;
        } else if (password.length() < 6) {
            text_input_password.setError("Palavra-passe deve conter no mínimo 6 caracteres!");
            text_input_password.requestFocus();
            return;
        }

        sign_up_progressBar.setVisibility(View.VISIBLE);

        registerNewUserIntoFirebase(email, password);

    }


    /**
     * This method saves the new user into firebase into the collection "utilizadores" .
     * @param email
     * @param password
     */
    private void registerNewUserIntoFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String , Object> user = new HashMap<>();
                    user.put("email", email);

                    mStore.collection("utilizadores")
                            .document(mAuth.getCurrentUser().getUid()).set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Utilizador foi registado!", Toast.LENGTH_SHORT).show();
                                        sign_up_progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Erro ao registar utilizador!!", Toast.LENGTH_SHORT).show();
                                        sign_up_progressBar.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                } else {
                    Toast.makeText(SignUpActivity.this, "Erro ao registar utilizador!", Toast.LENGTH_SHORT).show();
                    sign_up_progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}




