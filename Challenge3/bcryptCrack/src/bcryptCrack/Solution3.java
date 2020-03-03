package bcryptCrack;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.xml.bind.DatatypeConverter;

public class Solution3 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		HashMap<String,String> hashSalt = hash();
		
		HashMap<String,Integer> passwords = dict(hashSalt);
		
		writeFile(passwords);

	}
	
	public static HashMap<String,String> hash() {

		BufferedReader reader;
		HashMap<String,String> file = new HashMap<String,String>();
		try {
			reader = new BufferedReader(new FileReader("../rockyou-samples.bcrypt.txt"));
			String line = reader.readLine();
			file.put(line.substring(18,line.length()),line.substring(7,17));

			while (line != null) {
				line = reader.readLine();
				if(line==null) break;
				file.put(line.substring(18,line.length()),line.substring(7,17));
			}
			
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	public static HashMap<String,Integer> dict(HashMap<String,String> hashSalt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		
		HashMap<String,Integer> commonPasswords = new HashMap<String,Integer>();
		
		return commonPasswords;
	}
	
	public static void writeFile(HashMap<String,Integer> passwords) throws IOException {
		PrintWriter writer = new PrintWriter("bcrypt-lines.txt");
		for(String s : passwords.keySet()) {
			writer.println(passwords.get(s) + "," + s);
		}
		writer.close();
	}

}