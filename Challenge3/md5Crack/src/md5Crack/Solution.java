package md5Crack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;


public class Solution {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		HashMap<String,Integer> passwordFrequency = hash();
		HashMap<String,Integer> passwords = dict(passwordFrequency);
		writeFile(passwords);

	}
	
	public static HashMap<String,Integer> hash() {
		BufferedReader reader;
		HashMap<String,Integer> file = new HashMap<String,Integer>();
		try {
			reader = new BufferedReader(new FileReader("../rockyou-samples.md5.txt"));
			String line = reader.readLine();
			file.put(line, 0);
			while (line != null) {
				line = reader.readLine();
				if(!file.containsKey(line)) file.put(line, 1);
				else file.replace(line, file.get(line) + 1);
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	public static String md5Hash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
	}
	
	public static HashMap<String,Integer> dict(HashMap<String,Integer> passwordFrequency) throws NoSuchAlgorithmException {
		HashMap<String,Integer>  passwords = new HashMap<String,Integer>();
		char[] characters = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		for(Character a: characters) {
			System.out.println(a + "____________________________________________________________________________________________________");
			for(Character b: characters) {
				for(Character c: characters) {
					for(Character d: characters) {	
						for(Character e: characters) {
							if(passwordFrequency.containsKey(md5Hash(a.toString()+b.toString()+c.toString()+d.toString()+e.toString()))) {
								passwords.put(a.toString()+b.toString()+c.toString()+d.toString()+e.toString(), 
								passwordFrequency.get(md5Hash(a.toString()+b.toString()+c.toString()+d.toString()+e.toString())));
							}
						} 
					}
				}
			}
		}
		return passwords;
	}
	
	public static void writeFile(HashMap<String,Integer> passwords) throws IOException {
		PrintWriter writer = new PrintWriter("md5-cracked.txt");
		for(String s : passwords.keySet()) {
			writer.println(passwords.get(s) + "," + s);
		}
		writer.close();
	}

}
