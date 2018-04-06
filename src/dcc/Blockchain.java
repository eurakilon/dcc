package dcc;

public class Blockchain {
	private int difficulty;
	private int nb_transactions_max;
	private int nb_blocks;
	private Block blocks[];

	public Blockchain (int nb_transactions_max, int difficulty, int nbr_blocks) {
		this.blocks = new Block [nbr_blocks];
		this.difficulty = difficulty;
		this.blocks[0] = new Block("0",
				new Transaction [] {new Transaction("Genesis")},
				this.difficulty);
		this.nb_blocks = 1;
		this.nb_transactions_max = nb_transactions_max;
	}

	public void addBlock(Block blocks[]){
		Transaction transactions [] = new Transaction [Utility.rdm(this.nb_transactions_max)];
		this.blocks[this.nb_blocks] = new Block(this.blocks[this.nb_blocks - 1].getHash(), transactions, this.difficulty);
		this.nb_blocks++;
	}
	
	public String toString (){
		String t = "";
		for (int i = 0; i < this.nb_blocks; i++) {
			t += "\n" + this.blocks[i].toString();
		}
		return t;
	}

	public void checkBlockchain(){
		// etape 1 : verifier le chainage des hashs
		// etape 2 : verifier tous les arbres de markle
		for (int i=0; i < this.nb_blocks; i++);
	}

}
