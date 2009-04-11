package net.soemirno.scripts;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InsertHashScriptBuilder {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length < 2) return;
        File dataFile = new File(args[0]);
        File outputFile = new File(args[1]);
        if (!dataFile.exists()) return;

        makeScriptFile(dataFile, outputFile);
    }

    public static void makeScriptFile(final File inputFile, final File outputFile) throws IOException, NoSuchAlgorithmException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        while (reader.ready()) {
            writer.write("insert into polis_hash(hash) values (\'");
            writer.write(createHash(reader.readLine()));
            writer.write("\');");
            writer.newLine();
        }
        reader.close();
        writer.close();
    }

    private static String createHash(String row) throws NoSuchAlgorithmException {
        StringBuilder data = new StringBuilder();
        String[] columns = row.split(",");
        data.append('\'')
                .append(columns[1])
                .append("\'-\'")
                .append(columns[0])
                .append('\'');
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(data.toString().getBytes(), 0, data.toString().length());
        return Base64.encode(digest.digest()).trim();
    }
}
