package ejercicio11parte4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Conectar al servidor
        Socket clientSideSocket = new Socket("localhost", 3400);
        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
        BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));

        // Solicitar el número entero
        System.out.print("Ingrese un número entero: ");
        int number = scanner.nextInt();

        // Solicitar la cantidad de dígitos hexadecimales
        System.out.print("Ingrese la cantidad de dígitos hexadecimales: ");
        int digits = scanner.nextInt();

        // Enviar el número y la cantidad de dígitos al servidor
        toNetwork.println(number);
        toNetwork.println(digits);

        // Leer la respuesta del servidor
        String hexValue = fromNetwork.readLine();
        System.out.println("[Client] Hexadecimal value: " + hexValue);

        scanner.close();
        clientSideSocket.close();
    }
}

