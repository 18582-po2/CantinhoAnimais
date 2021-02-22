package com.example.cantinhodosanimais.View;

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

import com.example.cantinhodosanimais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * This classe takes care of the login process, both for the adopter-user and
 * admin-user. It allows them both to have access to different pages each, depending
 * on the username they use.
 */
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

    /**
     * This method fetches data inside "funcionariosCantinhoAnimais" collection and saves it in a list
     * @return
     * Req. 6 - Fonte de dados remota
     */
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


    /**
     * Receives the credentials inserted and validates them, calling afterwards, the method that does the login.
     * Req. 6 - Fonte de dados remota
     */
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


    /**
     * Method that realizes the login and decides to which page the user is directed,
     * for example, if the user is an employee, they get an emloyee's page, otherwise, they get
     * a normal user's page.
     * @param email
     * @param password
     * Req. 6 - Fonte de dados remota
     */
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
                login_progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    /**
     * Fetches the employees list to compare if they're the same as the user's input,
     * if so, returns true, if not, false.
     * @param email
     * @return true if the e-mail's match or false if they don't match
     * Req. 6 - Fonte de dados remota
     */
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
