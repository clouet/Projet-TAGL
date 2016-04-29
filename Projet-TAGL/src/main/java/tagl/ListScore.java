package tagl;

public class ListScore implements Comparable {
	private int score;
	private String valeur;
	
	public ListScore(int score, String valeur) {
		super();
		this.score = score;
		this.valeur = valeur;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getString() {
		return valeur;
	}
	public void setString(String valeur) {
		this.valeur = valeur;
	}
	@Override
	public int compareTo(Object arg0) {
		int res = 0;
		// TODO Auto-generated method stub
		ListScore tmp = (ListScore)arg0;
		if(tmp.getScore() < score) res =-1;
		if(tmp.getScore() > score) res = 1;
		
		return 0;
	}
	

}
