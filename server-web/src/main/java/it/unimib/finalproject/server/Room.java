package it.unimib.finalproject.server;

public class Room {
    private String time;
	private int room;
	private int length;
	private int seats;
	private int available;
	private int[][] seatsGrid;
    
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getRoom() {
        return room;
    }
    public void setRoom(int room) {
        this.room = room;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public int getAvailable() {
        return available;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public int[][] getSeatsGrid() {
        return seatsGrid;
    }
    public void setSeatsGrid(int[][] seatsGrid) {
        this.seatsGrid = seatsGrid;
    }

    
}
