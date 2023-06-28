package it.unimib.finalproject.server;

public class Movie{
	
	private String title;
	private String date;
	private Room[] rooms;
	
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
	public Room[] getRooms() {
		return rooms;
	}
	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
		for(Room r : rooms){
			r.setTitle(title);
			r.setDate(date);
		}
	}
	
}
