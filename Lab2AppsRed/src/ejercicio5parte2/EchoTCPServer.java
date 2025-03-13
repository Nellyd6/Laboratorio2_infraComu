package ejercicio5parte2;

import java.io.*;
import java.net.*;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        // Escucha en todas las interfaces de red disponibles
        ServerSocket listener = new ServerSocket(3400, 50, InetAddress.getByName("0.0.0.0"));
        System.out.println("The Echo TCP server is running on port 3400 ...");

        while (true) { // Para aceptar múltiples clientes
            System.out.println("The server is waiting for a client.");
            Socket serverSideSocket = listener.accept();
            System.out.println("A client has connected.");

            BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
            PrintWriter toNetwork = new PrintWriter(serverSideSocket.getOutputStream(), true);

            String message = fromNetwork.readLine();
            System.out.println("[Server] From client: " + message);
            toNetwork.println(message); // Enviar respuesta

            serverSideSocket.close(); // Cerrar conexión con el cliente
        }
    }
}
