package dcc;

import java.util.Date;
import java.sql.Timestamp;

public class Block implements Comparable<Block>{
	private int index;
	private String previousHash;
	private String timestamp;
	private int nbTransactions;
	private String transactions[];
	private String merkleRoot;
	private String blockHash;
	private int nonce = 0;
	public static final int MAX_TRANSACTIONS_VALUE = 100;
	public static final int MAX_TRANSACTIONS_AMOUNT = 10;

	/**
	 * Constructor
	 */
	public Block (int i, String hash_prev, int difficulty, String [] ts){
		index = i;
		previousHash = hash_prev;
		timestamp = new Timestamp(new Date().getTime()).toString();
		transactions = ts;
		nbTransactions = transactions.length;
		merkleRoot = generateMerkleRoot(transactions);
		generateNonce(difficulty);
		blockHash = getHash();
	}

	public static String generateMerkleRoot(String ts []) {
		if(ts.length == 1) return Utility.sha256(ts[0]);
		if (ts.length == 2) return Utility.sha256(Utility.sha256(ts[0]) + Utility.sha256(ts[1]));
			String t1 [] = new String [ts.length];
		int i = 0;
		for (; i < ts.length; i++)
			t1[i] = Utility.sha256(ts[i]);
		String t2 [] = new String [(ts.length + 1)/2];
		while (t2.length > 1) {
			t2 = new String [(t1.length + 1)/2];
			for (i = 0; i < t1.length; i += 2) {
				t2[i/2] = ((i + 1) == t1.length) ? Utility.sha256(t1[i] + t1[i]) : Utility.sha256(t1[i] + t1[i + 1]);
			}
			t1 = t2;
		}
		return t2[0];
	}

	public String getHash () {
		return Utility.sha256(previousHash + timestamp + merkleRoot + nonce);
	}

	public int getIndex() {
		return index;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public int getNbTransactions() {
		return nbTransactions;
	}

	public String[] getTransactions() {
		return transactions;
	}

	public String getMerkleRoot() {
		return merkleRoot;
	}

	public String getBlockHash() {
		return blockHash;
	}

	public int getNonce() {
		return nonce;
	}

	private void generateNonce(int difficulty) {
		String t = this.getHash();
		String str_difficulty = "";
		for (int i = 0; i < difficulty; i++) str_difficulty += "0";
		while (!t.substring(0, difficulty).equals(str_difficulty)){
			this.nonce++;
			t = this.getHash();
		}
	}

	public String toString (){
		return "Hash du block precedent : " + previousHash + "\n"
				+ "Timestamp : " + timestamp + "\n"
				+ "Nbr de transactions : " + nbTransactions + "\n"
				+ "Hash de l'arbre de merkle : " + merkleRoot + "\n"
				+ "Hash du block : " + blockHash + "\n"
				+ "Nonce : " + nonce + "\n";
	}

	@Override
	public int compareTo(Block o) {
		return index - o.getIndex();
	}

}