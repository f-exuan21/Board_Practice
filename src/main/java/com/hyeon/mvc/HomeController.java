package com.hyeon.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping(value = "/show")
    @ResponseBody
    public String show() {
        return "<h1>show<h1>";
    }

}
