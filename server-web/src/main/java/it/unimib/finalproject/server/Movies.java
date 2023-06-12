package it.unimib.finalproject.server;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
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
			mv.setTime("18:00");
			mv.setRoom(5);
			mv.setLength(120);
			mv.setSeats(100);
			mv.setAvailable(100);
			mv.setSeatsGrid(new int[10][10]);
			days.get(i).add(mv);

			mv = new Movie();
			mv.setTitle("Salvate il soldato Ryan");
			mv.setDate(day + "/08/2023");
			mv.setTime("20:00");
			mv.setRoom(5);
			mv.setLength(90);
			mv.setSeats(100);
			mv.setAvailable(100);
			mv.setSeatsGrid(new int[10][10]);
			days.get(i).add(mv);

			mv = new Movie();
			mv.setTitle("Interstellar");
			mv.setDate(day + "/08/2023");
			mv.setTime("22:00");
			mv.setRoom(6);
			mv.setLength(150);
			mv.setSeats(150);
			mv.setAvailable(150);
			mv.setSeatsGrid(new int[10][15]);
			days.get(i).add(mv);
			day++;	
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovies() {
		return Response.ok(days).build();
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDaySchedule(@PathParam("id") int id) {
		if(id < 0 || id > 6)
			return Response.status(Response.Status.BAD_REQUEST).build();
		return Response.ok(days.get(id)).build();
	}

	@Path("/{id}/{room}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoomSchedule(@PathParam("id") int id, @PathParam("room") int room) {
		List<Movie> movies = new ArrayList<Movie>();
		if(id < 0 || id > 6)
			return Response.status(Response.Status.BAD_REQUEST).build();
		for(Movie mv : days.get(id)) {
			if(mv.getRoom() == room)
				movies.add(mv);
		}
		if(movies.isEmpty())
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(movies).build();
	}
}
