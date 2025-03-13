package ejercicio9parte3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(3400)) {
			System.out.println("The Echo TCP server is running on port 3400 ...");

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

            String clientIP = clientSocket.getInetAddress().getHostAddress();
            int clientPort = clientSocket.getPort();
            System.out.println("Client connected from: " + clientIP + ":" + clientPort);

           
            StringBuilder message = new StringBuilder();
            String line;
            while ((line = fromNetwork.readLine()) != null) {
                if (line.equals("END")) break;  // Finaliza la lectura cuando recibe "END"
                message.append(line).append("\n");
            }

            System.out.println("[Server] From client " + clientIP + ":\n" + message.toString());

            toNetwork.println("Mensaje recibido:\n" + message.toString());
            toNetwork.println("END"); // Indicar fin del mensaje

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
