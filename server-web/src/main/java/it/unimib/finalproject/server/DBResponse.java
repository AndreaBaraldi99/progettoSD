package it.unimib.finalproject.server;

public class DBResponse {
    private int dayIndex;
    private int movieIndex;
    private int roomIndex;
    private Room room;
    
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
    public int getRoomIndex() {
        return roomIndex;
    }
    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    
}
