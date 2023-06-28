package it.unimib.finalproject.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {

    @JsonProperty("row")
    private int row;
    @JsonProperty("col")
    private int col;
    
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return col;
    }
    public void setColumn(int col) {
        this.col = col;
    }

    
}
