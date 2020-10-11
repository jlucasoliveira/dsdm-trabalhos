package br.ufc.quixada.dsdm.trabalho4;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho4.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddGroupActivity extends AppCompatActivity {

    FirebaseUser mUser;
    FirebaseFirestore mFirestore;

    EditText mEditName;
    ListView mListView;
    Button mBtnAddGroup;
    ProgressBar mPbAddGroup;

    List<User> mUsers = new ArrayList<>();
    Map<String, User> mSelectedUsers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgroup);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.add_group);

        mPbAddGroup = findViewById(R.id.pb_add_group);
        mBtnAddGroup = findViewById(R.id.btn_add_group);
        mEditName = findViewById(R.id.edit_add_group_name);
        mListView = findViewById(R.id.lv_add_group_members);

        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mBtnAddGroup.setOnClickListener(this::addGroup);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            User temp = mUsers.get(position);
            if (view.isSelected()){
                mSelectedUsers.remove(temp.getId());
            }
            else {
                mSelectedUsers.put(temp.getId(), temp);
            }
            view.setSelected(!view.isSelected());
        });

        loadUsers();
    }

    public void addGroup(View view) {
        String name = mEditName.getText().toString();

        if (name.isEmpty()) {
            mEditName.setError("Adicione um nome ao grupo");
            return;
        }

        if (mSelectedUsers.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos um usuário!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> data = new HashMap<>(1);
        data.put("name", name);

        mFirestore.collection("groups").add(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, String> tempData = new HashMap<>(2);
                tempData.put("group", task.getResult().getId());
                tempData.put("user", mUser.getUid());
                for (User u : mSelectedUsers.values()) {
                    tempData.replace("user", u.getId());
                    mFirestore.collection("group-user").add(tempData);
                }
            }
            else {
                Toast.makeText(this, "Deu ruim!", Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }

    public void loadUsers() {
        mFirestore.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().forEach(snap -> {
                    if (!mUser.getUid().equals(snap.getId()))
                        mUsers.add(
                                new User(snap.getId(), snap.getString("name"), snap.getString("email")));
                });
                mListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mUsers));
                mPbAddGroup.setVisibility(View.INVISIBLE);
            }
        });
    }
}