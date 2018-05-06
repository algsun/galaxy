package com.microwise.api.v1;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang.geng
 * @date 2014-6-26
 */
@Controller
@ApiIgnore
@RequestMapping("/v1")
public class IndexV1Action {
    public static final PegDownProcessor pegDownProcessor = new PegDownProcessor(Extensions.ALL);

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        request.setAttribute("action", this);
        return "/api/v1/index";
    }

    @RequestMapping("/doc")
    public String index() {
        return "/api/docs/index";
    }

    public String markdown2html(String markdown) {
        return pegDownProcessor.markdownToHtml(markdown);
    }
}
