package br.ufc.quixada.dsdm.trabalho3;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho3.models.Carro;
import br.ufc.quixada.dsdm.trabalho3.network.API;
import br.ufc.quixada.dsdm.trabalho3.network.CarroService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Carro> carros = new ArrayList<>();
    ArrayAdapter listViewAdapter;

    private ListView listView;
    private FloatingActionButton editBtn;
    private FloatingActionButton addBtn;
    private EditText editText;
    private ProgressBar progressBar;

    CarroService carroService = API.getCarroService();

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

        progressBar = findViewById(R.id.progressBar);

        loadCarros();
    }

    public void loadCarros() {
        Call<List<Carro>> callResponse = carroService.list();

        callResponse.enqueue(new Callback<List<Carro>>() {
            @Override
            public void onResponse(Call<List<Carro>> call, Response<List<Carro>> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE", response.body().toString());
                    carros.clear();
                    carros.addAll(response.body());
                    listViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Carro>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT);
            }
        });

    }

    public void onClickEditButton(View view) {
        String entrada = editText.getText().toString();
        if (entrada.equals("") || !isNumber(entrada)) {
            Toast.makeText(this, "Insira uma ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer id = Integer.parseInt(entrada);
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
        intent.setAction(ActionsEnum.ITEM_ADD.name());
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
                loadCarros();
            }
        }
        catch (NullPointerException e) {
          Toast.makeText(this, "Algo de errado não está certo", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean isNumber(String entrada) {
        if (entrada.length() == 0) return false;
        int i = 0;
        while ( i  < entrada.length()) {
            if (entrada.charAt(i) < '0' || entrada.charAt(i) > '9') return false;
            i++;
        }
        return true;
    }

}