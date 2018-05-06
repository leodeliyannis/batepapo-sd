package br.com.ufp.sd.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Security {
	
	public static String encrypt(String senha) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

	    return digest.digest(senha.getBytes(StandardCharsets.UTF_8)).toString();
	}

}
