package ejercicio8parte3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoTCPClient {
	
	public static void main(String[] args) throws IOException { 
		Socket clientSideSocket = new Socket("localhost", 3700); 
		PrintWriter toNetwork = new PrintWriter(clientSideSocket.getOutputStream(), true); 
		BufferedReader fromNetwork = new BufferedReader(new 
		InputStreamReader(clientSideSocket.getInputStream())); 
		Scanner scanner = new Scanner (System.in);
		
		System.out.println("Ingrese un mensaje:");
		String mensajeUsuario = scanner.nextLine();
		toNetwork.println(mensajeUsuario); 
		
		String fromServer = fromNetwork.readLine(); 
		System.out.println("[Client] From server: " + fromServer); 
		
		scanner.close();
		clientSideSocket.close(); 
		} 


}
