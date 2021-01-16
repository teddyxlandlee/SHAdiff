package io.github.teddyxlandlee.nios.sha;

import net.csdn.ryz.sha.GetFileSHA256;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarShaHelper {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Invalid");
            System.exit(-1);
        }
        long timeStarts = System.nanoTime();
        System.out.println("Analyzing...");

        JarFile file1 = new JarFile(args[0]);
        JarFile file2 = new JarFile(args[1]);

        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();

        fillMap(map1, file1);
        fillMap(map2, file2);

        List<Set<String>> sets = iterated(map1, map2);

        System.out.printf("Analyzing finished in %fms\n\n--------------------\n",
                (System.nanoTime() - timeStarts) / 1000000.0F);
        System.out.printf("\nOnly in %s:\n", file1.getName());
        sets.get(0).forEach(key -> System.out.println('\t' + key));
        System.out.printf("\nOnly in %s\n", file2.getName());
        sets.get(1).forEach(key -> System.out.println('\t' + key));
        System.out.println("\nDifference between two jars:");
        System.out.printf("\tName\t%s\t%s\n", file1.getName(), file2.getName());
        sets.get(2).forEach(key -> System.out.printf("\t%s\t%s\t%s\n",
                key, map1.getOrDefault(key, "???"), map2.getOrDefault(key, "???")));

        System.out.println("\n--Done--");
    }

    protected static List<Set<String>> iterated(Map<String, String> map1, Map<String, String> map2) {
        Set<String> map1only = new HashSet<>();
        Set<String> map2only = new HashSet<>();
        Set<String> differentSha = new HashSet<>();
        map1.forEach((name, sha256) -> {
            if (!map2.containsKey(name))
                map1only.add(name);
            else if (!map2.get(name).equals(sha256))
                differentSha.add(name);
        });
        map2.forEach((name, sha256) -> {
            if (!map1.containsKey(name))
                map2only.add(name);
        });
        return Arrays.asList(map1only, map2only, differentSha);
    }

    protected static void fillMap(Map<String, String> map1, JarFile file1) throws IOException {
        Enumeration<JarEntry> enumeration = file1.entries();
        JarEntry jarEntry;
        while (enumeration.hasMoreElements()) {
            jarEntry = enumeration.nextElement();
            String name = jarEntry.getRealName();
            InputStream inputStream = file1.getInputStream(jarEntry);
            String sha256 = GetFileSHA256.getFileSHA256(inputStream);
            map1.put(name, sha256);
        }
    }
}
