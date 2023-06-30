package it.unimib.finalproject.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class Movies {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovies() {
		List<List<Movie>> fromDB = new ArrayList<List<Movie>>(7);
		for(int i = 0; i < 7; i++) {
			fromDB.add(new ArrayList<Movie>());
		}
		try {
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GETBYTYPE_M";
			out.println(request);
			while(true){
				String response = in.readLine();
				if(response == null || response.equals("") || response.equals("null") || response.equals("OK")){
					break;
				}
				var mapper = new ObjectMapper();
				DBResponse dbResponse = mapper.readValue(response, DBResponse.class);
				List<Movie> dayMovies = fromDB.get(dbResponse.getDayIndex());
				if (dayMovies.size() <= dbResponse.getMovieIndex()) {
					while(dayMovies.size() < dbResponse.getMovieIndex()) {
						dayMovies.add(new Movie());
					}
					Movie movie = new Movie();
					setMovie(dbResponse, movie);
					dayMovies.add(dbResponse.getMovieIndex(), movie);
				} else {
					Movie movie = dayMovies.get(dbResponse.getMovieIndex());
					setMovie(dbResponse, movie);
				}
			}
			closeConnection(clientSocket, in, out);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(fromDB).build();
	}
	
	@GET
	@Path("/{dayIndex}/{movieIndex}/{time}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovie(@PathParam ("dayIndex") int dayIndex, @PathParam ("movieIndex") int movieIndex, @PathParam ("time") String time) {
		try {
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GET_M" + dayIndex + movieIndex + time;
			out.println(request);
			String response = in.readLine();
			if(response == null || response.equals("") || response.equals("null")){
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			var mapper = new ObjectMapper();
			DBResponse dbResponse = mapper.readValue(response, DBResponse.class);
			closeConnection(clientSocket, in, out);
			return Response.ok(dbResponse.getRoom()).build();
			
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/{dayIndex}/{movieIndex}/{time}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postMovie(@PathParam ("dayIndex") int dayIndex, @PathParam ("movieIndex") int movieIndex, @PathParam ("time") String time, String body) {
		try {
			var mapper = new ObjectMapper();
			var reservation = mapper.readValue(body, Reservation.class);
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GET_M" + dayIndex + movieIndex + time;
			out.println(request);
			String response = in.readLine();
			if(response == null || response.equals("") || response.equals("null")){
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.NOT_FOUND).build();
			} else if(in.readLine().equals("OK")){
				DBResponse dbResponse = mapper.readValue(response, DBResponse.class);
				for(Seat seat : reservation.getSeats()) {
						dbResponse.getRoom().getSeatsGrid()[seat.getRow()][seat.getCol()] = 1;					
				}
				dbResponse.getRoom().updateAvailable();
				out.println("UPDATE_M" + dayIndex + movieIndex + time + "&" + mapper.writeValueAsString(dbResponse));
				if(in.readLine().equals("OK")) {
					reservation.setDate(dbResponse.getRoom().getDate());
					reservation.setTitle(dbResponse.getRoom().getTitle());
					reservation.setDayIndex(dayIndex);
					reservation.setMovieIndex(movieIndex);
					reservation.setTime(time);
					reservation.setRoom(dbResponse.getRoom().getRoom());
					out.println("POST_R" + reservation.getCode() + "&" + mapper.writeValueAsString(reservation));
					if(in.readLine().equals("OK")) {
						closeConnection(clientSocket, in, out);
						return Response.ok().build();
					} else {
						closeConnection(clientSocket, in, out);
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
					}
				} else {
					closeConnection(clientSocket, in, out);
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}
			}else {
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			} 
		} catch (JsonParseException | JsonMappingException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/reservations/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservation(@PathParam ("code") String code) {
		try {
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GET_R" + code;
			out.println(request);
			String response = in.readLine();
			if(response == null || response.equals("") || response.equals("null")){
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.NOT_FOUND).build();
			}else if(in.readLine().equals("OK")){
				var mapper = new ObjectMapper();
				Reservation reservation = mapper.readValue(response, Reservation.class);
				closeConnection(clientSocket, in, out);
				return Response.ok(reservation).build();
			}else{
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("/reservations/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservation(@PathParam ("code") String code) {

		try {
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GET_R" + code;
			out.println(request);
			String response = in.readLine();
			if(response == null || response.equals("") || response.equals("null")){
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.NOT_FOUND).build();
			}else if(in.readLine().equals("OK")){
				var mapper = new ObjectMapper();
				Reservation reservation = mapper.readValue(response, Reservation.class);
				out.println("GET_M" + reservation.getDayIndex() + reservation.getMovieIndex() + reservation.getTime());
				response = in.readLine();
				if(response == null || response.equals("") || response.equals("null")){
					closeConnection(clientSocket, in, out);
					return Response.status(Response.Status.NOT_FOUND).build();
				}else if(in.readLine().equals("OK")){
					DBResponse dbResponse = mapper.readValue(response, DBResponse.class);
					for(Seat seat : reservation.getSeats()) {
						dbResponse.getRoom().getSeatsGrid()[seat.getRow()][seat.getCol()] = 0;					
					}
					dbResponse.getRoom().updateAvailable();
					out.println("UPDATE_M" + reservation.getDayIndex() + reservation.getMovieIndex() + reservation.getTime() + "&" + mapper.writeValueAsString(dbResponse));
					if(in.readLine().equals("OK")) {
						out.println("DELETE_R" + code);
						if(in.readLine().equals("OK")) {
							closeConnection(clientSocket, in, out);
							return Response.ok().build();
						} else {
							closeConnection(clientSocket, in, out);
							return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
						}
					} else {
						closeConnection(clientSocket, in, out);
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
					}
				}else{
					closeConnection(clientSocket, in, out);
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}				
			}else{
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/reservations/{code}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateReservation(@PathParam ("code") String code, String body) {

		try {
			Socket clientSocket = new Socket("localhost", 3030);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream out = new PrintStream(clientSocket.getOutputStream());
			String request = "GET_R" + code;
			out.println(request);
			String response = in.readLine();
			if(response == null || response.equals("") || response.equals("null")){
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.NOT_FOUND).build();
			}else if(in.readLine().equals("OK")){
				var mapper = new ObjectMapper();
				Reservation reservation = mapper.readValue(response, Reservation.class);
				Reservation newReservation = mapper.readValue(body, Reservation.class);
				out.println("GET_M" + reservation.getDayIndex() + reservation.getMovieIndex() + reservation.getTime());
				response = in.readLine();
				if(response == null || response.equals("") || response.equals("null")){
					closeConnection(clientSocket, in, out);
					return Response.status(Response.Status.NOT_FOUND).build();
				}else if(in.readLine().equals("OK")){
					DBResponse dbResponse = mapper.readValue(response, DBResponse.class);
					for(Seat seat : reservation.getSeats()) {
						dbResponse.getRoom().getSeatsGrid()[seat.getRow()][seat.getCol()] = 0;					
					}
					for(Seat seat : newReservation.getSeats()) {
						dbResponse.getRoom().getSeatsGrid()[seat.getRow()][seat.getCol()] = 1;					
					}
					dbResponse.getRoom().updateAvailable();
					out.println("UPDATE_M" + reservation.getDayIndex() + reservation.getMovieIndex() + reservation.getTime() + "&" + mapper.writeValueAsString(dbResponse));
					if(in.readLine().equals("OK")){
						reservation.setSeats(newReservation.getSeats());
						out.println("UPDATE_R" + code + "&" + mapper.writeValueAsString(reservation));
						if(in.readLine().equals("OK")) {
							closeConnection(clientSocket, in, out);
							return Response.ok().build();
						} else {
							closeConnection(clientSocket, in, out);
							return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
						}
					} else {
						closeConnection(clientSocket, in, out);
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
					}
				} else {
					closeConnection(clientSocket, in, out);
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}
			} else {
				closeConnection(clientSocket, in, out);
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}			
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private void closeConnection(Socket clientSocket, BufferedReader in, PrintStream out) throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	private void setMovie(DBResponse dbResponse, Movie movie) {
		movie.setTitle(dbResponse.getRoom().getTitle());
		movie.setDate(dbResponse.getRoom().getDate());
		movie.getRooms().add(dbResponse.getRoom());
	}
	
}
