package ejercicio10parte3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(3400);
        System.out.println("The Echo TCP server is running on port 3400 ...");

        while (true) {
            Socket serverSideSocket = listener.accept();
            System.out.println("A client has connected.");

            new ClientHandler(serverSideSocket).start();
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
            System.out.println("Client connected from: " + clientIP);

            // Leer el archivo enviado por el cliente línea por línea
            String line;
            StringBuilder receivedContent = new StringBuilder();
            while ((line = fromNetwork.readLine()) != null) {
                if (line.equals("END")) break;  // Termina la lectura
                receivedContent.append(line).append("\n");
            }

            System.out.println("[Server] Received from client:\n" + receivedContent.toString());

            // Enviar de regreso el contenido recibido
            toNetwork.println("Archivo recibido:\n" + receivedContent.toString());
            toNetwork.println("END"); // Señal de fin de mensaje

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


