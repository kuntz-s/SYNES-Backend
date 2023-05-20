package com.synes.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthentificatonControler {


    @GetMapping(value = "/login")
    public String home(){
        return "welcome to the SYNES web site";
    }


}
