package com.resin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.logging.Logger;

public class RawMaterialStock {
    private DailyStock aceticAcid;
    private DailyStock caustic;
    private DailyStock formalin;
    private DailyStock liqourAmmonia;
    private DailyStock maida;
    private DailyStock melamine;
    private DailyStock phenol;
    private DailyStock stureangBond;

    private final Context context;
    private final SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private DailyStock dailyStock = new DailyStock();

    private DailyStock[] stockUpdate = new DailyStock[8];

    public enum ERawMaterial {
        ACETIC_ACID ("Acetic Acid"),
        CAUSTIC ("Caustic (Solid)"),
        FORMALIN ("Formalin"),
        LIQUOR_AMMONIA ("Liquor Ammonia"),
        MAIDA ("Maida"),
        MELAMINE ("Melamine"),
        PHENOL ("Phenol"),
        STUREANG_BOND ("Stureang Bond");

        private String name;

        ERawMaterial(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    {
        for(int index = 0; index < stockUpdate.length; index++)
            stockUpdate[ index ] = new DailyStock();
    }

    RawMaterialStock(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("com.resinstock.rawMaterialStock", Context.MODE_PRIVATE);
    }

    void enableEditing() {
        this.editor = this.preferences.edit();
    }

    void commitData() {
        this.editor.apply();
    }

    public void setStockUpdate(DailyStock dailyStock, ERawMaterial eRawMaterial) {
        Logger.getGlobal().info(eRawMaterial.name() + " : " + dailyStock.toString());

        this.stockUpdate[eRawMaterial.ordinal()] = dailyStock;
    }

    private DailyStock parseString(String raw) {
        String[] splitData = raw.split(",");

        dailyStock.setStartStock( Integer.parseInt( splitData[0].trim() ) );
        dailyStock.setReceived( Integer.parseInt( splitData[0].trim() ) );
        dailyStock.setConsumed( Integer.parseInt( splitData[0].trim() ) );

        return dailyStock;
    }

    DailyStock getAceticAcid() {
        this.aceticAcid = parseString( this.preferences.getString( this.context.getString( R.string.acetic_acid ), "0,0,0") );
        return this.aceticAcid;
    }

    public void updateAllRawMaterials() {
        ERawMaterial[] rawMaterials = ERawMaterial.values();

        for(ERawMaterial rawMaterial : rawMaterials) {
            switch(rawMaterial) {
                case ACETIC_ACID : setAceticAcid( this.stockUpdate[ERawMaterial.ACETIC_ACID.ordinal()] );
                                    break;

                case CAUSTIC : setCaustic( this.stockUpdate[ERawMaterial.CAUSTIC.ordinal()] );
                                break;

                case FORMALIN : setFormalin( this.stockUpdate[ERawMaterial.FORMALIN.ordinal()] );
                                break;

                case LIQUOR_AMMONIA : setLiqourAmmonia( this.stockUpdate[ERawMaterial.LIQUOR_AMMONIA.ordinal()] );
                                    break;

                case MAIDA : setMaida( this.stockUpdate[ERawMaterial.MAIDA.ordinal()] );
                            break;

                case MELAMINE : setMelamine( this.stockUpdate[ERawMaterial.MELAMINE.ordinal()] );
                                break;

                case PHENOL: setPhenol( this.stockUpdate[ERawMaterial.PHENOL.ordinal()] );
                                break;

                case STUREANG_BOND: setStureangBond( this.stockUpdate[ERawMaterial.STUREANG_BOND.ordinal()] );
                                    break;
            }
        }

        Logger.getGlobal().info( toString() );
    }

    void setAceticAcid(DailyStock dailyStock) {
        this.aceticAcid = dailyStock;
        this.editor.putString( this.context.getString( R.string.acetic_acid ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getCaustic() {
        this.caustic = parseString( this.preferences.getString(this.context.getString( R.string.caustic_solid), "0,0,0") );
        return this.caustic;
    }

    void setCaustic(DailyStock dailyStock) {
        this.caustic = dailyStock;
        this.editor.putString(this.context.getString( R.string.caustic_solid ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getFormalin() {
        this.formalin = parseString( this.preferences.getString( this.context.getString( R.string.formalin ), "0,0,0") );
        return this.formalin;
    }

    void setFormalin(DailyStock dailyStock) {
        this.formalin = dailyStock;
        this.editor.putString(this.context.getString( R.string.formalin ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getLiqourAmmonia() {
        this.liqourAmmonia = parseString( this.preferences.getString(this.context.getString( R.string.liquor_ammonia ), "0,0,0") );
        return this.liqourAmmonia;
    }

    void setLiqourAmmonia(DailyStock dailyStock) {
        this.liqourAmmonia = dailyStock;
        this.editor.putString(this.context.getString( R.string.liquor_ammonia ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getMaida() {
        this.maida = parseString( this.preferences.getString(this.context.getString( R.string.maida ), "0,0,0") );
        return this.maida;
    }

    void setMaida(DailyStock dailyStock) {
        this.maida = dailyStock;
        this.editor.putString(this.context.getString( R.string.maida ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getMelamine() {
        this.melamine = parseString( this.preferences.getString(this.context.getString( R.string.melamine ), "0,0,0") );
        return this.melamine;
    }

    void setMelamine(DailyStock dailyStock) {
        this.melamine = dailyStock;
        this.editor.putString(this.context.getString( R.string.melamine ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    DailyStock getPhenol() {
        this.phenol = parseString( this.preferences.getString(this.context.getString( R.string.phenol ), "0,0,0") );
        return this.phenol;
    }

    void setPhenol(DailyStock dailyStock) {
        this.phenol = dailyStock;
        this.editor.putString(this.context.getString( R.string.phenol ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    public DailyStock getStureangBond() {
        this.stureangBond = parseString( this.preferences.getString(this.context.getString( R.string.stureang_bond ), "0,0,0") );
        return this.stureangBond;
    }

    public void setStureangBond(DailyStock dailyStock) {
        this.stureangBond = dailyStock;
        this.editor.putString(this.context.getString( R.string.stureang_bond ), dailyStock.getStartStock() + "," + dailyStock.getReceived() + "," + dailyStock.getConsumed());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append( this.context.getString( R.string.production_report ) );

        builder.append(this.context.getString( R.string.acetic_acid ) )
                .append( this.context.getString( R.string.equal ) ).append(this.aceticAcid)
                .append(" ").append( this.context.getString( R.string.kenny) ).append("\n");
        builder.append(this.context.getString( R.string.caustic_solid ) )
                .append(this.context.getString( R.string.equal )).append(this.caustic)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.formalin ) )
                .append(this.context.getString( R.string.equal )).append(this.formalin)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.liquor_ammonia ) )
                .append(this.context.getString( R.string.equal )).append(this.liqourAmmonia)
                .append(" ").append( this.context.getString( R.string.kenny) ).append("\n");
        builder.append(this.context.getString( R.string.maida ) )
                .append(this.context.getString( R.string.equal )).append(this.maida)
                .append(" ").append( this.context.getString( R.string.kenny) ).append("\n");
        builder.append(this.context.getString( R.string.melamine ) )
                .append( this.context.getString( R.string.equal ) ).append(this.melamine)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.phenol ) )
                .append( this.context.getString( R.string.equal ) ).append(this.phenol)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.stureang_bond ) )
                .append( this.context.getString( R.string.equal ) ).append(this.stureangBond)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");

        return builder.toString();
    }
}
