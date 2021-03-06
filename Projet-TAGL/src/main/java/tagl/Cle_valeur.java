package tagl;

public class Cle_valeur<T> {
	private String cle;
	private T valeur;
	
	
	/**
	 * Constructeur d'un couple clé-valeur
	 * @param c le nom de la clé
	 * @param v la valeur à associer à la clé
	 */
	public Cle_valeur(String c, T v){
		cle = c;
		valeur = v;
	}

	/**
	 * Fonction permettant de récupérer le nom de la clé
	 * @return le nom de la clé
	 */
	public String getCle() {
		return cle;
	}

	/**
	 * Fonction permettant de modifier le nom de la clé
	 * @param cle le nouveau nom de la clé
	 */
	public void setCle(String cle) {
		this.cle = cle;
	}

	/**
	 * Fonction permettant de récupérer la valeur qui est associé à la clé
	 * @return
	 */
	public T getValeur() {
		return valeur;
	}

	/**
	 * Fonction permettant de modifier la valeur qui est associée à la clé
	 * @param valeur la nouvelle valeur
	 */
	public void setValeur(T valeur) {
		this.valeur = valeur;
	}
	
}
