package ru.dobrotrener.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", "", "/index", "/index.php"})
    public String getIndex() {
        return "index";
    }
}
