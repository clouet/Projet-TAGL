package clientServeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import tagl.Gestion_cle_valeur;

public class ThreadServer implements Runnable {
	Gestion_cle_valeur gkey;
	int portnumber;

	public ThreadServer(Gestion_cle_valeur g, int p) {
		gkey = g;
		portnumber = p;
	}

	@Override
	public void run() {
		System.out.println("nouveau thread, port : "+portnumber);
		while (true) {
			Server s = new Server();
			
			try (ServerSocket serverSocket = new ServerSocket(portnumber);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println("message recu sur "+portnumber+ " : "+ inputLine);
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
								out.println("set echoué");
							}
							break;
						case ("setnx"):
							if (s.gkey.setnx(tabs[1], tabs[2]) == 1) {
								out.println("setnx reussi");
							} else {
								out.println("setnx echoué");
							}
							break;
						case ("del"):
							if (s.gkey.del(tabs[1]) == 1) {
								out.println("del reussi");
							} else {
								out.println("del echoué");
							}
							break;
						case ("incr"):
							out.println(s.gkey.incr(tabs[1]));
							break;
						case ("incrby"):
							out.println(s.gkey.incrBy(tabs[1], Integer.parseInt(tabs[2])));
							break;
						case ("decr"):
							out.println(s.gkey.decr(tabs[1]));
							break;
						case ("decrby"):
							out.println(s.gkey.decrBy(tabs[1], Integer.parseInt(tabs[2])));
							break;
						case ("exists"):
							if (s.gkey.exists(tabs[1]) == 1) {
								out.println("la clé existe");
							} else {
								out.println("la clé n'existe pas");
							}
							break;
						case ("rename"):
							if (s.gkey.rename(tabs[1], tabs[2]) == 1) {
								out.println("rename reussi");
							} else {
								out.println("rename echoué");
							}
							break;
						case ("renamenx"):
							if (s.gkey.renamenx(tabs[1], tabs[2]) == 1) {
								out.println("renamenx reussi");
							} else {
								out.println("renamenx echoué");
							}
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
				System.out.println("Exception caught when trying to listen on port " + portnumber
						+ " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
	}
}
