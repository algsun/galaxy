package com.microwise.saturn.action;

import com.google.gson.Gson;
import com.microwise.saturn.bean.TextureVO;
import com.microwise.saturn.service.TextureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 材质管理
 */
@Controller("textureController")
@RequestMapping("/texture")

public class TextureController {

    public static final String _pagePath = "texture/texture-index.ftl";

    @Autowired
    private TextureService textureService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("_pagePath", _pagePath);
        return "saturn/pages/manager/layout";

    }

    @RequestMapping(value = "findTextures", method = RequestMethod.GET)
    @ResponseBody
    public List<TextureVO> findTextures(){
        List<TextureVO> textureVOs = textureService.findAll();
        return  textureVOs;
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@RequestParam String textureName) {
        textureService.insert(textureName);
        return "redirect:/saturn/texture/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@RequestParam Integer id) {
        textureService.deleteById(id);
        return "redirect:/saturn/texture/index";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    @ResponseBody
    public String edit(@RequestParam Integer id, @RequestParam String textureName) {
        textureService.updateById(id, textureName);
        return "true";
    }

    @RequestMapping(value = "isExits", method = RequestMethod.GET)
    @ResponseBody
    public String isExits(@RequestParam String textureName) {
        boolean isExits = textureService.isExits(textureName);
        return new Gson().toJson(isExits);
    }
}
