package br.ufc.quixada.dsdm.trabalho2;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho2.models.Carro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private Integer objetoID = 0;

    public Integer getObjetoID() {
        return objetoID++;
    }

    private List<Carro> carros = new ArrayList<>();
    ArrayAdapter listViewAdapter;

    private ListView listView;
    private FloatingActionButton editBtn;
    private FloatingActionButton addBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listViewAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, carros);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener((p, v, po, id) ->
                Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT ).show());

        editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(this::onClickEditButton);

        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this::onCLinkAddButton);

        editText = findViewById(R.id.editId);
    }

    public void onClickEditButton(View view) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(this, "Insira uma ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer id = Integer.parseInt(editText.getText().toString());
        Carro editableCarro = null;

        for (Carro carro : carros)
            if (carro.getId().equals(id)) {
                editableCarro = carro;
                break;
            }


        if (editableCarro == null) {
            Toast.makeText(this, "Id inexistente!", Toast.LENGTH_LONG).show();
            return;
        }

        editText.setText("");
        Intent intent = new Intent(this, ItemForm.class);
        intent.putExtra(ActionsEnum.ITEM.name(), editableCarro);
        startActivityForResult(intent, ActionsEnum.ITEM_EDIT.ordinal());
    }

    public void onCLinkAddButton(View view) {
        Intent intent = new Intent(this, ItemForm.class);
        intent.putExtra(ActionsEnum.NEW_ITEM_ID.name(), getObjetoID());
        startActivityForResult(intent, ActionsEnum.ITEM_ADD.ordinal());
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (resultCode == ActionsEnum.ITEM_CANCEL.ordinal())
                Toast.makeText(this, "Ação cancelada!", Toast.LENGTH_LONG).show();
            else if (requestCode == ActionsEnum.ITEM_EDIT.ordinal()
                    && resultCode == ActionsEnum.ITEM_DONE.ordinal()) {

                Carro editedCar = (Carro) data.getSerializableExtra(ActionsEnum.ITEM.name());
                for (Carro carro : carros)
                    if (carro.getId().equals(editedCar.getId())){
                        carro.setModelo(editedCar.getModelo());
                        carro.setMarca(editedCar.getMarca());
                        carro.setAnoDeLancamento(editedCar.getAnoDeLancamento());
                        carro.setCor(editedCar.getCor());
                        break;
                    }
                listViewAdapter.notifyDataSetChanged();
            } else if (requestCode == ActionsEnum.ITEM_ADD.ordinal()
                    && resultCode == ActionsEnum.ITEM_DONE.ordinal()) {
                Carro newCar = (Carro) data.getSerializableExtra(ActionsEnum.ITEM.name());
                carros.add(newCar);
                listViewAdapter.notifyDataSetChanged();
            }
        }
        catch (NullPointerException e) {
          Toast.makeText(this, "Algo de errado não está certo", Toast.LENGTH_SHORT).show();
        }

    }

}