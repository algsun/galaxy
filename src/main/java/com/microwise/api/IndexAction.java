package com.microwise.api;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaohui
 * @date 13-12-6 10:09
 */
@Controller
@ApiIgnore
public class IndexAction {
    public static final PegDownProcessor pegDownProcessor = new PegDownProcessor(Extensions.ALL);

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        request.setAttribute("action", this);
        return "api/index";
    }

    /**
     * 将 markdown 转化为 html
     *
     * @param markdown
     * @return
     */
    public String markdown2html(String markdown) {
        return pegDownProcessor.markdownToHtml(markdown);
    }
}
