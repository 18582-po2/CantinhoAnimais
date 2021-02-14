package com.example.cantinhodosanimais;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView tv_signup;
    private TextInputEditText text_input_email, text_input_password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private ProgressBar login_progressBar;
    private List<QueryDocumentSnapshot> cantinhoEmployees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        cantinhoEmployees = getCantinhoEmployees();
        login_progressBar = findViewById(R.id.progressBar_login);
        text_input_email = findViewById(R.id.input_username);
        text_input_password = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        tv_signup = findViewById(R.id.textView_signup);
        tv_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            loginUser();
        }
        if (v.getId() == R.id.textView_signup) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    }

    private List<QueryDocumentSnapshot> getCantinhoEmployees() {
        List<QueryDocumentSnapshot> list = new ArrayList<>();

        mStore.collection("funcionariosCantinhoAnimais")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document);
                            }
                        } else {
                            //Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return list;
    }

    private void loginUser() {
        String email = text_input_email.getText().toString().trim();
        String password = text_input_password.getText().toString().trim();

        if (email.isEmpty()) {
            text_input_email.setError("Preencha o seu  email!");
            text_input_email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            text_input_email.setError("Por favor insira um email válido!");
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

        login_progressBar.setVisibility(View.VISIBLE);

        loginUserFromFireBase(email, password);

    }

    private void loginUserFromFireBase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                if(isAnEmployee(email)){
                    startActivity(new Intent(LoginActivity.this, MainContainerAdminActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainContainerUserActivity.class));
                    finish();
                }

            } else {
                Toast.makeText(this, "Credenciais inválidas!", Toast.LENGTH_LONG).show();
            }
        });

    }


    private boolean isAnEmployee(String email) {

        for (int i = 0; i < cantinhoEmployees.size() ; i++) {
            String employee_email = cantinhoEmployees.get(i).get("email").toString();
            if (email.equals(employee_email)){
                return true;
            }
        }
        return false;
    }

}
