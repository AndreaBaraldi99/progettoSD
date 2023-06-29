package it.unimib.finalproject.server;

import java.util.ArrayList;
import java.util.List;

public class Movie{
	
	private String title;
	private String date;
	private List<Room> rooms = new ArrayList<Room>();
	
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
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
		for(Room r : rooms){
			r.setTitle(title);
			r.setDate(date);
		}
	}
	
}
