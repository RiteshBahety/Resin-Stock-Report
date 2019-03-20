package com.resin;

import android.content.Context;
import android.content.SharedPreferences;

class RawMaterialUsed {
    private int formalin;
    private int maida;
    private int phenol;

    private final Context context;

    private final SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    RawMaterialUsed(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("com.resinstock.rawMaterialUsed", Context.MODE_PRIVATE);
    }

    void enableEditing() {
        this.editor = this.preferences.edit();
    }

    void commitData() {
        this.editor.apply();
    }

    int getFormalin() {
        this.formalin = this.preferences.getInt( this.context.getString( R.string.formalin ), 0);
        return this.formalin;
    }

    void setFormalin(int formalin) {
        this.formalin = formalin;
        this.editor.putInt( this.context.getString( R.string.formalin ), formalin);
    }

    int getMaida() {
        this.maida = this.preferences.getInt(this.context.getString( R.string.maida ), 0);
        return this.maida;
    }

    void setMaida(int maida) {
        this.maida = maida;
        this.editor.putInt(this.context.getString( R.string.maida ), maida);
    }

    int getPhenol() {
        this.phenol = this.preferences.getInt( this.context.getString( R.string.phenol ), 0);
        return this.phenol;
    }

    void setPhenol(int phenol) {
        this.phenol = phenol;
        this.editor.putInt( this.context.getString( R.string.phenol ), phenol);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if(this.formalin > 0)
            builder.append( this.context.getString( R.string.formalin ) ).append( this.context.getString( R.string.equal ) ).append(this.formalin)
                    .append(" ").append( this.context.getString( R.string.kg) ).append("\n");

        if(this.maida > 0)
            builder.append( this.context.getString( R.string.maida ) ).append( this.context.getString( R.string.equal ) ).append(this.maida)
                    .append(" ").append( this.context.getString( R.string.bag ) ).append("\n");

        if(this.phenol > 0)
            builder.append( this.context.getString( R.string.phenol ) ).append( this.context.getString( R.string.equal ) ).append(this.phenol)
                    .append(" ").append( this.context.getString( R.string.drum) ).append("\n");

        return builder.toString();
    }
}
