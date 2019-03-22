package com.resin;

class DailyStock {
    private int startStock;
    private int received;
    private int consumed;
    private int finalStock;

    public int getStartStock() {
        return startStock;
    }

    public void setStartStock(int startStock) {
        this.startStock = startStock;

        updateFinalStock();
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;

        updateFinalStock();
    }

    public int getConsumed() {
        return consumed;
    }

    public void setConsumed(int consumed) {
        this.consumed = consumed;

        updateFinalStock();
    }

    public int getFinalStock() {
        return finalStock;
    }

    /**
     * TODO: To work on negative value of Final Stock
     * 1: If value entered makes Final Stock negative, then do not save the value
     * 2: Show negative value in Final Stock
     * 3: On pressing OK, cancel the changes made as quantity can never be negative.
     * @return
     */
    private boolean updateFinalStock() {
        int stockLeft = this.startStock + this.received - this.consumed;

        if(stockLeft < 0) {
            return false;
        }

        this.finalStock = stockLeft;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[").append( this.startStock ).append("+").append( this.received )
                .append("-").append( this.consumed ).append("=").append( this.finalStock )
                .append("]");

        return builder.toString();
    }
}
