package com.myp.allknow.rsa;

import com.myp.allknow.utils.RsaUtils;
import org.junit.jupiter.api.Test;

class RsaUtilsTest {

    private final String  privateFilePath = "D:\\RSA\\key\\private.key";
    private final String  publicFilePath = "D:\\RSA\\key\\rsa_public.pub";

    @Test
    void getPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }

    @Test
    void getPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }

    @Test
    void generateKey() throws Exception {
        RsaUtils.generateKey(publicFilePath, privateFilePath, "saltss", 2048);
    }
}