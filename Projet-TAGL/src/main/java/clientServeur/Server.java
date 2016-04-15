package clientServeur;

import java.net.*;

import tagl.Gestion_cle_valeur;
import java.io.*;

public class Server {
	static Gestion_cle_valeur gkey;

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}
		int p = 8889;
		System.out.println("Server Ready");
		while (true) {
			gkey = new Gestion_cle_valeur();
			while (true) {
				try (ServerSocket serverSocket = new ServerSocket(8888);
						Socket clientSocket = serverSocket.accept();
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

					Thread x = new Thread(new ThreadServer(gkey, p));
					x.start();
					out.println(p);
					p += 1;

				} catch (IOException e) {
					System.out.println("Exception caught when trying to listen on port " + 8888
							+ " or listening for a connection");
					System.out.println(e.getMessage());
				}
			}
		}

	}
}
