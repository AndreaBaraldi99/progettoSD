package it.unimib.finalproject.server;

public class Movie{
	
	private String title;
	private String date;
	private String time;
	private int room;
	private int length;
	private int seats;
	private int available;
	private int[][] seatsGrid;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public int getSeats() {
		return seats;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getAvailable() {
		return available;
	}
	public void setSeatsGrid(int[][] seatsGrid) {
		this.seatsGrid = seatsGrid;
	}
	public int[][] getSeatsGrid() {
		return seatsGrid;
	}
	
}
