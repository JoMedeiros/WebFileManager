package br.ufrn.fm.controllers;

import br.ufrn.fm.models.*;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import br.ufrn.fm.services.CommandsService;

import br.ufrn.fm.filesAccess.Command;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Esse link pode ajudar com URI patterns:
 * https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/uri-pattern.html
 * @TODO: Mudar O resultado das requests para HttpResponse (Pra gerar erros mais significativos)
 */
@RestController
@RequestMapping("/api")
@Api(value = "API REST Para gerenciamento de arquivos remotos.")
@CrossOrigin(origins = "*") // Não é muito seguro permitir qualquer origem, mas para fins didáticos...
public class FileManagerController {

    public static final String root = "storage/";

    private Command command;
    private CommandsService commandsService;

    public FileManagerController(){
        this.command = new Command(root);
        commandsService = new CommandsService(command);
    }

    @ApiOperation(value = "Retorna uma lista de arquivos e diretórios")
    @GetMapping(value = "")
    public String main(HttpServletRequest request)
    {
        return "Teste de heroku";
    }

    @ApiOperation(value = "Move um arquivo ou diretório")
    @ApiParam(name = "moveDetails", value = "Um objeto com os campos de origem do arquivo e destino.")
    @PutMapping(
            value = "/move",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> move(HttpServletRequest request,
                                       @RequestBody MoveDetails moveDetails) {
        String responseTxt = "Moved " + moveDetails.getSourcePath() + " to " + moveDetails.getDestinationPath();
        boolean validPaths = command.moveFile(root + moveDetails.getSourcePath(),
                                                root + moveDetails.getDestinationPath());
        if (validPaths)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            return ResponseEntity.badRequest().body("Caminho inválido");
    }

    @ApiOperation(value = "Copia um arquivo ou diretório")
    @ApiParam(name = "moveDetails", value = "Um objeto com os campos de origem do arquivo e destino.")
    @PutMapping(
            value = "/copy",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> copy(HttpServletRequest request,
                                       @RequestBody MoveDetails moveDetails) {
        String responseTxt = "Copied " + moveDetails.getSourcePath() + " to " + moveDetails.getDestinationPath();
        try {
            command.copyFile(root + moveDetails.getSourcePath(),
                    root + moveDetails.getDestinationPath());
            return ResponseEntity.ok().body(responseTxt);
        } catch (IOException e) {
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Caminho inválido");
        }
    }

    @ApiOperation(value = "Retorna uma lista de arquivos e diretórios")
    @GetMapping(
        value = "/list",
        consumes={MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String[]> list_files_dir(
        HttpServletRequest request,
        @RequestBody ListDirDetails listDirDetails
    ) {
        String dirPath = root + listDirDetails.getDirPath();
        String[] response = command.listFiles(dirPath);
        if (response != null)
            return ResponseEntity.status(HttpStatus.OK).body(response);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[]{"Caminho inválido"});
    }

    @ApiOperation(value = "Retorna o conteúdo de um arquivo")
    @GetMapping(
            value = "/get_content",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> get_content(
            HttpServletRequest request,
            @RequestBody GetContentDetails getContentDetails
    ) {
        String path = root + getContentDetails.getPath();
        try {
            String response = command.showFileContent(path);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Caminho inválido");
        }
    }

    @ApiOperation(value = "Deleta um arquivo ou diretório")
    @ApiParam(value = "Um objeto contendo o camingo do arquivo a ser deletado.")
    @DeleteMapping(value = "/delete",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> delete(HttpServletRequest request,
                                         @RequestBody DeleteDetails deleteDetails) {
        String responseTxt = "Deleting " + deleteDetails.getPath();
        if (deleteDetails.isRecursive())
            responseTxt += " recursively";
        // @TODO verificar se os caminhos são válidos
        boolean result = command.removeDirectoryOrFile(root + deleteDetails.getPath());
        if (result)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo se caminho é inválido ou o diretório está vazio
            return ResponseEntity.badRequest().body("Operação inválida");
    }

    @ApiOperation(value = "Cria um diretório")
    @PostMapping(value = "/make_dir",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> make_dir(HttpServletRequest request,
                                         @RequestBody MakeDirDetails makeDirDetails) {
        String responseTxt = "Creating " + makeDirDetails.getPath();
        if (makeDirDetails.isRecursive())
            responseTxt += " recursively";
        // @TODO verificar se os caminhos são válidos
        boolean result = command.createDirectory(root + makeDirDetails.getPath());
        if (result)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            return ResponseEntity.badRequest().body("Caminho inválido");
    }

    @ApiOperation(value = "Cria um arquivo")
    @PostMapping(value = "/create_file",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> create_file(HttpServletRequest request,
                                           @RequestBody MakeDirDetails makeDirDetails) {
        String responseTxt = "Creating " + makeDirDetails.getPath();
        if (makeDirDetails.isRecursive())
            responseTxt += " recursively";
        // @TODO verificar se os caminhos são válidos
        boolean result = commandsService.createFile(root + makeDirDetails.getPath());
        if (result)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            return ResponseEntity.badRequest().body("Caminho inválido");
    }
}
