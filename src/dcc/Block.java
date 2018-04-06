package dcc;

import java.util.Date;

public class Block {
	private String hash_prev;
	private Date timestamp;

	private String hash_merkle; 
	private int nonce;
	private Transaction transactions[];
	private String hash_block;

	/**
	 * Constructor
	 */
	public Block (String hash_prev, Transaction transactions[], int difficulty){
		this.hash_prev = hash_prev;
		this.timestamp = new Date ();
		this.hash_merkle = this.generate_merkle(this.transactions);
		this.nonce = generateNonce(difficulty);
		this.hash_block = Utility.sha256(this.hash_prev + this.timestamp.toString() + this.hash_merkle + this.nonce);

	}

	private String generate_merkle(Transaction ts []) {
		String t [] = new String [0];
		System.out.println(ts[0]);
		for (int i = 0; i < ts.length; i++) t[i] = ts[i].getHash();
		return merkle_aux(t);
	}

	private String merkle_aux (String tab []){
		if (tab.length == 0) {
			return null;
		} else if (tab.length == 1) {
			return Utility.sha256(tab[0] + tab[0]);
		} else if (tab.length == 2) {
			return Utility.sha256(tab[0] + tab[1]);
		}
		String tab_aux [] = new String [0];
		int pos = 0;
		for (int i = 0; i < tab.length; i+=2) {
			if (i + 1 > tab.length){
				// cas impair duplication
				tab_aux [pos] = Utility.sha256(tab[i] + tab[i]);
				pos++;
			} else {
				// cas normal
				tab_aux [pos] = Utility.sha256(tab[i] + tab[i + 1]);
				pos++;
			}
		}
		return this.merkle_aux(tab);
	}

	public String getHash () {
		return this.hash_block;
	}
	

	private int generateNonce(int difficulty) {
		int nonce = 0;
		String t = Utility.sha256(this.hash_prev + this.timestamp.toString() + this.hash_merkle + nonce);
		String str_difficulty = "";
		for (int i = 0; i < difficulty; i++) str_difficulty += "0";
		while (t.substring(0, 4) == str_difficulty){
			t = Utility.sha256(this.hash_prev + this.timestamp.toString() + this.hash_merkle + nonce);
			nonce++;
		}
		return nonce;
	}
	
	public String toString (){
		return "------------------\n"
				+ "Hash du block : " + this.getHash() + "\n"
				+ "Hash du block precedent : " + this.hash_prev + "\n"
				+ "Timestamp : " + this.timestamp + "\n"
				+ "Nonce : " + this.nonce + "\n"
				+ "Merkle : ...";
	}

}
