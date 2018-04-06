package dcc;

public class Main {
	public static void main(String[] args) {
		Transaction t = new Transaction();
		System.out.println(t.toString());
	}
	
	/**
	 * Lire ?
	 * 		Oui
	 * 			GetFileName
	 * 		Non
	 * 			filename ?
	 * 			nbrBlock ?
	 * 			difficukty ?
	 * 			nbrmaxtransaction ?
	 * 			display exec time
	 * 			save2json
	 * 		checkBC
	 * 		"success" / "error"
	 * 		display exec time
	 * Tout afficher ?
	 * 		oui : display(my_bc)
	 * 		non : quel block afficher ? (entre 1 et ...)
	 * 			display(my_bc, int)
	 */
}
