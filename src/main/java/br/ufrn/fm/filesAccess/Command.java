package br.ufrn.fm.filesAccess;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Command {

    public static final String EOL = System.getProperty("line.separator");
    public String root;

    public Command(String s) {
        File storageDir = new File (s);
        root = s;
        storageDir.mkdir();
    }

    public String[] listFiles(String dirPath) {
        File f = new File(dirPath);
        return Arrays.stream(f.list()).map(s ->
            (new File(f.getPath() +"/"+ s)).isDirectory()? s + "/": s
        ).toArray(String[]::new);
    }

    public boolean createDirectory(String dirName){
        File f = new File(dirName);
        return f.mkdir();
    }

    public boolean createFile(String fileName) throws IOException {
        File f = new File(fileName);
        return f.createNewFile();
    }

    public String showFileContent(String filePath) throws IOException {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String nextLine = "";
            StringBuilder sb = new StringBuilder();
            while ((nextLine = br.readLine()) != null) {
                sb.append(nextLine);
                sb.append(EOL);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }
        return "";
    }

    public boolean moveFile(String filePath, String fileDestiny) {
        File f = new File(filePath);

        File d = new File(fileDestiny);

        return f.renameTo(d);
    }

    public void copyFile(String filePath, String fileDestiny) throws IOException {
        File f = new File(filePath);

        File d = new File(fileDestiny);

        Files.copy(f.toPath(), d.toPath());
    }

    public boolean removeDirectoryOrFile(String fileOrDirName) {
        File f = new File(fileOrDirName);
        return f.delete();
    }

    public String printWorkingDirectory() {
        Paths.get(".").toAbsolutePath().normalize().toString();
        return System.getProperty("user.dir");
    }
}