package by.training.equipment_store.util;

import by.training.equipment_store.util.exception.UtilException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Coder {
    public static String encrypt(String st) throws UtilException {
        MessageDigest md = null;
        String result = null;
        try {
            md = MessageDigest.getInstance("MD5");

            md.update(st.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            //Log
            throw new UtilException("Error with encrypting");
        }
        return result;
    }
}
