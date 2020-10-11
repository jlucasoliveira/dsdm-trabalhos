package br.ufc.quixada.dsdm.trabalho4.Fragments;

import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.trabalho4.Adapters.ChatAdapter;
import br.ufc.quixada.dsdm.trabalho4.Model.Identification;
import br.ufc.quixada.dsdm.trabalho4.Model.User;
import br.ufc.quixada.dsdm.trabalho4.R;
import br.ufc.quixada.dsdm.trabalho4.utils.ChatTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    ProgressBar mPbUserList;
    RecyclerView mRecyclerView;
    ChatAdapter chatAdapter;
    List<Identification> users = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, true);

        mPbUserList = view.findViewById(R.id.pb_user_list);
        mRecyclerView = view.findViewById(R.id.users_recycler);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        loadUsers();

        return view;
    }

    public void loadUsers() {
        mFirestore.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                task.getResult().forEach(snap -> {
                    if (!user.getUid().equals(snap.getId()))
                        users.add(
                            new User(snap.getId(), snap.getString("name"), snap.getString("email")));
                });
                chatAdapter = new ChatAdapter(getContext(), users, ChatTypes.USER_MESSAGE);
                mRecyclerView.setAdapter(chatAdapter);
                mPbUserList.setVisibility(View.INVISIBLE);
            }
        });
    }
}