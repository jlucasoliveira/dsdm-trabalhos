package br.ufc.quixada.dsdm.trabalho1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OngoingTodoFragment extends Fragment {

    private GridView gridView;
    private static final String[] todosDone = new String[]{
            "Comprar memoria", "Teste",
    };

    public OngoingTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongoing_todo, container, false);

        gridView = rootView.findViewById(R.id.gridview);
        ArrayAdapter gridAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, todosDone);
        gridView.setAdapter(gridAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }


}