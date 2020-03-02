package sha1Crack;

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

public class Solution2 {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		HashMap<String,String> hashSalt = hash();
		
		HashMap<String,Integer> passwords = dict(hashSalt);
		
		writeFile(passwords);

	}
	
	public static HashMap<String,String> hash() {

		BufferedReader reader;
		HashMap<String,String> file = new HashMap<String,String>();
		try {
			reader = new BufferedReader(new FileReader("../rockyou-samples.sha1-salt.txt"));
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
	
	public static String sha1Hash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	    crypt.reset();
	    crypt.update(password.getBytes("UTF-8"));

	    return new BigInteger(1, crypt.digest()).toString();
	}
	
	public String make(String id) {
        return sha1(id).substring(0, 6);
    }
    public static String sha1(String input) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
        }
        return sha1;
    }
	
	public static HashMap<String,Integer> dict(HashMap<String,String> hashSalt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String temp;
		
		HashMap<String,Integer> commonPasswords = new HashMap<String,Integer>();
		
		String[] commonPass = {"123456", "12345", "123456789", "password", "iloveyou", "princess", "1234567", "rockyou", "12345678", "abc123", "nicole", 
		"daniel", "babygirl", "monkey", "lovely", "jessica", "654321", "michael", "ashley", "qwerty", "111111", "iloveu", "000000", "michelle", "tigger"};
		
		
		
		for(String hash : hashSalt.keySet()) {
			for(String common : commonPass) {
				//System.out.println(sha1(hashSalt.get(hash) + common));
				//System.out.println("+: "+hash);
				//System.out.println("-: "+sha1(hashSalt.get(hash) + common));
				temp = sha1(hashSalt.get(hash) + common);
				if(temp.equals(hash.toUpperCase())) {
					System.out.println("here");
					if(commonPasswords.containsKey(common)) commonPasswords.replace(common, commonPasswords.get(common) + 1);
					else commonPasswords.put(common, 1);
				}
			}
		}
		
		return commonPasswords;
	}
	
	public static void writeFile(HashMap<String,Integer> passwords) throws IOException {
		PrintWriter writer = new PrintWriter("salt-cracked.txt");
		for(String s : passwords.keySet()) {
			writer.println(passwords.get(s) + "," + s);
		}
		writer.close();
	}

}
