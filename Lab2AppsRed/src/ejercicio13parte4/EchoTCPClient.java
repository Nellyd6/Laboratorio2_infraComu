package ejercicio13parte4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class EchoTCPClient {
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Conectar al servidor
        Socket clientSideSocket = new Socket("localhost", 3400);
        PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true);
        BufferedReader fromNetwork = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));

        // Generar aleatoriamente un String de 32 elementos (unos y ceros)
        String bitString = generateRandomBitString(32);
        System.out.println("[Client] Generated bitString: " + bitString);

        // Solicitar al usuario el número entero n entre 2 y 30
        System.out.print("Ingrese un número entre 2 y 30: ");
        int n = scanner.nextInt();

        // Verificar que n esté dentro del rango permitido
        if (n < 2 || n > 30) {
            System.out.println("Error: El número debe estar entre 2 y 30.");
            clientSideSocket.close();
            return;
        }

        // Enviar el bitString y el número n al servidor
        toNetwork.println(bitString);
        toNetwork.println(n);

        // Leer la respuesta del servidor
        String response = fromNetwork.readLine();
        System.out.println("[Client] Received from server: " + response);

        scanner.close();
        clientSideSocket.close();
    }

    // Método para generar un String aleatorio de 0s y 1s de longitud `length`
    private static String generateRandomBitString(int length) {
        Random random = new Random();
        StringBuilder bitString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bitString.append(random.nextBoolean() ? "1" : "0");
        }
        return bitString.toString();
    }
}

