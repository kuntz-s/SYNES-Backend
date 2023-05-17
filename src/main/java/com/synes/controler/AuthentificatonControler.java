package com.synes.controler;

import com.synes.util.BaseDeDonnee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificatonControler {

    BaseDeDonnee bd = new BaseDeDonnee();
   // String email,pws;

    @GetMapping(value = "/{email}+{pws}")
    public String welcome(@PathVariable("email") String email, @PathVariable("pws") String pws){
        //pws = "mdptamojulien";
       // email= "tamojulien@gmail.com";
        String text="";
        int val = bd.verif_Membre(email,pws);
        if (val == 0){
            text = "you are not a SYNES member";
        }else {
            text = "welcome in SYNES platform";
        }
        return text;
    }
}
