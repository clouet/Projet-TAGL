package tagl;

import java.util.ArrayList;

public class Gestion_cle_valeur {
	/**
	 * La liste de clé_valeur ne peut contenir que 10 entrées
	 */
	public static final int TAILLE_MAX = 10;
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
		if(!(cle == null || valeur == null || cle.equals(""))){
			// si la clé a déja été enregistrée avant
			if(cleExists(cle)){
				// on récupère sa position dans la liste
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				// on modifie la valeur associée a cette clé dans la liste
				tmp.setValeur(valeur);
				// on met a jour la liste
				list.remove(pos);
				list.add(tmp);
				reussi = 1;
			}
			// sinon
			else{
				if(list.size() == TAILLE_MAX){
					list.remove(0);
				}
				// on crée un nouveau couple (clé, valeur) et on l'ajoute a la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant d'enregistrer une clé associée a une valeur. 
	 * La clé ne doit pas avoir déja été enregistrée.
	 * @param cle le nom de la clé
	 * @param valeur la valeur a associer a la clé
	 * @return 1 si l'enregistrement a pu être fait, 0 sinon
	 */
	public int setnx(String cle, String valeur){
		int reussi = 0;
		if(!(cle == null || valeur == null || cle.equals(""))){
			// si la clé a déja été enregistrée avant
			if(!cleExists(cle)){
				if(list.size() == TAILLE_MAX){
					list.remove(0);
				}
				// on crée un nouveau couple (clé, valeur) et on l'ajoute a la liste
				Cle_valeur tmp = new Cle_valeur(cle, valeur);
				list.add(tmp);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de récupérer la valeur associée a une clé
	 * La valeur doit être un String
	 * @param cle le nom de la clé dont on veut récupérer la valeur associée
	 * @return la valeur associée a la clé, si la clé n'existe pas retourne null
	 * @throws WrongTypeValueException si la valeur associée a la clé n'est pas une String
	 */
	public String get(String cle) throws WrongTypeValueException{
		String valeur = null;
		if(!(cle == null || cle.equals(""))){
			if(cleExists(cle)){
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				if(tmp.getValeur() instanceof String){
					valeur = String.valueOf(tmp.getValeur());
				}
				else{
					throw new WrongTypeValueException();
				}
				list.remove(pos);
				list.add(tmp);
			}
		}
		return valeur;
	}
	
	
	/**
	 * Fonction permettant d'enlever un couple (clé,valeur) de la liste
	 * @param cle la clé dont on veut enlever le couple
	 * @return 1 si le couple a bien été enlevé, 0 sinon
	 */
	public int del(String cle){
		int reussi = 0;
		if(!(cle == null || cle.equals(""))){
			if(cleExists(cle)){
				int pos = posCle(cle);
				list.remove(pos);
				reussi = 1;
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant d'incrémenter la valeur associée a une clé si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur 1.
	 * @param cle la clé dont on veut d'incrémenter la valeur
	 * @return la nouvelle valeur associée a la clé
	 * @throws NumberFormatException si la valeur associée a la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée a la clé ne peut pas être augmentée de 1 sans franchir la valeur maximum pour Integer
	 */
	public int incr(String cle) throws NumberFormatException, OverFlowException{
		int reussi = 0;
		if(!(cle == null || cle.equals(""))){
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
							list.remove(pos);
							list.add(cv);
						}catch(NumberFormatException n){
							throw n;
						}
				}
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant d'augmenter la valeur associée a une clé d'un certain entier, si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur l'entier donné.
	 * @param cle la clé dont on veut augmenter la valeur
	 * @param i l'entier duquel on veut augmenter la valeur associée a la clé
	 * @return la nouvelle valeur associée a la clé
	 * @throws NumberFormatException si la valeur associée a la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée a la clé ne peut pas être augmentée de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associée a la clé ne peut pas être augmentée de i (i étant négatif)
	 */
	public int incrBy(String cle, int i) throws NumberFormatException, OverFlowException, UnderFlowException{
		int reussi = 0;
		if(!(cle == null || cle.equals(""))){
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
						list.remove(pos);
						list.add(cv);
					}catch(NumberFormatException n){
						throw n;
					}
				}
			}
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de décrémenter la valeur associée a une clé si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur -1.
	 * @param cle la clé dont on veut décrémenter la valeur
	 * @return la nouvelle valeur associée a la clé, ou lève une erreur si la valeur associée a la clé n'est pas un entier
	 * @throws NumberFormatException si la valeur associée a la clé n'est pas un entier acceptable
	 * @throws UnderFlowException si la valeur associée a la clé ne peut pas être augmentée de i (i étant négatif)
	 */
	public int decr(String cle) throws NumberFormatException, UnderFlowException{
		int reussi = 0;
		if(!(cle == null || cle.equals(""))){
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
						list.remove(pos);
						list.add(cv);
					}catch(NumberFormatException n){
						throw n;
					}
				}
			}
		}
		return reussi;
	}
	
	/**
	 * Fonction permettant de diminuer la valeur associée a une clé  si cette valeur est un entier.
	 * Si la clé n'exsiste pas, un nouveau couple (clé,valeur) est crée, avec comme valeur -i.
	 * @param cle la clé dont on veut décrémenter la valeur
	 * @param i l'entier duquel on veut diminuer la valeur associée a la clé
	 * @return la nouvelle valeur associée a la clé, ou lève une erreur si la valeur associée a la clé n'est pas un entier
	 * @throws NumberFormatException si la valeur associée a la clé n'est pas un entier acceptable
	 * @throws OverFlowException si la valeur associée a la clé ne peut pas être augmentée de i sans franchir la valeur maximum pour Integer
	 * @throws UnderFlowException si la valeur associée a la clé ne peut pas être augmentée de i (i étant négatif)
	 */
	public int decrBy(String cle, int i) throws NumberFormatException, OverFlowException, UnderFlowException{
		int reussi = 0;
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
		if(!(cle == null || cle.equals(""))){
			if(cleExists(cle)){
				exist = 1;
				int pos = posCle(cle);
				Cle_valeur tmp = list.get(pos);
				list.remove(pos);
				list.add(tmp);
			}
		}
		return exist;
	}
	
	
	/**
	 * Fonction permettant de renommer une cle enregistrée
	 * @param cle la clé que l'on veut renommer
	 * @param newname le nouveau nom pour la clé
	 * @return 1 si le renommage a réussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clé sont identiques
	 * @throws KeyNotExistsException si la clé n'existe pas
	 */
	public int rename(String cle, String newname) throws SameNameException, KeyNotExistsException {
		int reussi = 0;
		if(!(cle == null || newname == null || cle.equals("") || newname.equals(""))){
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
					list.remove(pos);
					list.add(tmp);
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
	 * @return 1 si le renommage a réussi, 0 sinon
	 * @throws SameNameException si l'ancien nom et le nouveau nom de la clé sont identiques
	 * @throws KeyNotExistsException si la clé n'existe pas
	 */
	public int renamenx(String cle, String newname) throws SameNameException, KeyNotExistsException {
		int reussi = 0;
		if(!(cle == null || newname == null || cle.equals("") || newname.equals(""))){
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
						list.remove(pos);
						list.add(tmp);
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
	 * @throws WrongTypeValueException si la clé existait et n'était pas associée à une liste
	 */
	public int rpush(String cle, ArrayList<String> listVal)throws WrongTypeValueException{
		int taille = 0;
		if(cle != null && listVal != null && !cle.equals("")){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					for(int i = 0; i < listVal.size(); i++ ){
						String val = listVal.get(i);
						tmp.add(val);
					}
					taille = tmp.size();
					Cle_valeur cv = list.get(pos);
					list.remove(pos);
					list.add(cv);
				}
				else{
					throw new WrongTypeValueException();
				}
			}
			else{
				if(list.size() == TAILLE_MAX){
					list.remove(0);
				}
				Cle_valeur<ArrayList<String>> couple = new Cle_valeur<ArrayList<String>>(cle, listVal);
				list.add(couple);
				taille = listVal.size(); 
			}
		}
		return taille;
	}
	
	
	/**
	 * Fonction permettant d'ajouter une valeur à la droite (fin) de la liste associée à une clé.
	 * La clé doit exister.
	 * @param cle la clé à qui ont veut associer la valeur
	 * @param val la valeur que l'on veut associer à la clé
	 * @return la taille de la liste associée à la clé, 0 si la clé n'existait pas
	 * @throws WrongTypeValueException si la clé n'était pas associée à une liste
	 */
	public int rpushx(String cle, String val)throws WrongTypeValueException{
		int taille = 0;
		if(cle != null && val != null && !cle.equals("") ){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					tmp.add(val);
					taille = tmp.size();
					Cle_valeur cv = list.get(pos);
					list.remove(pos);
					list.add(cv);
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return taille;
	}
	
	
	
	/**
	 * Fonction permettant d'ajouter une ou plusieurs valeurs à la gauche (au début) de la liste associée à une clé.
	 * Si la clé existait alors il faut qu'elle soit déjà associée à une liste de valeur.
	 * Si la clé n'existait pas alors une nouvelle clée est ajoutée, et elle est associée aux valeurs donnée en paramètres
	 * @param cle la clé à qui ont veut associer les valeurs
	 * @param listVal l'ensemble des valeurs que l'on veut associer à la clé
	 * @return la taille de la liste associée à la clé
	 * @throws WrongTypeValueException si la clé existait et n'était pas associée à une liste
	 */
	public int lpush(String cle, ArrayList<String> listVal)throws WrongTypeValueException{
		int taille = 0;
		if(cle != null && listVal != null && !cle.equals("")){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					if(tmp != null){
						for(int i = 0; i < tmp.size(); i++ ){
							String val = tmp.get(i);
							listVal.add(val);
						}
						list.get(pos).setValeur(listVal);
						taille = listVal.size();
						Cle_valeur cv = list.get(pos);
						list.remove(pos);
						list.add(cv);
					}
				}
				else{
					throw new WrongTypeValueException();
				}
			}
			else{
				if(list.size() == TAILLE_MAX){
					list.remove(0);
				}
				Cle_valeur<ArrayList<String>> couple = new Cle_valeur<ArrayList<String>>(cle, listVal);
				list.add(couple);
				taille = listVal.size(); 
			}
		}
		return taille;
	}
	
	
	
	/**
	 * Fonction permettant d'ajouter une valeur à la gauche (au début) de la liste associée à une clé.
	 * La clé doit exister
	 * @param cle la clé à qui ont veut associer les valeurs
	 * @param val la valeur que l'on veut associer à la clé
	 * @return la taille de la liste associée à la clé, 0 si la clé n'existait pas
	 * @throws WrongTypeValueException si la clé existait et n'était pas associée à une liste
	 */
	public int lpushx(String cle, String val)throws WrongTypeValueException{
		int taille = 0;
		if(cle != null && val != null && !cle.equals("")){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> tmp = (ArrayList) list.get(pos).getValeur();
					if(tmp != null){
						tmp.add(0, val);
						taille = tmp.size();
						Cle_valeur cv = list.get(pos);
						list.remove(pos);
						list.add(cv);
					}
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return taille;
	}
	
	
	/**
	 * Fonction permettant de retirer et de récupérer le premier élément d'une liste de valeur qui était associée a une clé
	 * Si la clé existe elle doit être associée a une liste.
	 * Si la clé n'existe pas la fonction retourne null 
	 * Si il n'y a pas d'élément dans la liste associée à la clé, la fonction retourne null
	 * @param cle la clé qui est associé a la liste dont on veut récupérer, et retirer, le premier élément
	 * @return le premier élément de la liste associé a la clé
	 * @throws WrongTypeValueException si la clé n'est n'est pas associé a une liste de valeur
	 */
	public String lpop(String cle) throws WrongTypeValueException{
		String premier_element = null;
		if(cle != null && !cle.equals("")){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> liste_valeur = (ArrayList) list.get(pos).getValeur();
					if(liste_valeur != null && liste_valeur.size() != 0){
						premier_element = liste_valeur.remove(0);
						Cle_valeur cv = list.get(pos);
						list.remove(pos);
						list.add(cv);
					}
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return premier_element;
	}
	
	/**
	 * Fonction permettant de retirer et de récupérer le dernier élément d'une liste de valeur qui était associée a une clé
	 * Si la clé existe elle doit être associée a une liste.
	 * Si la clé n'existe pas la fonction retourne null 
	 * Si il n'y a pas d'élément dans la liste associée à la clé, la fonction retourne null
	 * @param cle la clé qui est associé a la liste dont on veut récupérer, et retirer, le dernier élément
	 * @return le dernier élément de la liste associé a la clé
	 * @throws WrongTypeValueException si la clé n'est n'est pas associé a une liste de valeur
	 */
	public String rpop(String cle) throws WrongTypeValueException{
		String premier_element = null;
		if(cle != null && !cle.equals("")){
			if(cleExists(cle)){
				int pos = posCle(cle);
				if(list.get(pos).getValeur() instanceof ArrayList){
					ArrayList<String> liste_valeur = (ArrayList) list.get(pos).getValeur();
					if(liste_valeur != null && liste_valeur.size() != 0){
						premier_element = liste_valeur.remove(liste_valeur.size()-1);
						Cle_valeur cv = list.get(pos);
						list.remove(pos);
						list.add(cv);
					}
				}
				else{
					throw new WrongTypeValueException();
				}
			}
		}
		return premier_element;
	}
	
	/**
     * Fonction permettant d'enregistrer une clé associée à un ensemble de valeur; ces valeurs étant des String avec un score. 
	 * Si la clé existait avant, l'ensemble des valeur est remplacé par le nouvel ensemble
	 * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @param valeurs l'ensemble des valeurs que l'on veut associer à la clé
	 * @return le nombre d'elements ajoutes
	 */
	public int zAdd(String cle, ArrayList<ListScore> valeurs){
		int reussi = 0;
		if(cle !=null && valeurs != null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							list.remove(pos);
							Collections.sort(valeurs);
							Cle_valeur<ArrayList<ListScore>> couple = new Cle_valeur<ArrayList<ListScore>> (cle, valeurs);
							list.add(couple);
							reussi = valeurs.size();
						}						
					}
				}
				else{
					if(list.size() == TAILLE_MAX){
						list.remove(0);
					}
					Collections.sort(valeurs);
					Cle_valeur<ArrayList<ListScore>> couple = new Cle_valeur<ArrayList<ListScore>> (cle, valeurs);
					list.add(couple);
					reussi = valeurs.size();
				}
			}
		}
		return reussi;
	}
	

	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	/**
     * Fonction permettant de récupérer les valeurs associées à une clé
     * Ces valeurs étant des String avec un score. 
     * Les valeurs sont triées par leur score (dans l'ordre décroissant) 	 
	 * @param cle la clé dont on veut associer l'ensemble de valeur
	 * @return les valeurs associées à la clé. Juste les Strings dans l'ordre enregistré.
	 */
	public ArrayList<String> zGet(String cle){
		ArrayList<String> res = null;
		if(cle !=null){
			if(!cle.equals("")){
				if(cleExists(cle)){
					int pos = posCle(cle);
					if(list.get(pos).getValeur() instanceof ArrayList){
						ArrayList<Object> list_val = (ArrayList)list.get(pos).getValeur();
						if(list_val.get(0) instanceof ListScore){
							res = new ArrayList<>();
							Cle_valeur<ArrayList<ListScore>> couple = list.get(pos);					
							list.remove(pos);
							list.add(couple);
							for(int i = 0; i < couple.getValeur().size(); i++){
								res.add(couple.getValeur().get(i).getString());
							}
						}						
					}
				}
			}
		}
		return res;
	}
	
	
	
	
	
	/**
	 * Fonction permettant de vérifier si une clé a déja été enregistrée
	 * @param c la clé que l'on veut vérifier l'enregistrement
	 * @return true si la clé a déja été enregistrée, false sinon
	 */
	private Boolean cleExists(String c){
		Boolean result = false;
		if(list != null && !c.equals("") && c != null){
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
		if(list != null && !c.equals("") && c != null){
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
