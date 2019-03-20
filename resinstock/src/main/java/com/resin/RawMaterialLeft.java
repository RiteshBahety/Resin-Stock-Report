package com.resin;

import android.content.Context;
import android.content.SharedPreferences;

class RawMaterialLeft {
    private int aceticAcid;
    private int caustic;
    private int formalin;
    private int liqourAmmonia;
    private int maida;
    private int melamine;
    private int phenol;
    private int stureangBond;

    private RawMaterialUsed rawMaterialUsed;

    private final Context context;
    private final SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    RawMaterialLeft(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("com.resinstock.rawMaterialLeft", Context.MODE_PRIVATE);
    }

    void enableEditing() {
        this.editor = this.preferences.edit();
    }

    void commitData() {
        this.editor.apply();
    }

    int getAceticAcid() {
        this.aceticAcid = this.preferences.getInt( this.context.getString( R.string.acetic_acid ), 0);
        return this.aceticAcid;
    }

    void setAceticAcid(int aceticAcid) {
        this.aceticAcid = aceticAcid;
        this.editor.putInt( this.context.getString( R.string.acetic_acid ), aceticAcid);
    }

    int getCaustic() {
        this.caustic = this.preferences.getInt(this.context.getString( R.string.caustic_solid), 0);
        return this.caustic;
    }

    void setCaustic(int caustic) {
        this.caustic = caustic;
        this.editor.putInt(this.context.getString( R.string.caustic_solid ), caustic);
    }

    int getFormalin() {
        this.formalin = this.preferences.getInt( this.context.getString( R.string.formalin ), 0);
        return this.formalin;
    }

    void setFormalin(int formalin) {
        this.formalin = formalin;
        this.editor.putInt(this.context.getString( R.string.formalin ), formalin);
    }

    int getLiqourAmmonia() {
        this.liqourAmmonia = this.preferences.getInt(this.context.getString( R.string.liquor_ammonia ), 0);
        return this.liqourAmmonia;
    }

    void setLiqourAmmonia(int liqourAmmonia) {
        this.liqourAmmonia = liqourAmmonia;
        this.editor.putInt(this.context.getString( R.string.liquor_ammonia ), liqourAmmonia);
    }

    int getMaida() {
        this.maida = this.preferences.getInt(this.context.getString( R.string.maida ), 0);
        return this.maida;
    }

    void setMaida(int maida) {
        this.maida = maida;
        this.editor.putInt(this.context.getString( R.string.maida ), maida);
    }

    int getMelamine() {
        this.melamine = this.preferences.getInt(this.context.getString( R.string.melamine ), 0);
        return this.melamine;
    }

    void setMelamine(int melamine) {
        this.melamine = melamine;
        this.editor.putInt(this.context.getString( R.string.melamine ), melamine);
    }

    int getPhenol() {
        this.phenol = this.preferences.getInt(this.context.getString( R.string.phenol ), 0);
        return this.phenol;
    }

    void setPhenol(int phenol) {
        this.phenol = phenol;
        this.editor.putInt(this.context.getString( R.string.phenol ), phenol);
    }

    public int getStureangBond() {
        this.stureangBond = this.preferences.getInt(this.context.getString( R.string.stureang_bond ), 0);
        return this.stureangBond;
    }

    public void setStureangBond(int stureangBond) {
        this.stureangBond = stureangBond;
        this.editor.putInt(this.context.getString( R.string.stureang_bond ), stureangBond);
    }

    public void setRawMaterialUsed(RawMaterialUsed rawMaterialUsed) {
        this.rawMaterialUsed = rawMaterialUsed;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        //Formalin
        {
            builder.append( this.context.getString( R.string.formalin ) );

            if(this.rawMaterialUsed != null)
                builder.append(" ").append( this.context.getString( R.string.hyphen ) )
                        .append(this.rawMaterialUsed.getFormalin());

            builder.append( this.context.getString( R.string.equal ) ).append(this.formalin)
                    .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        }

        builder.append(this.context.getString( R.string.stureang_bond ) )
                .append( this.context.getString( R.string.equal ) )
                .append(this.stureangBond)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");

        //Phenol
        {
            builder.append(this.context.getString( R.string.phenol ) );

            if(this.rawMaterialUsed != null)
                builder.append(" -").append(this.rawMaterialUsed.getPhenol());

            builder.append( this.context.getString( R.string.equal ) )
                    .append(this.phenol)
                    .append(" ").append( this.context.getString( R.string.drum) ).append("\n");
        }

        builder.append( this.context.getString( R.string.production_report ) );

        builder.append(this.context.getString( R.string.melamine ) )
                .append( this.context.getString( R.string.equal ) ).append(this.melamine)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.acetic_acid ) )
                .append( this.context.getString( R.string.equal ) ).append(this.aceticAcid)
                .append(" ").append( this.context.getString( R.string.kenny) ).append("\n");
        builder.append(this.context.getString( R.string.caustic_solid ) )
                .append(this.context.getString( R.string.equal )).append(this.caustic)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");
        builder.append(this.context.getString( R.string.liquor_ammonia ) )
                .append(this.context.getString( R.string.equal )).append(this.liqourAmmonia)
                .append(" ").append( this.context.getString( R.string.kenny) ).append("\n");

        //Maida
        {
            builder.append(this.context.getString( R.string.maida ) );

            if(this.rawMaterialUsed != null)
                builder.append(" ").append( this.context.getString( R.string.hyphen) ).append(this.rawMaterialUsed.getMaida());

            builder.append(this.context.getString( R.string.equal )).append(this.maida)
                    .append(" ").append( this.context.getString( R.string.bag ) ).append("\n");
        }

        return builder.toString();
    }
}
