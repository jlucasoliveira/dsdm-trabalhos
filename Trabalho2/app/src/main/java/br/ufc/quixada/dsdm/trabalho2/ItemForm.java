package br.ufc.quixada.dsdm.trabalho2;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.ufc.quixada.dsdm.trabalho2.models.Carro;

public class ItemForm extends AppCompatActivity {

    private TextView txtIDField;
    private EditText editTxtCarModel;
    private EditText editTxtCarBrand;
    private EditText editTxtCarColor;
    private EditText editTxtPubYear;

    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

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
            else if (bundle.containsKey(ActionsEnum.NEW_ITEM_ID.name()))
                txtIDField.setText(bundle.get(ActionsEnum.NEW_ITEM_ID.name()).toString());
            else {
                setResult(ActionsEnum.ITEM_CANCEL.ordinal());
                finish();
            }
        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this::onClickSaveButton);

        btnCancel = findViewById(R.id.btcCancel);
        btnCancel.setOnClickListener(this::onClickCancelButton);

    }

    public void onClickSaveButton(View view) {
        Integer id = Integer.parseInt(txtIDField.getText().toString());
        String modelo = editTxtCarModel.getText().toString();
        String marca = editTxtCarBrand.getText().toString();
        String cor = editTxtCarColor.getText().toString();
        String pubYear = editTxtPubYear.getText().toString();
        Carro carro = new Carro(id, modelo, marca, cor, pubYear);

        Intent intent = new Intent();
        intent.putExtra(ActionsEnum.ITEM.name(), carro);
        setResult(ActionsEnum.ITEM_DONE.ordinal(), intent);
        finish();
    }

    public void onClickCancelButton(View view) {
        setResult(ActionsEnum.ITEM_CANCEL.ordinal());
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(ActionsEnum.ITEM_CANCEL.ordinal());
        finish();
    }
}