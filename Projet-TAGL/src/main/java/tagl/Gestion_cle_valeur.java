package tagl;

import java.util.ArrayList;

public class Gestion_cle_valeur {
	private ArrayList<Cle_valeur> list;
	
	
	/**
	 * Fonction permettant d'enregistrer une clé associée à une valeur. 
	 * Si la clé existait avant, la valeur est mise à jour
	 * @param cle le nom de la clé
	 * @param valeur la valeur à associer à la clé
	 * @return 1 si l'enregistrement à pu être fait, 0 sinon
	 */
	public int set(String cle, String valeur){
		int reussi = 0;
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
		return reussi;
	}
	
	
	/**
	 * Fonction permettant d'enregistrer une clé associée à une valeur. 
	 * La clé ne doit pas avoir déja été enregistrée.
	 * @param cle le nom de la clé
	 * @param valeur la valeur à associer à la clé
	 * @return 1 si l'enregistrement à pu être fait, 0 sinon
	 */
	public int setnx(String cle, String valeur){
		int reussi = 0;
		// si la clé à déjà été enregistrée avant
		if(!cleExists(cle)){
			// on crée un nouveau couple (clé, valeur) et on l'ajoute à la liste
			Cle_valeur tmp = new Cle_valeur(cle, valeur);
			list.add(tmp);
			reussi = 1;
		}
		return reussi;
	}
	
	
	/**
	 * Fonction permettant de récupérer la valeur qui est associé à une clé
	 * @param cle le nom de la clé dont on veut récupérer la valeur qui lui est associée
	 * @return la valeur associée à la clé
	 */
	public String get(String cle){
		String valeur = null;
		if(cleExists(cle)){
			int pos = posCle(cle);
			Cle_valeur tmp = list.get(pos);
			valeur = tmp.getValeur();
		}
		return valeur;
	}
	
	
	/**
	 * Fonction permettant d'enlever un couple (clé,valeur) de la liste
	 * @param cle la clé dont on veut enlever l'enregistrement
	 * @return 1 si le couple à bien été enlevé, 0 sinon
	 */
	public int del(String cle){
		int reussi = 0;
		if(cleExists(cle)){
			int pos = posCle(cle);
			list.remove(pos);
			reussi = 1;
		}
		return reussi;
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
