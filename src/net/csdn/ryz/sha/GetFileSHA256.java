package net.csdn.ryz.sha;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 计算文件sha256值
 *
 * @author ryz
 * @since 2020-05-12
 */
public class GetFileSHA256 {
    //public static void main(String[] args) {
    //    File file = new File("D:\\secmcd\\TestApp.apk");
    //    System.out.println("文件 " + file + " SHA256值是:" + getFileSHA1(file));
    //}
    public static String getFileSHA256(File file) {
        String str = "";
        try {
            str = getHash(file, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String getFileSHA256(InputStream stream) {
        String str = "";
        try {
            str = getHash(stream, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    private static String getHash(File file, String hashType) throws Exception {
        InputStream fis = new FileInputStream(file);
        return getHash(fis, hashType);
    }
    private static String getHash(InputStream fis, String hashType) throws Exception {
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }
    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toHexString(aB & 0xFF));
        }
        return sb.toString();
    }
}
