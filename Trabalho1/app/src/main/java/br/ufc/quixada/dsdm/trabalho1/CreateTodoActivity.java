package br.ufc.quixada.dsdm.trabalho1;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateTodoActivity extends AppCompatActivity {

    private static final String[] repeatOptions = new String[]{
            "Não repete", "Todos os dias", "Todas as semanas", "Todos os meses", "Todos os anos"
    };

    private static final String[] participants = new String[]{
            "Aang", "Zuko", "Katara", "Sokka", "Toph"
    };

    private AutoCompleteTextView autoCompleteParticipant;
    private Spinner spnRepeat;
    private RadioGroup priorityGroup;
    private TextView txtHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        // https://developer.android.com/reference/android/widget/AutoCompleteTextView
        ArrayAdapter participantsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, participants);
        autoCompleteParticipant = findViewById(R.id.autoCompleteParticipants);
        autoCompleteParticipant.setAdapter(participantsAdapter);

        // https://www.learn2crack.com/2013/12/android-spinner-dropdown-example.html
        ArrayAdapter repeatOptionAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, repeatOptions);
        spnRepeat = findViewById(R.id.spnRepeat);
        spnRepeat.setAdapter(repeatOptionAdapter);

        // https://stacktips.com/tutorials/android/android-radio-button-example
        priorityGroup = findViewById(R.id.radioGroupPriority);
        priorityGroup.clearCheck();
        priorityGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = group.findViewById(checkedId);
            if (rb != null && checkedId > -1)
                Toast.makeText(CreateTodoActivity.this, rb.getText(), Toast.LENGTH_LONG).show();
        });

        // https://stackoverflow.com/questions/21329132/android%E2%80%90custom%E2%80%90dropdown%E2%80%90popup%E2%80%90menu
        txtHide = findViewById(R.id.hideYet);
        txtHide.setOnClickListener(v -> {
            PopupMenu hideMenu = new PopupMenu(CreateTodoActivity.this, txtHide);
            hideMenu.getMenu().add("Data do vencimento");
            hideMenu.getMenu().add("No tempo devido");
            hideMenu.getMenu().add("Dias antes do vencimento");
            hideMenu.getMenu().add("Semanas antes do vencimento");

            hideMenu.setOnMenuItemClickListener(item -> {
                txtHide.setText(item.getTitle());
                return true;
            });
            hideMenu.show();
        });

    }
}