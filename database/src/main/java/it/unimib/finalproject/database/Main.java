package it.unimib.finalproject.database;

import java.net.*;

import java.io.*;

/**
 * Classe principale in cui parte il database.
 */
public class Main {
    /**
     * Porta di ascolto.
     */
    public static final int PORT = 3030;

    /**
     * Avvia il database e l'ascolto di nuove connessioni.
     *
     * @return Un server HTTP Grizzly.
     */
    public static void startServer() {
        try {
            var server = new ServerSocket(PORT);
            Database database = new Database();
            System.out.println("Database listening at localhost:" + PORT);
            //System.out.println(database.getData("M1"));
            while (true)
                new Handler(server.accept(), database).start();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Handler di una connessione del client.
     */
    private static class Handler extends Thread {
        private Socket client;
        private Database database;

        public Handler(Socket client, Database database) {
            this.client = client;
            this.database = database;
        }

        public void run() {
            try {

                var out = new PrintWriter(client.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received: " + inputLine);
                    String[] keyValue = inputLine.split(" ");
                    System.out.println("Input received");
                    if (keyValue[0].equals("GET")) {
                        out.println(database.getData(keyValue[1]));
                    } else if (keyValue[0].equals("POST")) {
                        database.setData(keyValue[1], keyValue[2]);
                        out.println("OK");
                    } else if (keyValue[0].equals("DELETE")) {
                        database.deleteData(keyValue[1]);
                        out.println("OK");
                    } else if (keyValue[0].equals("UPDATE")) {
                        database.updateData(keyValue[1], keyValue[2]);
                        out.println("OK");
                    } else {
                        out.println("ERROR");
                    }
                }

                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Metodo principale di avvio del database.
     *
     * @param args argomenti passati a riga di comando.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        startServer();
    }
}

