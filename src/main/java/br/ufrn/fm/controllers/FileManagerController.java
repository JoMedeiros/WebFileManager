package br.ufrn.fm.controllers;

import br.ufrn.fm.models.MoveDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
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

    @PutMapping(
            value = "/api/move",
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

}
