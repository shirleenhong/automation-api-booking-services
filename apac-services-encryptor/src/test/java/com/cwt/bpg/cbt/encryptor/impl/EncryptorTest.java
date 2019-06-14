package com.cwt.bpg.cbt.encryptor.impl;

import org.junit.Test;

public class EncryptorTest {
    @Test
    public void testProdDbEncryption() throws Exception {
        Encryptor encryptor = new Encryptor("Z&E[CgSHt8\"{>k&h");
        System.out.println(encryptor.decrypt("Ouf+BYKVBQDmk2agjJVNgblXpV+T5AwTjMWB1dDyEa48OPf10dbgMGSVKIDb"));
        System.out.println(encryptor.decrypt("MiZrUr2h0WUMlbaiRceAnT/Ek9Bhx47YTobwmUiW0TBqVVUfsEAhyNOsOUa2voQ="));
    }


    @Test
    public void testNonProdDbEncryption() throws Exception {
        Encryptor encryptor = new Encryptor("TheBestSecretKey");
        System.out.println(encryptor.decrypt("1pg+B9VgliM0N058AgIQFPOQvtIkgT9Ung31jK6PHMvTTkQWIpy3yg3JCmpY"));
        System.out.println(encryptor.decrypt("IZ8bjG6vgDuU86Ar9kTkxUOaGfqVTjDFINI501f2fsdE"));
        System.out.println(encryptor.decrypt("3gd5Y8GzKMA5ORKX2FnKQ4AXVHxNiFynShbiT+w80VEnnj+etJGojw8="));
        System.out.println(encryptor.decrypt("Ve+/TcCQfdXjvueCLmIiy6FDr0M2tcyk7JQ+49aGVcs="));
    }
}