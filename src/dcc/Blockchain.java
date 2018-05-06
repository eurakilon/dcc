package dcc;

import java.util.Arrays;

public class Blockchain {
	private int difficulty;
	private int nbBlocks;
	private Block BC[];
	// Codes d'erreur de checkBlockchain()
	public static final int BC_VALIDE = 0;
	public static final int GENESIS_CORROMPU = 1;
	public static final int CHAINAGE_CORROMPU = 2;
	public static final int MERKLE_CORROMPU = 3;
	public static final int HASH_CORROMPU = 4;

	public static void main(String[] args) {
		Blockchain t = new Blockchain(4, 10);
		System.out.println(t);
		System.out.println(t.checkBlockchain()[0]);
	}

	public Blockchain (int bc_difficulty, int nbr_blocks) {
		BC = new Block [nbr_blocks];
		difficulty = bc_difficulty;
		BC[0] = new Block(0, "0", difficulty);
		nbBlocks = 1;
		for (int i = 1; i < nbr_blocks; i++)
			addBlock();
	}

	public void addBlock(){
		BC[nbBlocks] = new Block(nbBlocks, BC[nbBlocks - 1].getHash(), difficulty);
		nbBlocks++;
	}

	public String toString (){
		String t = "";
		for (int i = 0; i < nbBlocks; i++) {
			t += "------------------\nBlock " + (i + 1) + " / " + nbBlocks + "\n" + BC[i].toString();
		}
		return t;
	}

	public int[] checkBlockchain(){
		/* etape 1 :
		 * - verifier que le premier block un block genesis valide,
		 * - vérifier que le hash des blocks est réel
		 * - vérifier le chainage des BC
		 */
		int i = 1;
		Block tmp = BC[0];
		// Vérifications sur le block genesis
		if (!(tmp.getPreviousHash().equals("0")
				// && tmp.getTransactions()[0].equals("Genesis")
				&& tmp.getTransactions().length == 1
				&& tmp.getNonce() == 0))
			return new int[] {GENESIS_CORROMPU, 0};
		for (; i < nbBlocks; i++) {
			// Vérification du hash des blocks
			if (!tmp.getBlockHash().equals(tmp.getHash()))
				return new int[] {HASH_CORROMPU, i};
			// Vérification du chainage des BC
			else if (!tmp.getHash().equals(BC[i].getPreviousHash()))
				return new int[] {CHAINAGE_CORROMPU, i};
			tmp = BC[i];
		}
		// etape 2 : verifier les arbres de Merkle de tous les BC
		for (i = 0; i < nbBlocks; i++)
			if (!BC[i].getMerkleRoot().equals(Block.generateMerkleRoot(BC[i].getTransactions())))
				return new int[] {MERKLE_CORROMPU, i};
		return new int[] {BC_VALIDE, 0};
	}
	
	public void sortBC () {
		Arrays.sort(this.BC);
	}
}
