package io.github.teddyxlandlee.nios.sha;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ZipShaHelper {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid");
            System.exit(-1);
        }
        JarFile file1 = new JarFile(args[0]);
        JarFile file2 = new JarFile(args[1]);

        Map<String, String> map1 = new HashMap<>();

        Enumeration<JarEntry> enumeration = file1.entries();
        JarEntry jarEntry;
        while (enumeration.hasMoreElements()) {
            jarEntry = enumeration.nextElement();
            String name = jarEntry.getRealName();

            map1.put(name, null);
        }
    }
}
