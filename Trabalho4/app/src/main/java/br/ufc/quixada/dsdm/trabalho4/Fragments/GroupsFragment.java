package br.ufc.quixada.dsdm.trabalho4.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.ufc.quixada.dsdm.trabalho4.Adapters.ChatAdapter;
import br.ufc.quixada.dsdm.trabalho4.AddGroupActivity;
import br.ufc.quixada.dsdm.trabalho4.Model.Group;
import br.ufc.quixada.dsdm.trabalho4.Model.Identification;
import br.ufc.quixada.dsdm.trabalho4.Model.User;
import br.ufc.quixada.dsdm.trabalho4.R;
import br.ufc.quixada.dsdm.trabalho4.utils.ChatTypes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    FirebaseFirestore mFirestore;
    ChatAdapter mChatAdapter;

    ProgressBar mPgGroupList;
    FloatingActionButton mFaAddGroup;
    RecyclerView mRecyclerView;

    List<Identification> groups = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        mFaAddGroup = view.findViewById(R.id.fa_add_group);
        mRecyclerView = view.findViewById(R.id.groups_recycler);
        mPgGroupList = view.findViewById(R.id.pg_group_list);
        mFirestore = FirebaseFirestore.getInstance();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFaAddGroup.setOnClickListener(v -> startActivity(new Intent(getContext(), AddGroupActivity.class)));

        loadGroups();
        return view;
    }

    public void loadGroups() {
        mFirestore.collection("groups").get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                task.getResult().forEach(snap -> {
                    groups.add(new Group(snap.getId(), snap.getString("name")));
                });
            mChatAdapter = new ChatAdapter(getContext(), groups, ChatTypes.GROUP_MESSAGE);
            mRecyclerView.setAdapter(mChatAdapter);
            mPgGroupList.setVisibility(View.INVISIBLE);
        });

    }
}