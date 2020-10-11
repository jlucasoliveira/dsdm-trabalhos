package br.ufc.quixada.dsdm.trabalho4;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.trabalho4.Adapters.MessageAdapter;
import br.ufc.quixada.dsdm.trabalho4.Auth.LoginActivity;
import br.ufc.quixada.dsdm.trabalho4.Model.Message;
import br.ufc.quixada.dsdm.trabalho4.utils.ChatTypes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    FirebaseUser mUser;
    FirebaseFirestore mFirestore;

    ProgressBar mPbChat;
    EditText mEditMessage;
    FloatingActionButton mFaSendMessage;
    RecyclerView mRecyclerView;

    MessageAdapter mMessageAdapter;
    List<Message> messages = new ArrayList<>();
    Map<String, String> mMembers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final String name = getIntent().getExtras().getString("name");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(name);

        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mPbChat = findViewById(R.id.pb_chat);
        mFaSendMessage = findViewById(R.id.btn_send);
        mRecyclerView = findViewById(R.id.message_list);
        mEditMessage = findViewById(R.id.input_message);
        mMessageAdapter = new MessageAdapter(messages, mMembers);

        LinearLayoutManager lnm = new LinearLayoutManager(this);
        lnm.setStackFromEnd(true);

        mFaSendMessage.setOnClickListener(this::sendMessage);
        mRecyclerView.setLayoutManager(lnm);
        mRecyclerView.setHasFixedSize(true);

        loadMessages();

    }

    public void sendMessage(View view) {
        final int CHAT_TYPE = getIntent().getExtras().getInt("CHAT_TYPE");
        final String receiver_id = getIntent().getExtras().getString("uid");

        String message = mEditMessage.getText().toString();
        if (message.isEmpty()) return;

        Map<String, Object> data = new HashMap<>(3);
        data.put("sender", mUser.getUid());
        data.put("message", message);
        data.put("instant", Timestamp.now());
        data.put((CHAT_TYPE == ChatTypes.USER_MESSAGE)?"receiver":"group", receiver_id);

        mFirestore.collection("chat").add(data);

        mEditMessage.setText("");
    }

    private void loadMessages() {
        final int CHAT_TYPE = getIntent().getExtras().getInt("CHAT_TYPE");
        final String receiver_id = getIntent().getExtras().getString("uid");

        if (CHAT_TYPE == ChatTypes.GROUP_MESSAGE) {
            mMembers = new HashMap<>();
            mFirestore.collection("group-user").whereEqualTo("group", receiver_id)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot s : task.getResult()) {
                        mFirestore.collection("users").document(s.getString("user")).get()
                        .addOnCompleteListener(innerTask -> {
                            mMembers.put(innerTask.getResult().getId(), innerTask.getResult().getString("name"));
                        });
                    }
                }
                mFirestore.collection("chat").orderBy("instant").addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    messages.clear();
                    for (QueryDocumentSnapshot q : value) {
                        Message m = q.toObject(Message.class);
                        if (m.getGroup() != null && m.getGroup().equals(receiver_id)) {
                            messages.add(m);
                        }
                    }
                    mMessageAdapter.setMessages(messages);
                    mMessageAdapter.setUsers(mMembers);
                    mRecyclerView.setAdapter(mMessageAdapter);
                    mPbChat.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, mMembers.toString(), Toast.LENGTH_SHORT).show();
                });
            });
        }
        else {
            mFirestore.collection("chat").orderBy("instant").addSnapshotListener((value, error) -> {
                if (error != null) {
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                    return;
                }
                messages.clear();
                for (QueryDocumentSnapshot q : value) {
                    Message m = q.toObject(Message.class);
                    if (m.getReceiver() != null &&
                            ((m.getSender().equals(mUser.getUid()) && m.getReceiver().equals(receiver_id)) ||
                            m.getSender().equals(receiver_id) && m.getReceiver().equals(mUser.getUid()))
                    )
                        messages.add(m);
                }
                mMessageAdapter.setMessages(messages);
                mRecyclerView.setAdapter(mMessageAdapter);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final int CHAT_TYPE = getIntent().getExtras().getInt("CHAT_TYPE");
        if (CHAT_TYPE == ChatTypes.GROUP_MESSAGE)
            getMenuInflater().inflate(R.menu.group_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_member) {
            Toast.makeText(this, "É rapaz!!!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}