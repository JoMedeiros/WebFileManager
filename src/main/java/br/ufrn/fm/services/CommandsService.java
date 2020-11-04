package br.ufrn.fm.services;

import static java.util.stream.Collectors.joining;

import br.ufrn.fm.filesAccess.Command;
import br.ufrn.fm.models.MakeDirDetails;

import java.io.File;
import java.util.Arrays;

public class CommandsService {

    private Command command;

    public CommandsService(Command command){
        this.command = command;
    }

    public String[] list_files(String dirPath){
        dirPath = dirPath.replaceAll("..", "");
        try {
            return command.listFiles(dirPath);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean createFile(String path) {
        try {
            command.createFile(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createDirectory(MakeDirDetails makeDirDetails)
    {
        if (makeDirDetails.isRecursive()){
            String[] dirs = makeDirDetails.getPath().split("/");
            String path = command.root;
            for (String dir: dirs) {
                path += "/" + dir;
                File f = new File(path);
                if (!f.exists())
                    f.mkdir();
            }
            return true;
        }
        else {
            return command.createDirectory(makeDirDetails.getPath());
        }
    }

    public boolean moveFile(String filePath, String fileDestiny)
    {
        return command.moveFile(filePath, fileDestiny);
    }
}

