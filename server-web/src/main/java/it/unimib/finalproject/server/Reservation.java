package it.unimib.finalproject.server;

import java.util.ArrayList;
import java.util.List;


public class Reservation {
    
    private List<Seat> seats = new ArrayList<Seat>();
    private String code;
    private int dayIndex;
    private int movieIndex;
    private String time;
    private String title;
    private String date;
    private int room;

    public List<Seat> getSeats() {
        return seats;
    }
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getDayIndex() {
        return dayIndex;
    }
    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }
    public int getMovieIndex() {
        return movieIndex;
    }
    public void setMovieIndex(int movieIndex) {
        this.movieIndex = movieIndex;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
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
}
