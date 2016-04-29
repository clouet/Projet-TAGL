package clientServeur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagl.Gestion_cle_valeur;
import tagl.ListScore;

public class ThreadServer implements Runnable {
	Gestion_cle_valeur gkey;
	int portnumber;
	public ThreadServer(Gestion_cle_valeur g, int p) {
		gkey = g;
		portnumber = p;
	}
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		System.out.println("nouveau thread, port : " + portnumber);
		while (true) {
			Server s = new Server();
			try (ServerSocket serverSocket = new ServerSocket(portnumber);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println("message recu sur " + portnumber + " : " + inputLine);
					String[] tabs = new String[16];
					for(int i=0;i<15;i++){
						tabs[i]="";
					}
					ArrayList<String> list = new ArrayList<String>();
					Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputLine);
					while (m.find()) {
						list.add(m.group(1));
					}
					for (int i = 0; i < list.size(); i++) {
						tabs[i] = (String) list.get(i);
					}
					try {
						synchronized (gkey) {
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
							case ("lpop"):
								out.println(s.gkey.lpop(tabs[1]));
								break;
							case ("rpop"):
								out.println(s.gkey.rpop(tabs[1]));
								break;
							case ("lpush"):
								ArrayList<String> a = new ArrayList<>();

								for (int i = 2; i < list.size(); i++) {
									a.add(tabs[i]);
								}
								out.println(s.gkey.lpush(tabs[1], a));
								break;
							case ("rpush"):
								ArrayList<String> b = new ArrayList<>();
								for (int i = 2; i < list.size(); i++) {
									b.add(tabs[i]);
								}
								out.println(s.gkey.rpush(tabs[1], b));
								break;
							case ("rpushx"):
								out.println(s.gkey.rpushx(tabs[1], tabs[2]));
								break;
							case ("zadd"):
								ArrayList<ListScore> c = new ArrayList<>();
							for (int i = 2; i < list.size()-1; i+=2) {
								c.add(new ListScore(Integer.parseInt(tabs[i]),tabs[i+1]));
							}
								out.println(s.gkey.zAdd(tabs[1], c));
								break;
							case ("zget"):
								out.println(s.gkey.zGet(tabs[1]));
								break;	
							default:
								out.println("il est possible que ça n'ai pas marché");
								break;
							}
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
