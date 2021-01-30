package server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {
    ArrayList<PrintWriter> clientOutputStream;

    public static void main(String[] args) {
        System.out.println("Started server at port 5000");
        new Server();
    }

    public Server() {
        clientOutputStream = new ArrayList<>();
        try (ServerSocket ss = new ServerSocket(5000)) {
            while (true) {
                Socket clientSocket = ss.accept();
                System.out.println("Got a connection");
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                clientOutputStream.add(writer);
                Thread t = new Thread(new ClientHandle(reader));
                t.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    class ClientHandle implements Runnable {
        BufferedReader reader;

        public ClientHandle(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            String message;
            try {
                message = reader.readLine();
                while (message != null) {
                    System.out.println("Reading; " + message);
                    for (PrintWriter writer : clientOutputStream) {
                        writer.println(message);
                        writer.flush();
                    }
                    message = reader.readLine();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}
