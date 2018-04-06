package duckcoincoin;

public class Blockchain {
	private int difficulty;
	private int nb_transactions_max;
	private int nb_blocks;
	private Block blocks[];
	
	public Blockchain(){
		public void addBlock(Block blocks[]){
			for (int i=0; i<nb_blocks;i++)
				this.blocks[i] = new Block(this.blocks[i-1], this.generateTransaction(),this.difficulty);
		}
		public void checkBlockchain()
		// etape 1 : vérifier le chainage des hashs
		// etape 2 : vérifier tous les arbres de markle
	}

}
