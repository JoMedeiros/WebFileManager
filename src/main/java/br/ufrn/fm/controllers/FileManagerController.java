package br.ufrn.fm.controllers;

import br.ufrn.fm.models.DeleteDetails;
import br.ufrn.fm.models.MoveDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(value = "API REST Para gerenciamento de arquivos remotos.")
/**
 * Esse link pode ajudar com URI patterns:
 * https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/uri-pattern.html
 * @TODO: Mudar O resultado das requests para HttpResponse (Pra gerar erros mais significativos)
 */
public class FileManagerController {

    public static final String root = "storage/";
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

    @ApiOperation(value = "Request para mover um arquivo ou diretório",
            consumes = "Consome um JSON com dois campos: sourcePath e destinationPath. " +
                    "Move o arquivo do sourcePath para o destinationPath.")
    @PutMapping(
            value = "/move",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> move(HttpServletRequest request,
                                       @RequestBody MoveDetails moveDetails) {
        String responseTxt = "Moving " + moveDetails.getSourcePath() + " to " + moveDetails.getDestinationPath();
        // @TODO verificar se os caminhos são válidos
        boolean validPaths = true;
        if (validPaths)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            return ResponseEntity.badRequest().body("Caminho inválido");
    }

    @ApiOperation(value = "Request para deletar um arquivo ou diretório",
    consumes = "Consome um JSON com dois campos: path e recusive. O campo path indica o caminho para o arquivo ou " +
            "diretório a ser deletado e recursive é um booleano que indica se os arquivos serão deletados " +
            "recursivamente")
    @DeleteMapping(value = "/delete",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> delete(HttpServletRequest request,
                                         @RequestBody DeleteDetails deleteDetails) {
        String responseTxt = "Deleting " + deleteDetails.getPath();
        if (deleteDetails.isRecursive())
            responseTxt += " recursively";
        // @TODO verificar se os caminhos são válidos
        boolean validPaths = true;
        if (validPaths)
            return ResponseEntity.ok().body(responseTxt);
        else
            // @TODO Colocar uma mensagem mais significativa dizendo qual dos caminhos é inválido
            return ResponseEntity.badRequest().body("Caminho inválido");
    }
}
