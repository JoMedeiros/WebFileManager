package br.ufrn.fm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
/**
 * Esse link pode ajudar com URI patterns:
 * https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/uri-pattern.html
 * @TODO: Mudar O resultado das requests para HttpResponse (Pra gerar erros mais significativos)
 */
public class FileManagerController {

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
