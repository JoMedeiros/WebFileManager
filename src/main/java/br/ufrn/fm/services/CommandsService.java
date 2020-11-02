package br.ufrn.fm.services;

import static java.util.stream.Collectors.joining;

import br.ufrn.fm.filesAccess.Command;

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
}

