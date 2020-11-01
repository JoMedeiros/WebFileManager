package br.ufrn.fm.filesAccess;

import java.io.IOException;

public interface ICommand {
    public String[] listFiles(String dirPath);
    public boolean createDirectory(String dirName);
    public boolean createFile(String fileName) throws IOException;
    public String showFileContent(String filePath) throws IOException;
    public boolean moveFile(String filePath, String fileDestiny);
    public boolean removeDirectoryOrFile(String fileOrDirName);
    public String printWorkingDirectory();
}