package br.ufrn.fm.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufrn.fm.services.ListFilesInDirService;

import br.ufrn.fm.filesAccess.Command;
import br.ufrn.fm.models.ListDirDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Esse link pode ajudar com URI patterns:
 * https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/uri-pattern.html
 * @TODO: Mudar O resultado das requests para HttpResponse (Pra gerar erros mais significativos)
 */
@Controller
@ResponseBody
public class FileManagerController {

    public static final String root = "storage/";

    private Command command;

    public FileManagerController(){
        this.command = new Command(root);
    }

    @GetMapping(
        value = "list/",
        consumes={MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> list_files_dir(
        HttpServletRequest request,
        @RequestBody ListDirDetails listDirDetails
    ) {
        String dirPath = root + listDirDetails.getDirPath();

        ListFilesInDirService listFilesInDirService = new ListFilesInDirService(this.command);

        String response = listFilesInDirService.execute(dirPath);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/home/**/files")
    public String list_from_home(HttpServletRequest request) {
        return "from test2(), request uri: " + request.getRequestURI();
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping("/root/**/files")
    public String list_from_root(HttpServletRequest request) {
        return "from list_files(), request uri: " + request.getRequestURI();
    }

}
