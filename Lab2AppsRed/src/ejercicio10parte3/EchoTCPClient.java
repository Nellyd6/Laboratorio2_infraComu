package ejercicio10parte3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient {
    public static void main(String[] args) throws IOException {
        // 🔴 Reemplaza con la IP del servidor
        Socket clientSideSocket = new Socket("192.168.1.100", 3400);
        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
        BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la ruta del archivo a enviar: ");
        String filePath = scanner.nextLine();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                toNetwork.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }

        toNetwork.println("END");  // Indica el fin del mensaje

        // Leer la respuesta del servidor
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



