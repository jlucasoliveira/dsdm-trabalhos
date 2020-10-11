package br.ufc.quixada.dsdm.trabalho4.Auth;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho4.MainActivity;
import br.ufc.quixada.dsdm.trabalho4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    ProgressBar mPbLogin;
    Button mBtnLogin, mBtnLoginSignUp;
    EditText mEditEmail, mEditPassword;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mPbLogin = findViewById(R.id.pb_login);
        mBtnLogin = findViewById(R.id.btn_login);
        mEditEmail = findViewById(R.id.edit_login_email);
        mBtnLoginSignUp = findViewById(R.id.btn_login_signup);
        mEditPassword = findViewById(R.id.edit_login_password);

        mBtnLogin.setOnClickListener(this::login);
        mBtnLoginSignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));

    }

    public void login(View view) {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        mPbLogin.setVisibility(View.VISIBLE);

        if (email.isEmpty()) {
            mEditEmail.setError("Campo obrigatório");
            return;
        }

        if (password.isEmpty()) {
            mEditPassword.setError("Campo obrigatório");
            return;
        }else if (password.length() < 6) {
            mEditPassword.setError("A senha deve ter no mínimo 6 caracteres");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            mPbLogin.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else Toast.makeText(getApplicationContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
        });

    }

}