package br.ufrn.fm.services;

import static java.util.stream.Collectors.joining;

import br.ufrn.fm.filesAccess.Command;

import java.util.Arrays;

public class ListFilesInDirService {

    private Command command;

    public ListFilesInDirService(Command command){
        this.command = command;
    }

    public String execute(String dirPath){
        String[] files = command.listFiles(dirPath);

        final String joined = Arrays.asList(files).stream()
                .map(item -> item + ",")
                .collect(joining("\n"));

        return joined;
    }
}

