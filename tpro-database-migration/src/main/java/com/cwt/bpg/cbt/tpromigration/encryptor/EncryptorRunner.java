package com.cwt.bpg.cbt.tpromigration.encryptor;

public class EncryptorRunner {

	public static void main(String[] args) {
		Encryptor enc = new Encryptor("secretKey");
		System.out.println("user:"+enc.decrypt("lo5lRrdWRcjQlGM1icGxDw=="));
		System.out.println("password:"+enc.decrypt("BVa5y45inDfEgHaTzoddQQ=="));

	}

}
