package com.cwt.bpg.cbt.tpromigration.encryptor;

public class EncryptorRunner {

	public static void main(String[] args) {
		Encryptor enc = new Encryptor("secretKey");
		//vs4dW0JOBP7/HjnAn2ewkF8PklB5dS34/Uxr7hMjCcM=
			//	com.cwt.tpromigration.mongodb.dbpwd=uxilwqPfd/+IRxSPrAFcLqCoPcDzDABc8zqk0mi0iQk=
		System.out.println(enc.decrypt("RD0L9ECWZohFxngMDkKTP6oKaWsqUh5fSzC+eRWW9FE="));
		System.out.println(enc.decrypt("d3RLKdssM6lhYyV1k8HfCg=="));
		
		//apac-services-app
		//M3d1t#rr35n8unD@nc#
	}

}
