package duckcoincoin;

import java.security.MessageDigest;
import java.util.Random;

public class Utility {
	/**
	 * @param input
	 * @return hash de input
	 */
	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	/**
	 * Random ("Aléaoire" en anglais)
	 * @param borne supérieure
	 * @return nombre aléatoire inférieur à la borne supérieur donnée en paramètre
	 */
	public static int rdm (int i) {
		Random rand = new Random();
		return rand.nextInt(i);
	}
}
