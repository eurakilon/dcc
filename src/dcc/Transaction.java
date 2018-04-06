package dcc;

public class Transaction {
	private int max_value = 1;
	private String content;
	
	/**
	 * Constructeur
	 */
	public Transaction () {
		this.content = Utility.sha256(Integer.toString(Utility.rdm(100)));
	}
	
	/**
	 * @return "Transaction : " + contenu de la transaction
	 */
	public String toString () {
		return "Transaction : " + this.content;
	}
}
