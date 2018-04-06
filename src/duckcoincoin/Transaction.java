package dcc;

public class Transaction {
	private int max_value = 1;
	private String content;
	
	/**
	 * Constructeur
	 */
	public Transaction () {
		this.content = Integer.toString(Utility.rdm(100));
	}
	
	public Transaction (String content){
		this.content = content;
	}
	
	/**
	 * @return "Transaction : " + contenu de la transaction
	 */
	public String toString () {
		return "Transaction : " + this.content;
	}
	
	public String getHash (){
		return Utility.sha256(this.toString());
	}
}
