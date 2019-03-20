package com.resin;

import android.content.Context;

/**
 * 3 resins are manufactured viz:
 * 1: UF
 * 2: PF
 * 3: MR
 *
 * A resin kettle makes at-most 3 charges in a single day. On each charge, different amount of
 * resin can be manufactured using different quantity of raw materials.
 *
 * Values for *Number of charges* is stored in string-array inside strings.xml resource file. It
 * populate the spinner.
 */
class Production {
    private QuantityManufactured uf;
    private QuantityManufactured pf;
    private QuantityManufactured mr;

    private final Context context;

    public Production(Context context) {
        this.context = context;
    }

    public void setUF(byte numberOfCharge, int quantity) {
        this.uf = new QuantityManufactured(numberOfCharge, quantity);
    }

    public void setPF(byte numberOfCharge, int quantity) {
        this.pf = new QuantityManufactured(numberOfCharge, quantity);
    }

    public void setMR(byte numberOfCharge, int quantity) {
        this.mr = new QuantityManufactured(numberOfCharge, quantity);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("\n");

        boolean noProduction = true;

        if( this.uf.getNumberOfCharge() > 0) {
            builder.append( this.context.getString( R.string.uf ) ).append( this.context.getString( R.string.equal ) ).append(this.uf.getNumberOfCharge())
                    .append( this.context.getString( R.string.colon ) ).append(this.uf.getQuantity()).append("\n");
            noProduction = false;
        }

        if( this.pf.getNumberOfCharge() > 0) {
            builder.append( this.context.getString( R.string.pf ) ).append( this.context.getString( R.string.equal ) ).append(this.pf.getNumberOfCharge())
                    .append( this.context.getString( R.string.colon ) ).append(this.pf.getQuantity()).append("\n");
            noProduction = false;
        }

        if( this.mr.getNumberOfCharge() > 0) {
            builder.append( this.context.getString( R.string.mr ) ).append( this.context.getString( R.string.equal ) ).append(this.mr.getNumberOfCharge())
                    .append( this.context.getString( R.string.colon ) ).append(this.mr.getQuantity()).append("\n");
            noProduction = false;
        }

        if( noProduction )
            builder.append( this.context.getString( R.string.no_charge_made ) ).append("\n");

        builder.append("\n");

        return builder.toString();
    }

    private class QuantityManufactured {
        private byte numberOfCharge;
        private int quantity;

        QuantityManufactured(byte numberOfCharge, int quantity) {
            this.numberOfCharge = numberOfCharge;
            this.quantity = (numberOfCharge == 0) ? 0 : quantity;
        }

        byte getNumberOfCharge() {
            return this.numberOfCharge;
        }

        int getQuantity() {
            return this.quantity;
        }
    }
}
