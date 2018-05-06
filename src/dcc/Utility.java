package dcc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public static String file2string (File file) throws IOException, FileNotFoundException {
		String list = "";
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String text = null;

		while ((text = reader.readLine()) != null)
			list += text;
		reader.close();

		return list;
	}
	
	public static void string2file(String txt, File selectedFile) throws IOException {
		FileWriter fw = null;
		fw = new FileWriter(selectedFile);
		fw.write(txt);
		fw.close();
	}
}
