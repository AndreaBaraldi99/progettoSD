package it.unimib.finalproject.server;

public class Room {

    private String title;
    private String time;
	private int room;
	private int length;
    private int available = 0;
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
    public int getAvailable() {
        return available;
    }
    public int[][] getSeatsGrid() {
        return seatsGrid;
    }
    public void setSeatsGrid(int[][] seatsGrid) {
        this.seatsGrid = seatsGrid;
        for(int i = 0; i < seatsGrid.length; i++)
            for(int j = 0; j < seatsGrid[i].length; j++)
                if(seatsGrid[i][j] == 0)
                    available++;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void updateAvailable() {
        available = 0;
        for(int i = 0; i < seatsGrid.length; i++)
            for(int j = 0; j < seatsGrid[i].length; j++)
                if(seatsGrid[i][j] == 0)
                    available++;
    }
    
}
