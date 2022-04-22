package com.logging.elasticsearch.appender.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public String index(Model model) {
        String version = environment.getProperty("app.version");
        String appName = environment.getProperty("app.name");
        model.addAttribute("appName", appName);
        model.addAttribute("version", version);
        return "index";
    }

}