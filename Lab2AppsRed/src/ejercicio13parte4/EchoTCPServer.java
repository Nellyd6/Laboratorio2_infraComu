package ejercicio13parte4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(3400)) {
			System.out.println("The server is running on port 3400 ...");

			while (true) {
			    Socket serverSideSocket = listener.accept();
			    System.out.println("A client has connected.");

			    new ClientHandler(serverSideSocket).start();
			}
		}
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter toNetwork = new PrintWriter(clientSocket.getOutputStream(), true);

            // Leer la secuencia de bits y el número n
            String bitString = fromNetwork.readLine();
            int n = Integer.parseInt(fromNetwork.readLine());

            // Verificar que n esté dentro del rango
            if (n < 2 || n > 30) {
                toNetwork.println("Error: El número debe estar entre 2 y 30.");
                clientSocket.close();
                return;
            }

            // Crear el nuevo String con los primeros n caracteres de bitString y el resto en 1
            StringBuilder result = new StringBuilder(bitString.substring(0, n));
            for (int i = n; i < 32; i++) {
                result.append("1");
            }

            System.out.println("[Server] Received bitString: " + bitString);
            System.out.println("[Server] Responding with: " + result.toString());

            // Enviar la respuesta al cliente
            toNetwork.println(result.toString());

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

