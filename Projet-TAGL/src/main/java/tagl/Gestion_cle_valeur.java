package tagl;

import java.util.ArrayList;

public class Gestion_cle_valeur {
	private ArrayList<Cle_valeur> list;
	
	public Gestion_cle_valeur(){
		if(list == null) list = new ArrayList<>();
	}
	
	/**
	 * Fonction permettant d'enregistrer une clﾃｩ associﾃｩe ﾃ� une valeur. 
	 * Si la clﾃｩ existait avant, la valeur est mise ﾃ� jour
	 * @param cle le nom de la clﾃｩ
	 * @param valeur la valeur ﾃ� associer ﾃ� la clﾃｩ
<<<<<<< HEAD
	 * @return 1 si l'enregistrement ﾃ� pu �ｾ�ｽｪtre fait, 0 sinon
=======
	 * @return 1 si l'enregistrement a pu ﾃｪtre fait, 0 sinon
>>>>>>> branch 'master' of https://github.com/clouet/Projet-TAGL
	 */
	public int set(String cle, String valeur){
		int reussi = 0;
		if(!(cle == null || valeur == null)){
			// si la clﾃｩ ﾃ� dﾃｩjﾃ� ﾃｩtﾃｩ enregistrﾃｩe avant
			if(cleExists(cle)){
				// on rﾃｩcupﾃｨre sa position dans la liste
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				// on modifie la valeur associﾃｩe ﾃ� cette clﾃｩ dans la liste
				tmp.setValeur(valeur);
				// on met ﾃ� jour la liste
				list.set(pos, tmp);
				reussi = 1;
			}
			// sinon
			else{
				// on crﾃｩe un nouveau couple (clﾃｩ, valeur) et on l'ajoute ﾃ� la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant d'enregistrer une clﾃｩ associﾃｩe ﾃ� une valeur. 
	 * La clﾃｩ ne doit pas avoir dﾃｩja ﾃｩtﾃｩ enregistrﾃｩe.
	 * @param cle le nom de la clﾃｩ
	 * @param valeur la valeur ﾃ� associer ﾃ� la clﾃｩ
	 * @return 1 si l'enregistrement a pu ﾃｪtre fait, 0 sinon
	 */
	public int setnx(String cle, String valeur){
		int reussi = 0;
		if(!(cle == null || valeur == null)){
			// si la clﾃｩ ﾃ� dﾃｩjﾃ� ﾃｩtﾃｩ enregistrﾃｩe avant
			if(!cleExists(cle)){
				// on crﾃｩe un nouveau couple (clﾃｩ, valeur) et on l'ajoute ﾃ� la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de rﾃｩcupﾃｩrer la valeur associﾃｩe ﾃ� une clﾃｩ
	 * La valeur doit ﾃｪtre un String
	 * @param cle le nom de la clﾃｩ dont on veut rﾃｩcupﾃｩrer la valeur associﾃｩe
	 * @return la valeur associﾃｩe ﾃ� la clﾃｩ
	 * @throws WrongTypeValueException si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas une String
	 */
	public String get(String cle) throws WrongTypeValueException{
		String valeur = null;
		if(!(cle == null)){
			if(cleExists(cle)){
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				if(tmp.getValeur() instanceof String){
					valeur = String.valueOf(tmp.getValeur());
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return valeur;
	}
	
	
	/**
	 * Fonction permettant d'enlever un couple (clﾃｩ,valeur) de la liste
	 * @param cle la clﾃｩ dont on veut enlever le couple
	 * @return 1 si le couple ﾃ� bien ﾃｩtﾃｩ enlevﾃｩ, 0 sinon
	 */
	public int del(String cle){
		int reussi = 0;
		if(!(cle == null)){
			if(cleExists(cle)){
				int pos = posCle(cle);
				list.remove(pos);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant d'incrﾃｩmenter la valeur associﾃｩe ﾃ� une clﾃｩ si cette valeur est un entier.
	 * Si la clﾃｩ n'exsiste pas, un nouveau couple (clﾃｩ,valeur) est crﾃｩe, avec comme valeur 1.
	 * @param cle la clﾃｩ dont on veut d'incrﾃｩmenter la valeur
	 * @return la nouvelle valeur associﾃｩe ﾃ� la clﾃｩ
	 * @throws NumberFormatException si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de 1 sans franchir la valeur maximum pour Integer
	 */
	public int incr(String cle) throws NumberFormatException, OverFlowException{
		int reussi = 0;
		if(!(cle == null)){
			if(!cleExists(cle)){
				Cle_valeur cv = new Cle_valeur(cle, "1");
				list.add(cv);
				reussi = 1;
			}
			else{
				int pos = posCle(cle);
				Cle_valeur cv = list.get(pos);
				if(cv.getValeur() instanceof String){
					String valeur = String.valueOf(cv.getValeur());
						try{
							int n = Integer.parseInt(valeur);
							if( n == Integer.MAX_VALUE){
								throw new OverFlowException();
							}
							n = n + 1;
							cv.setValeur(String.valueOf(n));
							reussi = n;
						}catch(NumberFormatException n){
							throw n;
						}
				}
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant d'augmenter la valeur associﾃｩe ﾃ� une clﾃｩ d'un certain entier, si cette valeur est un entier.
	 * Si la clﾃｩ n'exsiste pas, un nouveau couple (clﾃｩ,valeur) est crﾃｩe, avec comme valeur l'entier donnﾃｩ.
	 * @param cle la clﾃｩ dont on veut augmenter la valeur
	 * @param i l'entier duquel on veut augmenter la valeur associﾃｩe ﾃ� la clﾃｩ
	 * @return la nouvelle valeur associﾃｩe ﾃ� la clﾃｩ
	 * @throws NumberFormatException si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de i (i ﾃｩtant nﾃｩgatif)
	 */
	public int incrBy(String cle, int i) throws NumberFormatException, OverFlowException, UnderFlowException{
		int reussi = 0;
		if(!(cle == null)){
			if(!cleExists(cle)){
				Cle_valeur cv = new Cle_valeur(cle, String.valueOf(i));
				list.add(cv);
				reussi = i;
			}
			else{
				int pos = posCle(cle);
				Cle_valeur cv = list.get(pos);
				if(cv.getValeur() instanceof String){
					String valeur = String.valueOf(cv.getValeur());
					try{
						int n = Integer.parseInt(valeur);
						if(n > 0 && Integer.MAX_VALUE-n <= i){
							throw new OverFlowException();
						}
						if(n < 0 && i < 0 && Integer.MIN_VALUE - i >= n){
							throw new UnderFlowException();
						}
						n = n + i;
						cv.setValeur(String.valueOf(n));
						reussi = n;
					}catch(NumberFormatException n){
						throw n;
					}
				}
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de dﾃｩcrﾃｩmenter la valeur associﾃｩe ﾃ� une clﾃｩ si cette valeur est un entier.
	 * Si la clﾃｩ n'exsiste pas, un nouveau couple (clﾃｩ,valeur) est crﾃｩe, avec comme valeur -1.
	 * @param cle la clﾃｩ dont on veut dﾃｩcrﾃｩmenter la valeur
	 * @return la nouvelle valeur associﾃｩe ﾃ� la clﾃｩ, ou lﾃｨve une erreur si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier
	 * @throws NumberFormatException si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier acceptable
	 * @throws UnderFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de i (i ﾃｩtant nﾃｩgatif)
	 */
	public int decr(String cle) throws NumberFormatException, UnderFlowException{
		int reussi = 0;
		if(!(cle == null)){
			if(!cleExists(cle)){
				Cle_valeur cv = new Cle_valeur(cle, "-1");
				list.add(cv);
				reussi = -1;
			}
			else{
				int pos = posCle(cle);
				Cle_valeur cv = list.get(pos);
				if(cv.getValeur() instanceof String){
					String valeur = String.valueOf(cv.getValeur());
					try{
						int n = Integer.parseInt(valeur);
						if( n == Integer.MIN_VALUE){
							throw new UnderFlowException();
						}
						n = n - 1;
						cv.setValeur(String.valueOf(n));
						reussi = n;
					}catch(NumberFormatException n){
						throw n;
					}
				}
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant de diminuer la valeur associﾃｩe ﾃ� une clﾃｩ  si cette valeur est un entier.
	 * Si la clﾃｩ n'exsiste pas, un nouveau couple (clﾃｩ,valeur) est crﾃｩe, avec comme valeur -i.
	 * @param cle la clﾃｩ dont on veut dﾃｩcrﾃｩmenter la valeur
	 * @param i l'entier duquel on veut diminuer la valeur associﾃｩe ﾃ� la clﾃｩ
	 * @return la nouvelle valeur associﾃｩe ﾃ� la clﾃｩ, ou lﾃｨve une erreur si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier
	 * @throws NumberFormatException si la valeur associﾃｩe ﾃ� la clﾃｩ n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associﾃｩe ﾃ� la clﾃｩ ne peut pas ﾃｪtre augmentﾃｩe de i (i ﾃｩtant nﾃｩgatif)
	 */
	public int decrBy(String cle, int i) throws NumberFormatException, OverFlowException, UnderFlowException{
		int reussi = 0;
		/*if(!(cle == null)){
			if(!cleExists(cle)){
				String val = String.valueOf(-i);
				Cle_valeur cv = new Cle_valeur(cle, val);
				list.add(cv);
				reussi = -i;
			}
			else{
				int pos = posCle(cle);
				Cle_valeur cv = list.get(pos);
				String valeur = cv.getValeur();
				try{
					int n = Integer.parseInt(valeur);
					if(Integer.MAX_VALUE - n <= -i){
						throw new OverFlowException();
					}
					if(n - Integer.MIN_VALUE <= i){
						throw new UnderFlowException();
					}
					n = n - i;
					cv.setValeur(String.valueOf(n));
					reussi = n;
				}catch(NumberFormatException n){
					throw n;
				}
			}
			
		}
		*/
		reussi = incrBy(cle, -i);
		return reussi;
	}
	
	/**
	 * Fonction permettant de vﾃｩrifier si une clﾃｩ est enregistrﾃｩe.
	 * @param cle la clﾃｩ que l'on veut vﾃｩrifier
	 * @return 1 si la clﾃｩ est enregistrﾃｩe, 0 sinon
	 */
	public int exists(String cle){
		int exist = 0;
		if(!(cle == null)){
			if(cleExists(cle)) exist = 1;
		}
		return exist;
	}
	
	
	/**
	 * Fonction permettant de renommer une clﾃ enregistrﾃｩe
	 * @param cle la clﾃｩ que l'on veut renommer
	 * @param newname le nouveau nom pour la clﾃｩ
	 * @return 1 si le renommage ﾃ� rﾃｩussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clﾃｩ sont identiques
	 * @throws KeyNotExistsException si la clﾃｩ n'existe pas
	 */
	public int rename(String cle, String newname) throws SameNameException, KeyNotExistsException {
		int reussi = 0;
		if(!(cle == null || newname == null)){
			if (cle.equals(newname)){
				throw new SameNameException();
			}
			else{
				if(cleExists(cle)){
					if(cleExists(newname)){
						int pos = posCle(newname);
						list.remove(pos);
					}
					int pos = posCle(cle);
					Cle_valeur tmp = list.get(pos);
					tmp.setCle(newname);
					reussi = 1;
				}
				else{
				 throw new KeyNotExistsException();	
				}				
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant de renommer une clﾃｩ enregistrﾃｩe si le nouveau nom n'existe pas
	 * @param cle la clﾃｩ que l'on veut renommer
	 * @param newname le nouveau nom pour la clﾃｩ
	 * @return 1 si le renommage ﾃ� rﾃｩussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clﾃｩ sont identiques
	 * @throws KeyNotExistsException si la clﾃｩ n'existe pas
	 */
	public int renamenx(String cle, String newname) throws SameNameException, KeyNotExistsException {
		int reussi = 0;
		if(!(cle == null || newname == null)){
			if (cle.equals(newname)){
				throw new SameNameException();
			}
			else{
				if(cleExists(cle)){
					if(!cleExists(newname)){
						int pos = posCle(cle);
						Cle_valeur tmp = list.get(pos);
						tmp.setCle(newname);
						reussi = 1;
					}
				}
				else{
				 throw new KeyNotExistsException();	
				}				
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant d'ajouter une ou plusieurs valeurs ﾃ� la droite (fin) de la liste associﾃｩe ﾃ� une clﾃｩ.
	 * Si la clﾃｩ existait alors il faut qu'elle soit dﾃｩjﾃ� associﾃｩe ﾃ� une liste de valeur.
	 * Si la clﾃｩ n'existait pas alors une nouvelle clﾃｩe est ajoutﾃｩe, et elle est associﾃｩe aux valeurs donnﾃｩe en paramﾃｨtres
	 * @param cle la clﾃｩ ﾃ� qui ont veut associer les valeurs
	 * @param listVal l'ensemble des valeurs que l'on veut associer ﾃ� la clﾃｩ
	 * @return la taille de la liste associﾃｩe ﾃ� la clﾃｩ
	 * @throws WrongTypeValueException si la clﾃｩ existait est n'ﾃｩtait pas associﾃｩe ﾃ� une liste
	 */
	public int rpush(String cle, ArrayList<String> listVal)throws WrongTypeValueException{
		int taille = -1;
		if(cle != null && listVal != null){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					for(int i = 0; i < listVal.size(); i++ ){
						String val = listVal.get(i);
						tmp.add(val);
					}
					taille = tmp.size();
				}
				else{
					throw new WrongTypeValueException();
				}
			}
			else{
				Cle_valeur<ArrayList<String>> couple = new Cle_valeur<ArrayList<String>>(cle, listVal);
				list.add(couple);
				taille = listVal.size(); 
			}
		}
		return taille;
	}
	
	
	/**
	 * Fonction permettant d'ajouter une valeur à la droite (fin) de la liste associée à une clé.
	 * La clé doit exister
	 * @param cle la clé à qui ont veut associer la valeur
	 * @param listVal l'ensemble des valeurs que l'on veut associer à la clé
	 * @return la taille de la liste associée à la clé
	 * @throws WrongTypeValueException si la clé existait est n'était pas associée à une liste
	 */
	public int rpushx(String cle, ArrayList<String> listVal)throws WrongTypeValueException{
		int taille = -1;
		if(cle != null && listVal != null){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					for(int i = 0; i < listVal.size(); i++ ){
						String val = listVal.get(i);
						tmp.add(val);
					}
					taille = tmp.size();
				}
				else{
					throw new WrongTypeValueException();
				}
			}
			else{
				Cle_valeur<ArrayList<String>> couple = new Cle_valeur<ArrayList<String>>(cle, listVal);
				list.add(couple);
				taille = listVal.size(); 
			}
		}
		return taille;
	}
	
	
	
	/**
	 * Fonction permettant d'ajouter une ou plusieurs valeurs ﾃ� la gauche (au dﾃｩbut) de la liste associﾃｩe ﾃ� une clﾃｩ.
	 * Si la clﾃｩ existait alors il faut qu'elle soit dﾃｩjﾃ� associﾃｩe ﾃ� une liste de valeur.
	 * Si la clﾃｩ n'existait pas alors une nouvelle clﾃｩe est ajoutﾃｩe, et elle est associﾃｩe aux valeurs donnﾃｩe en paramﾃｨtres
	 * @param cle la clﾃｩ ﾃ� qui ont veut associer les valeurs
	 * @param listVal l'ensemble des valeurs que l'on veut associer ﾃ� la clﾃｩ
	 * @return la taille de la liste associﾃｩe ﾃ� la clﾃｩ
	 * @throws WrongTypeValueException si la clﾃｩ existait est n'ﾃｩtait pas associﾃｩe ﾃ� une liste
	 */
	public int lpush(String cle, ArrayList<String> listVal)throws WrongTypeValueException{
		int taille = -1;
		if(cle != null && listVal != null){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					for(int i = 0; i < listVal.size(); i++ ){
						String val = listVal.get(i);
						tmp.add(0,val);
					}
					taille = tmp.size();
				}
				else{
					throw new WrongTypeValueException();
				}
			}
			else{
				Cle_valeur<ArrayList<String>> couple = new Cle_valeur<ArrayList<String>>(cle, listVal);
				list.add(couple);
				taille = listVal.size(); 
			}
		}
		return taille;
	}
	
	/**
	 * Fonction permettant de retirer et de rﾃｩcupﾃｩrer le premier ﾃｩlﾃｩment d'une liste de valeur qui ﾃｩtait associﾃｩe ﾃ� une clﾃｩ
	 * Si la clﾃｩ existe elle doit ﾃｪtre associﾃｩe ﾃ� une liste.
	 * Si la clﾃｩ n'existe pas la fonction retourne null 
	 * @param cle la clﾃｩ qui est associﾃｩ ﾃ� la liste dont on veut rﾃｩcupﾃｩrer, et retirer, le premier ﾃｩlﾃｩment
	 * @return le premier ﾃｩlﾃｩment de la liste associﾃｩ ﾃ� la clﾃｩ
	 * @throws WrongTypeValueException si la clﾃｩ n'est n'est pas associﾃｩ ﾃ� une liste de valeur
	 */
	public String lpop(String cle) throws WrongTypeValueException{
		String premier_element = null;
		if(cle != null){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> liste_valeur = (ArrayList) list.get(pos).getValeur();
					premier_element = liste_valeur.remove(0);
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return premier_element;
	}
	
	/**
	 * Fonction permettant de retirer et de rﾃｩcupﾃｩrer le dernier ﾃｩlﾃｩment d'une liste de valeur qui ﾃｩtait associﾃｩe ﾃ� une clﾃｩ
	 * Si la clﾃｩ existe elle doit ﾃｪtre associﾃｩe ﾃ� une liste.
	 * Si la clﾃｩ n'existe pas la fonction retourne null 
	 * @param cle la clﾃｩ qui est associﾃｩ ﾃ� la liste dont on veut rﾃｩcupﾃｩrer, et retirer, le dernier ﾃｩlﾃｩment
	 * @return le dernier ﾃｩlﾃｩment de la liste associﾃｩ ﾃ� la clﾃｩ
	 * @throws WrongTypeValueException si la clﾃｩ n'est n'est pas associﾃｩ ﾃ� une liste de valeur
	 */
	public String rpop(String cle) throws WrongTypeValueException{
		String premier_element = null;
		if(cle != null){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> liste_valeur = (ArrayList) list.get(pos).getValeur();
					premier_element = liste_valeur.remove(liste_valeur.size()-1);
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return premier_element;
	}
	
	
	
	
	/**
	 * Fonction permettant de vﾃｩrifier si une clﾃｩ ﾃ� dﾃｩja ﾃｩtﾃｩ enregistrﾃｩe
	 * @param c la clﾃｩ que l'on veut vﾃｩrifier l'enregistrement
	 * @return true si la clﾃｩ ﾃ� dﾃｩja ﾃｩtﾃｩ enregistrﾃｩe, false sinon
	 */
	private Boolean cleExists(String c){
		Boolean result = false;
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				Cle_valeur couple = list.get(i);
				if(couple.getCle().equals(c)) result = true;
			}
		}
	
		return result;
	}
	
	
	
	/**
	 * Fonction permettant de rﾃｩcupﾃｩrer la position d'une clﾃｩ dans la liste d'enregistrement
	 * @param c la clﾃｩ dont on veut connaitre la position
	 * @return la position de la clﾃｩ dans la liste d'enregistrement ou -1 si la clﾃｩ n'existe pas dans la liste d'enregistrement.
	 */
	private int posCle(String c){
		int pos = -1;
		Boolean find = false;
		if(list != null){
			int i = 0;
			while (i < list.size() && !find){
				Cle_valeur couple = list.get(i);
				if(couple.getCle().equals(c)) find = true;
				else i++;
			}
			pos = i;
		}
		return pos;
	}
	
	
}
