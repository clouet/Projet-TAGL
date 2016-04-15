package clientServeur;

import java.net.*;

import tagl.Gestion_cle_valeur;
import tagl.WrongTypeValueException;

import java.io.*;

public class Server {
	Gestion_cle_valeur gkey;
	Server() {
		gkey = new Gestion_cle_valeur();
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}
		while (true) {
			int portNumber = Integer.parseInt(args[0]);
			Server s = new Server();
			System.out.println("Server Ready");
			try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					String[] tabs = inputLine.split(" ");
					try {
					if (tabs[0].equals("get")) {
						
							out.println(s.gkey.get(tabs[1]));
						
					} else if (tabs[0].equals("set")) {
						out.println(s.gkey.set(tabs[1],tabs[2]));
					}  else
						out.println("il est possible que ça n'ai pas marché");
					} catch (Exception e) {
						out.println(e.getMessage());
					}
				}
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port " + portNumber
						+ " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}

	}

}
