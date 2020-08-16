package br.ufc.quixada.dsdm.trabalho1;

import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoneTodoFragment extends Fragment {

    private ListView todoList;
    private String[] todos = new String[]{
            "Ir ao supermercado", "Trabalhos de CG", "Trabalhos de Mobile", "Trabalhos de WEB"
    };

    public DoneTodoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // https://stackoverflow.com/questions/29440415/java-lang-nullpointerexception-attempt-to-invoke-virtual-method-android-view-v
        View rootView = inflater.inflate(R.layout.fragment_done_todo, container, false);

        // https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment

        // http://theopentutorials.com/tutorials/android/listview/android‐creating‐and‐populating‐listview‐items‐in‐xml
        todoList = rootView.findViewById(R.id.listTodo);
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // http://androidbite.blogspot.com.br/2013/03/android‐long‐press‐event‐handle‐example.html
        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        ((TextView) view).getText() + " " + String.valueOf(id), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // https://stackoverflow.com/questions/4533440/android-listview-text-color
        ArrayAdapter todosAdapter = new ArrayAdapter(getContext(),
                R.layout.todo_item, todos);
        todoList.setAdapter(todosAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }
}