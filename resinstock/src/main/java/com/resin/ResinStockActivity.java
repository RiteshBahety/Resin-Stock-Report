package com.resin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.resin.sms.MessageSender;

import java.util.logging.Logger;

public class ResinStockActivity extends Activity {

    private final MessageSender sender = new MessageSender();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resin_stock);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Raw Material in Stock
        RawMaterialLeft rawMaterialLeft = new RawMaterialLeft(this);

        EditText aceticAcid = findViewById(R.id.acetic_acid_quantity);
        aceticAcid.setText( getString(R.string.blank, rawMaterialLeft.getAceticAcid() ) );

        EditText caustic = findViewById(R.id.caustic_solid_quantity);
        caustic.setText( getString(R.string.blank, rawMaterialLeft.getCaustic() ) );

        EditText formaldehyde = findViewById(R.id.formaldehyde_quantity);
        formaldehyde.setText( getString(R.string.blank, rawMaterialLeft.getFormalin() ) );

        EditText liquorAmmonia = findViewById(R.id.liquor_ammonia_quantity);
        liquorAmmonia.setText( getString(R.string.blank, rawMaterialLeft.getLiqourAmmonia() ) );

        EditText maida = findViewById(R.id.maida_quantity);
        maida.setText( getString(R.string.blank, rawMaterialLeft.getMaida() ) );

        EditText melamine = findViewById(R.id.melamine_quantity);
        melamine.setText( getString(R.string.blank, rawMaterialLeft.getMelamine() ) );

        EditText phenol = findViewById(R.id.phenol_quantity);
        phenol.setText( getString(R.string.blank, rawMaterialLeft.getPhenol() ) );

        EditText stureangBond = findViewById(R.id.stureang_bond_quantity);
        stureangBond.setText( getString(R.string.blank, rawMaterialLeft.getStureangBond() ) );

        //Raw Material Used
        RawMaterialUsed rawMaterialUsed = new RawMaterialUsed(this);

        EditText formaldehydeUsed = findViewById(R.id.formalin_used_quantity);
        formaldehydeUsed.setText( getString(R.string.blank, rawMaterialUsed.getFormalin() ) );

        EditText maidaUsed = findViewById(R.id.maida_used_quantity);
        maidaUsed.setText( getString(R.string.blank, rawMaterialUsed.getMaida()) );

        EditText phenolUsed = findViewById(R.id.phenol_used_quantity);
        phenolUsed.setText( getString(R.string.blank, rawMaterialUsed.getPhenol()) );

        //Stock Left
        Spinner ufSpinner = findViewById(R.id.uf_charge);
        ufSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(ufSpinner));

        Spinner pfSpinner = findViewById(R.id.pf_charge);
        pfSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(pfSpinner));

        Spinner mrSpinner = findViewById(R.id.mr_charge);
        mrSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(mrSpinner));
    }

    public void onSubmit(View view) {
        //Raw Materials Used
        RawMaterialUsed rawMaterialUsed = new RawMaterialUsed(this);

        rawMaterialUsed.enableEditing();
        EditText formalinUsed = findViewById(R.id.formalin_used_quantity);
        rawMaterialUsed.setFormalin( extractIntValue( formalinUsed.getText().toString() ) );

        EditText maidaUsed = findViewById(R.id.maida_used_quantity);
        rawMaterialUsed.setMaida( extractIntValue( maidaUsed.getText().toString() ) );

        EditText phenolUsed = findViewById(R.id.phenol_used_quantity);
        rawMaterialUsed.setPhenol( extractIntValue( phenolUsed.getText().toString() ) );

        rawMaterialUsed.commitData();

        //Raw Materials Left
        RawMaterialLeft rawMaterialLeft = new RawMaterialLeft(this);

        rawMaterialLeft.enableEditing();
        EditText aceticAcid = findViewById(R.id.acetic_acid_quantity);
        rawMaterialLeft.setAceticAcid( extractIntValue( aceticAcid.getText().toString() ) );

        EditText caustic = findViewById(R.id.caustic_solid_quantity);
        rawMaterialLeft.setCaustic( extractIntValue( caustic.getText().toString() ) );

        EditText formaldehyde = findViewById(R.id.formaldehyde_quantity);
        rawMaterialLeft.setFormalin( extractIntValue( formaldehyde.getText().toString() ) );

        EditText liquorAmmonia = findViewById(R.id.liquor_ammonia_quantity);
        rawMaterialLeft.setLiqourAmmonia( extractIntValue( liquorAmmonia.getText().toString() ) );

        EditText maida = findViewById(R.id.maida_quantity);
        rawMaterialLeft.setMaida( extractIntValue( maida.getText().toString() ) );

        EditText melamine = findViewById(R.id.melamine_quantity);
        rawMaterialLeft.setMelamine( extractIntValue( melamine.getText().toString() ) );

        EditText phenol = findViewById(R.id.phenol_quantity);
        rawMaterialLeft.setPhenol( extractIntValue( phenol.getText().toString() ) );

        EditText stureangBond = findViewById(R.id.stureang_bond_quantity);
        rawMaterialLeft.setStureangBond( extractIntValue( stureangBond.getText().toString() ) );

        rawMaterialLeft.commitData();
        rawMaterialLeft.setRawMaterialUsed(rawMaterialUsed);

        //Production
        Production production = new Production(this);
        Spinner ufChargeMade = findViewById(R.id.uf_charge);
        EditText ufChargeQuantity = findViewById(R.id.uf_charge_value);
        production.setUF( Byte.valueOf( ufChargeMade.getSelectedItem().toString() ), extractIntValue( ufChargeQuantity.getText().toString()));

        Spinner pfChargeMade = findViewById(R.id.pf_charge);
        EditText pfChargeQuantity = findViewById(R.id.pf_charge_value);
        production.setPF( Byte.valueOf( pfChargeMade.getSelectedItem().toString() ), extractIntValue( pfChargeQuantity.getText().toString()));

        Spinner mrChargeMade = findViewById(R.id.mr_charge);
        EditText mrChargeQuantity = findViewById(R.id.mr_charge_value);
        production.setMR( Byte.valueOf( mrChargeMade.getSelectedItem().toString() ), extractIntValue( mrChargeQuantity.getText().toString()));

        //Stock In Hand
        StockInHand stockInHand = new StockInHand(this);
        EditText ufStock = findViewById(R.id.uf_stock_value);
        stockInHand.setUF( extractIntValue( ufStock.getText().toString() ) );

        EditText pfStock = findViewById(R.id.pf_stock_value);
        stockInHand.setPF( extractIntValue( pfStock.getText().toString() ) );

        EditText mrStock = findViewById(R.id.mr_stock_value);
        stockInHand.setMR( extractIntValue( mrStock.getText().toString() ) );

        sendSMS(rawMaterialLeft.toString().replace(this.getString( R.string.production_report ), production.toString())
                + stockInHand.toString());
    }

    private void sendSMS(String body) {
        Logger.getGlobal().warning(body);

        sender.sendSMS(body, this);

        finish();
    }

    private int extractIntValue(String value) {
        if( value.equals("") )
            value = "0";

        return Integer.valueOf( value );
    }

    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        private final Spinner spinner;
        private int currentSelection;

        CustomOnItemSelectedListener(Spinner s){
            spinner = s;
            currentSelection = spinner.getSelectedItemPosition();
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (currentSelection != position) {
                spinner.setFocusableInTouchMode(true);
                spinner.requestFocus();
            }

            currentSelection = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //do nothing
        }

    }
}

