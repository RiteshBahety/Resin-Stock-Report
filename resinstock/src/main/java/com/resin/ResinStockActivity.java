package com.resin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.resin.RawMaterialStock.ERawMaterial;

import com.resin.sms.MessageSender;

import java.util.logging.Logger;

public class ResinStockActivity extends Activity {

    private final MessageSender sender = new MessageSender();
    private RawMaterialStock rawMaterialStock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resin_stock);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Raw Material in Stock
        rawMaterialStock = new RawMaterialStock(this);

        TextView aceticAcid = findViewById(R.id.acetic_acid_quantity);
        aceticAcid.setText( getString(R.string.blank, rawMaterialStock.getAceticAcid().getFinalStock() ) );

        TextView caustic = findViewById(R.id.caustic_solid_quantity);
        caustic.setText( getString(R.string.blank, rawMaterialStock.getCaustic().getFinalStock() ) );

        TextView formaldehyde = findViewById(R.id.formaldehyde_quantity);
        formaldehyde.setText( getString(R.string.blank, rawMaterialStock.getFormalin().getFinalStock() ) );

        TextView liquorAmmonia = findViewById(R.id.liquor_ammonia_quantity);
        liquorAmmonia.setText( getString(R.string.blank, rawMaterialStock.getLiqourAmmonia().getFinalStock() ) );

        TextView maida = findViewById(R.id.maida_quantity);
        maida.setText( getString(R.string.blank, rawMaterialStock.getMaida().getFinalStock() ) );

        TextView melamine = findViewById(R.id.melamine_quantity);
        melamine.setText( getString(R.string.blank, rawMaterialStock.getMelamine().getFinalStock() ) );

        TextView phenol = findViewById(R.id.phenol_quantity);
        phenol.setText( getString(R.string.blank, rawMaterialStock.getPhenol().getFinalStock() ) );

        TextView stureangBond = findViewById(R.id.stureang_bond_quantity);
        stureangBond.setText( getString(R.string.blank, rawMaterialStock.getStureangBond().getFinalStock() ) );

        //Stock Left
        Spinner ufSpinner = findViewById(R.id.uf_charge);
        ufSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(ufSpinner));

        Spinner pfSpinner = findViewById(R.id.pf_charge);
        pfSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(pfSpinner));

        Spinner mrSpinner = findViewById(R.id.mr_charge);
        mrSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(mrSpinner));
    }

    public void onSubmit(View view) {
        //Raw Materials
        rawMaterialStock.enableEditing();
        rawMaterialStock.updateAllRawMaterials();
        rawMaterialStock.commitData();

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

        sendSMS(rawMaterialStock.toString().replace(this.getString( R.string.production_report ), production.toString())
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

    public void launchDialog(View view) {
        Logger.getGlobal().info("launchDialog : " + view.getId());

        StockEntry stockEntry = null;

        switch( view.getId() ) {
            case R.id.acetic_acid_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getAceticAcid(), ERawMaterial.ACETIC_ACID );
                break;

            case R.id.caustic_solid_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getCaustic(), ERawMaterial.CAUSTIC );
                break;

            case R.id.formaldehyde_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getFormalin(), ERawMaterial.FORMALIN );
                break;

            case R.id.liquor_ammonia_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getLiqourAmmonia(), ERawMaterial.LIQUOR_AMMONIA );
                break;

            case R.id.maida_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getMaida(), ERawMaterial.MAIDA );
                break;

            case R.id.melamine_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getMelamine(), ERawMaterial.MELAMINE );
                break;

            case R.id.phenol_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getPhenol(), ERawMaterial.PHENOL );
                break;

            case R.id.stureang_bond_quantity :
                stockEntry = new StockEntry( this.rawMaterialStock.getStureangBond(), ERawMaterial.STUREANG_BOND );
                break;
        }

        if(stockEntry != null)
            stockEntry.show(getFragmentManager(), "dialog");
    }

    private class StockEntry extends DialogFragment {
        private DailyStock dailyStock;
        private ERawMaterial eRawMaterial;

        StockEntry(DailyStock dailyStock, ERawMaterial rawMaterial) {
            this.dailyStock = dailyStock;
            this.eRawMaterial = rawMaterial;
        }

        public void hello(final View dialogView) {
            EditText startStock = dialogView.findViewById(R.id.openingStock);
            startStock.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Logger.getGlobal().info("Entered number : " + s);
                    TextView finalStock = dialogView.findViewById(R.id.finalStock);
                    String val = String.valueOf( s );
                    if(val.equals(""))
                        val = "0";

                    dailyStock.setStartStock(Integer.parseInt(val));
                    finalStock.setText(String.valueOf(dailyStock.getFinalStock()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
            startStock.setText( String.valueOf( dailyStock.getStartStock() ) );

            EditText received = dialogView.findViewById(R.id.received);
            received.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Logger.getGlobal().info("Entered number : " + s);
                    TextView finalStock = dialogView.findViewById(R.id.finalStock);

                    String val = String.valueOf( s );
                    if(val.equals(""))
                        val = "0";

                    dailyStock.setReceived(Integer.parseInt(val));
                    finalStock.setText(String.valueOf(dailyStock.getFinalStock()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
            received.setText( String.valueOf( dailyStock.getReceived() ) );

            EditText consumed = dialogView.findViewById(R.id.consumed);
            consumed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //do nothing
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Logger.getGlobal().info("Entered number : " + s);
                    TextView finalStock = dialogView.findViewById(R.id.finalStock);

                    String val = String.valueOf( s );
                    if(val.equals(""))
                        val = "0";

                    dailyStock.setReceived(Integer.parseInt(val));
                    finalStock.setText(String.valueOf(dailyStock.getFinalStock()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
            consumed.setText( String.valueOf( dailyStock.getConsumed() ) );
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

            LayoutInflater inflater = LayoutInflater.from( getApplicationContext() );
            final View dialogView = inflater.inflate(R.layout.dialog_stock_entry, null);

            builder .setView( dialogView )
                    .setTitle(eRawMaterial.getName())
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText startStock = dialogView.findViewById(R.id.openingStock);
                            int startStockValue = Integer.parseInt( startStock.getText().toString().trim() );

                            EditText received = dialogView.findViewById(R.id.received);
                            int receivedValue = Integer.parseInt( received.getText().toString().trim() );

                            EditText consumed = dialogView.findViewById(R.id.consumed);
                            int consumedValue = Integer.parseInt( consumed.getText().toString().trim() );

                            dailyStock.setStartStock( startStockValue );
                            dailyStock.setReceived( receivedValue );
                            dailyStock.setConsumed( consumedValue );

                            rawMaterialStock.setStockUpdate(dailyStock, eRawMaterial);

                            updateRawMaterialEntry(eRawMaterial, dailyStock.getFinalStock());
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO: Cancel the changes
                        }
                    });

            AlertDialog alertDialog = builder.create();
            hello(dialogView);
            return alertDialog;
        }
    }

    private void updateRawMaterialEntry(ERawMaterial eRawMaterial, int value) {
        TextView rawMaterialTextView = null;

        switch (eRawMaterial) {
            case ACETIC_ACID: rawMaterialTextView = findViewById(R.id.acetic_acid_quantity);
                                break;

            case CAUSTIC: rawMaterialTextView = findViewById(R.id.caustic_solid_quantity);
                           break;

            case FORMALIN: rawMaterialTextView = findViewById(R.id.formaldehyde_quantity);
                            break;

            case LIQUOR_AMMONIA: rawMaterialTextView = findViewById(R.id.liquor_ammonia_quantity);
                                break;

            case MAIDA: rawMaterialTextView = findViewById(R.id.maida_quantity);
                        break;

            case MELAMINE: rawMaterialTextView = findViewById(R.id.melamine_quantity);
                            break;

            case PHENOL: rawMaterialTextView = findViewById(R.id.phenol_quantity);
                        break;

            case STUREANG_BOND: rawMaterialTextView = findViewById(R.id.stureang_bond_quantity);
                                break;

        }

        if(rawMaterialTextView != null)
            rawMaterialTextView.setText( getString(R.string.blank, value));
    }

    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

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
