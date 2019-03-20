package com.resin;

import android.content.Context;

/**
 * 3 resins are manufactured viz:
 * 1: UF
 * 2: PF
 * 3: MR
 *
 * It keeps the stock of each resins.
 */
class StockInHand {
    private int uf;
    private int pf;
    private int mr;

    private final Context context;

    public StockInHand(Context context) {
        this.context = context;
    }

    public void setUF(int uf) {
        this.uf = uf;
    }

    public void setPF(int pf) {
        this.pf = pf;
    }

    public void setMR(int mr) {
        this.mr = mr;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");

        builder.append( this.context.getString( R.string.uf ) ).append( this.context.getString( R.string.equal ) ).append(this.uf)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");

        builder.append("PF=").append(this.pf)
                .append(" ").append( this.context.getString( R.string.kg) ).append("\n");

        builder.append("MR=").append(this.mr)
                .append(" ").append( this.context.getString( R.string.kg) );

        return builder.toString();
    }
}
