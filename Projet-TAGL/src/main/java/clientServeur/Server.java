package clientServeur;

import java.net.*;

import tagl.Gestion_cle_valeur;
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
					String[] t = inputLine.split(" ");
					String[] tabs = new String[6];
					for (int i = 0; i < t.length; i++) {
						tabs[i] = t[i];
					}
					try {
						switch (tabs[0]) {
						case ("get"):
							out.println(s.gkey.get(tabs[1]));
							break;
						case ("set"):
							if (s.gkey.set(tabs[1], tabs[2]) == 1) {
								out.println("set reussi");
							} else {
								out.println("set non reussi");
							}
							break;
						case ("setnx"):
							if (s.gkey.setnx(tabs[1], tabs[2]) == 1) {
								out.println("setnx reussi");
							} else {
								out.println("setnx non reussi");
							}
							break;
						case ("del"):
							if (s.gkey.del(tabs[1]) == 1) {
								out.println("del reussi");
							} else {
								out.println("del non reussi");
							}
							break;
						case ("incr"):
							out.println(s.gkey.incr(tabs[1]));
							break;
						case ("incrby"):
							out.println(s.gkey.incrBy(tabs[1], Integer.parseInt(tabs[2])));
							break;
						default:
							out.println("il est possible que ça n'ai pas marché");
							break;
						}
					} catch (Exception e) {
						out.println(e.getClass().toString());
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
