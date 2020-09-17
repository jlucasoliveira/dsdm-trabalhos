package br.ufc.quixada.dsdm.trabalho3;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho3.models.Carro;
import br.ufc.quixada.dsdm.trabalho3.network.API;
import br.ufc.quixada.dsdm.trabalho3.network.CarroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemForm extends AppCompatActivity {

    private TextView txtIDField;
    private TextView txtIDLabel;
    private EditText editTxtCarModel;
    private EditText editTxtCarBrand;
    private EditText editTxtCarColor;
    private EditText editTxtPubYear;

    private Button btnSave;

    private CarroService carroService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        carroService = API.getCarroService();

        txtIDLabel = findViewById(R.id.lblId);
        txtIDField = findViewById(R.id.txtId);
        editTxtCarModel = findViewById(R.id.editTxtCarModel);
        editTxtCarBrand = findViewById(R.id.editTxtCarBrand);
        editTxtCarColor = findViewById(R.id.editTxtCarColor);
        editTxtPubYear = findViewById(R.id.editTxtPubYear);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Carro carro = (Carro) bundle.getSerializable(ActionsEnum.ITEM.name());
            if (carro != null) {
                txtIDField.setText(carro.getId().toString());
                editTxtCarModel.setText(carro.getModelo());
                editTxtCarBrand.setText(carro.getMarca());
                editTxtCarColor.setText(carro.getCor());
                editTxtPubYear.setText(carro.getAnoDeLancamento());
            }
            else if (getIntent().getAction().equals(ActionsEnum.ITEM_ADD.name())) {
                txtIDField.setVisibility(View.INVISIBLE);
                txtIDLabel.setVisibility(View.INVISIBLE);
            }
            else {
                setResult(ActionsEnum.ITEM_CANCEL.ordinal());
                finish();
            }
        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::onClickSaveButton);
    }

    public void onClickSaveButton(View view) {
        String _id = txtIDField.getText().toString();
        Integer id = _id.isEmpty()?null:Integer.parseInt(_id);
        String modelo = editTxtCarModel.getText().toString();
        String marca = editTxtCarBrand.getText().toString();
        String cor = editTxtCarColor.getText().toString();
        String pubYear = editTxtPubYear.getText().toString();
        Carro carro = new Carro(id, modelo, marca, cor, pubYear);

        if (id == null) addCarro(carro);
        else updateCarro(carro);

        Intent intent = new Intent();
        intent.putExtra(ActionsEnum.ITEM.name(), carro);
        setResult(ActionsEnum.ITEM_DONE.ordinal(), intent);
        finish();
    }

    public void addCarro(Carro carro) {
        Call<Carro> callResponse = carroService.register(carro);
        callResponse.enqueue(new Callback<Carro>() {
            @Override
            public void onResponse(Call<Carro> call, Response<Carro> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT);
                }
                else
                    Toast.makeText(getBaseContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Carro> call, Throwable t) {

            }
        });
    }

    public void updateCarro(Carro carro) {
        Call<Carro> callResponse = carroService.update(carro.getId(), carro);
        callResponse.enqueue(new Callback<Carro>() {
            @Override
            public void onResponse(Call<Carro> call, Response<Carro> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ItemForm.this, "Ocorreu um erro", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Carro> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(ActionsEnum.ITEM_CANCEL.ordinal());
        finish();
    }
}