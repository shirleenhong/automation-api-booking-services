package com.cwt.bpg.cbt.tpromigration.encryptor;

public class EncryptorRunner {

	public static void main(String[] args) throws Exception
	{
		Encryptor enc = new Encryptor("TheBestSecretKey");
		//vs4dW0JOBP7/HjnAn2ewkF8PklB5dS34/Uxr7hMjCcM=
			//	com.cwt.tpromigration.mongodb.dbpwd=uxilwqPfd/+IRxSPrAFcLqCoPcDzDABc8zqk0mi0iQk=
		System.out.println(enc.decrypt("WIWyaFxWVSH40EMs5QVPeWhewhrUvkgfqIhJeoGDq6CSiXXkqUU="));
		System.out.println(enc.decrypt("KMMzQvHf4T34pbkGg2SlMA9lOh9k4wBkvTzIbY2oJAUEe8DK"));
		
		//apac-services-app
		//M3d1t#rr35n8unD@nc#
	}

}
