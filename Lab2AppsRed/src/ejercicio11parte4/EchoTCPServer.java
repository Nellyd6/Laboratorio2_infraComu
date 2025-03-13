package ejercicio11parte4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(3400)) {
			System.out.println("The Hexadecimal Conversion server is running on port 3400 ...");

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

            // Leer el número entero y la cantidad de dígitos hexadecimales
            int number = Integer.parseInt(fromNetwork.readLine());
            int digits = Integer.parseInt(fromNetwork.readLine());

            // Convertir el número a hexadecimal
            String hexValue = Integer.toHexString(number).toUpperCase();

            // Asegurarse de que el número hexadecimal tenga el número de dígitos especificado
            while (hexValue.length() < digits) {
                hexValue = "0" + hexValue;  // Rellenar con ceros a la izquierda
            }

            System.out.println("[Server] Number: " + number + " converted to hexadecimal: " + hexValue);

            // Enviar la respuesta al cliente
            toNetwork.println(hexValue);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



