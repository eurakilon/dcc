package duckcoincoin;

import java.util.Date;

public class Block {
	private int nonce;
	private Transaction transactions[];
	private String hash_block;
	private String hash_prev;
	private String hash_merkle; 
	private Date timestamp;
	
	/**
	 * Constructeur
	 */
	public Block (String hash_prev,Transaction transactions[],int difficulty){
		this.hash_prev = hash_prev;
		this.timestamp = timestamp;
		merkle(transactions[]);
		this.nonce = generateNonce(difficulty);
		
	}
}

