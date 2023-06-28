package it.unimib.finalproject.server;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {
    
    @JsonProperty("seats")
    private List<Seat> seats = new ArrayList<Seat>();
    @JsonProperty("code")
    private String code;
    private int dayIndex;
    private int movieIndex;
    private String time;
    private String title;

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
}
