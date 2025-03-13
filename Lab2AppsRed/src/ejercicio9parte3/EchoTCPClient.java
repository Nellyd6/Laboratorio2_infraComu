package ejercicio9parte3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient {
    public static void main(String[] args) throws IOException {
       
        Socket clientSideSocket = new Socket("192.168.87.23", 3400);

        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
        BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escriba su mensaje (múltiples líneas). Escriba 'END' para finalizar:");

        while (true) {
            String userMessage = scanner.nextLine();
            toNetwork.println(userMessage); 

            if (userMessage.equals("END")) break;  
        }

       
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = fromNetwork.readLine()) != null) {
            if (line.equals("END")) break;
            response.append(line).append("\n");
        }

        System.out.println("[Client] From server:\n" + response.toString());

        scanner.close();
        clientSideSocket.close();
    }
}


