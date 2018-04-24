package com.cwt.bpg.cbt.tpromigration.encryptor;

public class EncryptorRunner {

	public static void main(String[] args) {
		Encryptor enc = new Encryptor("secretKey");
		System.out.println("user:"+enc.encrypt("EinvoiceUser"));
		System.out.println("password:"+enc.encrypt("@cwteinv1134"));
		
		System.out.println("user:"+enc.decrypt("/RIjNuFKuri/X6VZFcuxcw=="));
		System.out.println("password:"+enc.decrypt("sGhOX/JxBrktrJByikd1vA=="));

	}

}
