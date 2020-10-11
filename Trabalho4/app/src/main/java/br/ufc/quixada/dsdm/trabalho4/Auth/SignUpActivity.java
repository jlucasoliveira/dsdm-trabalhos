package br.ufc.quixada.dsdm.trabalho4.Auth;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho4.MainActivity;
import br.ufc.quixada.dsdm.trabalho4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    Button mBtnSignUp;
    ImageView mBackArrow;
    ProgressBar mPbSignUp;
    EditText mEditName, mEditEmail, mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mPbSignUp = findViewById(R.id.pb_signup);
        mBtnSignUp = findViewById(R.id.btn_signup);
        mBackArrow = findViewById(R.id.back_arrow);
        mEditName = findViewById(R.id.edit_signup_name);
        mEditEmail = findViewById(R.id.edit_signup_email);
        mEditPassword = findViewById(R.id.edit_signup_password);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mBtnSignUp.setOnClickListener(this::register);
        mBackArrow.setOnClickListener(v -> finish());
    }

    public void register(View view) {
        String name = mEditName.getText().toString();
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        mPbSignUp.setVisibility(View.VISIBLE);

        if (name.isEmpty()) {
            mEditName.setError("Campo obrigatório");
            return;
        }

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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            mPbSignUp.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                String newUserUid = mAuth.getCurrentUser().getUid();
                Map<String, String> data = new HashMap<>(2);
                data.put("name", name);
                data.put("email", email);
                mFirestore.collection("users").
                        document(newUserUid).set(data).addOnCompleteListener(taskinner -> {
                            if (taskinner.isSuccessful())
                                Toast.makeText(getApplicationContext(), "Usuário criado com sucesso",
                                        Toast.LENGTH_SHORT).show();
                });
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
            else Toast.makeText(getApplicationContext(), "Tente mais tarde!", Toast.LENGTH_SHORT).show();
        });
    }

}