package edu.byu.cs.tweeter.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltedSHAHashing {
	//from sample code by Jacob Williams (TA) https://gist.github.com/NovaMachina/2e5c72484cffec0a8ea763a820ba193c#file-saltedshahashing-java
	public static String getSecurePassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte aByte : bytes) {
				sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "FAILED TO HASH PASSWORD";
	}

	public static String getSalt() {
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return Base64.getEncoder().encodeToString(salt);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return "FAILED TO GET SALT";
	}
}
