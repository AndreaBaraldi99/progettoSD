package it.unimib.finalproject.server;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class Movies {
	
	/* private static List<Movie> movies = new ArrayList<Movie>();
	
	static {
		Movie mv = new Movie();
		mv.setTitle("Colpa delle stelle");		
		mv.setDate("30/08/2023");
		mv.setTime("18:00");
		mv.setRoom(5);
		mv.setLength(120);
		movies.add(mv);
		
		mv = new Movie();
		mv.setTitle("Salvate il soldato Ryan");
		mv.setDate("30/08/2023");
		mv.setTime("18:00");
		mv.setRoom(6);
		mv.setLength(90);
		movies.add(mv);
		
		mv = new Movie();
		mv.setTitle("Interstellar");
		mv.setDate("01/09/2023");
		mv.setTime("20:00");
		mv.setRoom(2);
		mv.setLength(150);
		movies.add(mv);
				
	}	 */
	
	private static List<List<Movie>> days = new ArrayList<List<Movie>>(7);
	private static List<Reservation> reservations = new ArrayList<Reservation>();

	static {
		for(int i = 0; i < 7; i++) {
			days.add(new ArrayList<Movie>());
		}
		int day = 30;
		for(int i = 0; i < 7; i++) {
			if(day > 31) {
				day = 1;
			}
			Movie mv = new Movie();
			mv.setTitle("Colpa delle stelle");
			mv.setDate(day + "/08/2023");
			Room[] rooms = new Room[2];
			rooms[0] = new Room();
			rooms[0].setTime("18:00");
			rooms[0].setRoom(5);
			rooms[0].setLength(120);
			int[][] seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[0].setSeatsGrid(seatsGrid);
			rooms[1] = new Room();
			rooms[1].setTime("20:00");
			rooms[1].setRoom(6);
			rooms[1].setLength(90);
			seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[1].setSeatsGrid(seatsGrid);
			mv.setRooms(rooms);
			days.get(i).add(mv);

			mv = new Movie();
			mv.setTitle("Salvate il soldato Ryan");
			mv.setDate(day + "/08/2023");
			rooms = new Room[2];
			rooms[0] = new Room();
			rooms[1] = new Room();
			rooms[0].setTime("18:00");
			rooms[0].setRoom(5);
			rooms[0].setLength(120);
			seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[0].setSeatsGrid(seatsGrid);
			rooms[1].setTime("20:00");
			rooms[1].setRoom(6);
			rooms[1].setLength(90);
			seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[1].setSeatsGrid(seatsGrid);
			mv.setRooms(rooms);
			days.get(i).add(mv);

			mv = new Movie();
			mv.setTitle("Interstellar");
			mv.setDate(day + "/08/2023");
			rooms = new Room[2];
			rooms[0] = new Room();
			rooms[1] = new Room();
			rooms[0].setTime("18:00");
			rooms[0].setRoom(5);
			rooms[0].setLength(120);
			seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[0].setSeatsGrid(seatsGrid);
			rooms[1].setTime("20:00");
			rooms[1].setRoom(6);
			rooms[1].setLength(90);
			seatsGrid = new int[10][10];
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					seatsGrid[j][k] = (int)Math.round(Math.random());
				}
			}
			rooms[1].setSeatsGrid(seatsGrid);
			mv.setRooms(rooms);
			days.get(i).add(mv);
			day++;	
		}
		
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovies() {
		return Response.ok(days).build();
	}
	
	@GET
	@Path("/{dayIndex}/{movieIndex}/{time}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovie(@PathParam ("dayIndex") int dayIndex, @PathParam ("movieIndex") int movieIndex, @PathParam ("time") String time) {
		for(Room room : days.get(dayIndex).get(movieIndex).getRooms()) {
			if(room.getTime().equals(time)) {
				return Response.ok(room).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/{dayIndex}/{movieIndex}/{time}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMovie(@PathParam ("dayIndex") int dayIndex, @PathParam ("movieIndex") int movieIndex, @PathParam ("time") String time, String body) {
		try {
			var mapper = new ObjectMapper();
			var reservation = mapper.readValue(body, Reservation.class);
			//System.out.println(body);
			//System.out.println(reservation.getSeats().get(0).getRow() + " " + reservation.getSeats().get(0).getColumn());
			for(Room room : days.get(dayIndex).get(movieIndex).getRooms()) {
				if(room.getTime().equals(time)) {
					for(Seat seat : reservation.getSeats()) {
						room.getSeatsGrid()[seat.getRow()][seat.getColumn()] = 1;					
					}
					reservation.setTitle(room.getTitle());
					reservation.setDayIndex(dayIndex);
					reservation.setMovieIndex(movieIndex);
					reservation.setTime(time);
					reservations.add(reservation);
					room.updateAvailable();					
					return Response.ok().build();
				}
			}
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/reservations/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservation(@PathParam ("code") String code) {
		for(Reservation reservation : reservations) {
			if(reservation.getCode().equals(code)) {
				return Response.ok(reservation).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
}
