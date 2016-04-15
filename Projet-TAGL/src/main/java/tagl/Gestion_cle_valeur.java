package tagl;

import java.util.ArrayList;

public class Gestion_cle_valeur {
	private ArrayList<Cle_valeur> list;
	
	public Gestion_cle_valeur(){
		if(list == null) list = new ArrayList<>();
	}
	
	/**
	 * Fonction permettant d'enregistrer une clé associée à une valeur. 
	 * Si la clé existait avant, la valeur est mise à jour
	 * @param cle le nom de la clé
	 * @param valeur la valeur à associer à la clé
	 * @return 1 si l'enregistrement a pu être fait, 0 sinon
	 */
	public int set(String cle, String valeur){
		int reussi = 0;
		if(!(cle == null || valeur == null)){
			// si la clé à déjà été enregistrée avant
			if(cleExists(cle)){
				// on récupère sa position dans la liste
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				// on modifie la valeur associée à cette clé dans la liste
				tmp.setValeur(valeur);
				// on met à jour la liste
				list.set(pos, tmp);
				reussi = 1;
			}
			// sinon
			else{
				// on crée un nouveau couple (clé, valeur) et on l'ajoute à la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant d'enregistrer une clé associée à une valeur. 
	 * La clé ne doit pas avoir déja été enregistrée.
	 * @param cle le nom de la clé
	 * @param valeur la valeur à associer à la clé
	 * @return 1 si l'enregistrement a pu être fait, 0 sinon
	 */
	public int setnx(String cle, String valeur){
		int reussi = 0;
		if(!(cle == null || valeur == null)){
			// si la clé à déjà été enregistrée avant
			if(!cleExists(cle)){
				// on crée un nouveau couple (clé, valeur) et on l'ajoute à la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de récupérer la valeur associée à une clé
	 * La valeur doit être un String
	 * @param cle le nom de la clé dont on veut récupérer la valeur associée
	 * @return la valeur associée à la clé
	 * @throws WrongTypeValueException si la valeur associée à la clé n'est pas une String
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
	 * Fonction permettant d'enlever un couple (clé,valeur) de la liste
	 * @param cle la clé dont on veut enlever le couple
	 * @return 1 si le couple à bien été enlevé, 0 sinon
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
	 * Fonction permettant d'incrémenter la valeur associée à une clé si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur 1.
	 * @param cle la clé dont on veut d'incrémenter la valeur
	 * @return la nouvelle valeur associée à la clé
	 * @throws NumberFormatException si la valeur associée à la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée à la clé ne peut pas être augmentée de 1 sans franchir la valeur maximum pour Integer
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
	 * Fonction permettant d'augmenter la valeur associée à une clé d'un certain entier, si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur l'entier donné.
	 * @param cle la clé dont on veut augmenter la valeur
	 * @param i l'entier duquel on veut augmenter la valeur associée à la clé
	 * @return la nouvelle valeur associée à la clé
	 * @throws NumberFormatException si la valeur associée à la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée à la clé ne peut pas être augmentée de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associée à la clé ne peut pas être augmentée de i (i étant négatif)
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
	 * Fonction permettant de décrémenter la valeur associée à une clé si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur -1.
	 * @param cle la clé dont on veut décrémenter la valeur
	 * @return la nouvelle valeur associée à la clé, ou lève une erreur si la valeur associée à la clé n'est pas un entier
	 * @throws NumberFormatException si la valeur associée à la clé n'est pas un entier acceptable
	 * @throws UnderFlowException si la valeur associée à la clé ne peut pas être augmentée de i (i étant négatif)
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
	 * Fonction permettant de diminuer la valeur associée à une clé  si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur -i.
	 * @param cle la clé dont on veut décrémenter la valeur
	 * @param i l'entier duquel on veut diminuer la valeur associée à la clé
	 * @return la nouvelle valeur associée à la clé, ou lève une erreur si la valeur associée à la clé n'est pas un entier
	 * @throws NumberFormatException si la valeur associée à la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée à la clé ne peut pas être augmentée de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associée à la clé ne peut pas être augmentée de i (i étant négatif)
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
	 * Fonction permettant de vérifier si une clé est enregistrée.
	 * @param cle la clé que l'on veut vérifier
	 * @return 1 si la clé est enregistrée, 0 sinon
	 */
	public int exists(String cle){
		int exist = 0;
		if(!(cle == null)){
			if(cleExists(cle)) exist = 1;
		}
		return exist;
	}
	
	
	/**
	 * Fonction permettant de renommer une clé enregistrée
	 * @param cle la clé que l'on veut renommer
	 * @param newname le nouveau nom pour la clé
	 * @return 1 si le renommage à réussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clé sont identiques
	 * @throws KeyNotExistsException si la clé n'existe pas
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
	 * Fonction permettant de renommer une clé enregistrée si le nouveau nom n'existe pas
	 * @param cle la clé que l'on veut renommer
	 * @param newname le nouveau nom pour la clé
	 * @return 1 si le renommage à réussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clé sont identiques
	 * @throws KeyNotExistsException si la clé n'existe pas
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
	 * Fonction permettant d'ajouter une ou plusieurs valeurs à la droite (fin) de la liste associée à une clé.
	 * Si la clé existait alors il faut qu'elle soit déjà associée à une liste de valeur.
	 * Si la clé n'existait pas alors une nouvelle clée est ajoutée, et elle est associée aux valeurs donnée en paramètres
	 * @param cle la clé à qui ont veut associer les valeurs
	 * @param listVal l'ensemble des valeurs que l'on veut associer à la clé
	 * @return la taille de la liste associée à la clé
	 * @throws WrongTypeValueException si la clé existait est n'était pas associée à une liste
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
	 * Fonction permettant de vérifier si une clé à déja été enregistrée
	 * @param c la clé que l'on veut vérifier l'enregistrement
	 * @return true si la clé à déja été enregistrée, false sinon
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
	 * Fonction permettant de récupérer la position d'une clé dans la liste d'enregistrement
	 * @param c la clé dont on veut connaitre la position
	 * @return la position de la clé dans la liste d'enregistrement ou -1 si la clé n'existe pas dans la liste d'enregistrement.
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
