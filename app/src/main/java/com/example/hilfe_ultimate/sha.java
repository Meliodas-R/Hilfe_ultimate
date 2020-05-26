package com.example.hilfe_ultimate;

import java.security.MessageDigest;

public class sha {

    /**
     * @param data Infromación a ser encriptada.
     * @param shaN Método de encriptación,SHA-1,SHA-224,SHA-256,SHA-384,SHA-512
     */
    public static byte[] encryptSHA(byte[] data, String shaN) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(data);
        return sha.digest();

    }
}

