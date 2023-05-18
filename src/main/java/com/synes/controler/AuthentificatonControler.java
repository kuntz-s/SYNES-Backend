package com.synes.controler;

import com.synes.util.BaseDeDonnee;
import com.synes.util.Membre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthentificatonControler {

    BaseDeDonnee bd = new BaseDeDonnee();
   // String email,pws;

   /* @GetMapping(value = "login/{email}+{pws}")
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
    }*/

    @GetMapping(value = "/{matricule}+{nom}+{prenom}+{email}+{photo}+{motdepasse}+{role}+{iduniversite}")
    public Membre addMember(@PathVariable("matricule") String matricule, @PathVariable("nom") String nom,@PathVariable("prenom") String prenom, @PathVariable("email") String email,@PathVariable("photo") String photo, @PathVariable("motdepasse") String motdepasse,@PathVariable("role") String role, @PathVariable("iduniversite") int iduniversite){
        Membre newMembre = new Membre(matricule,nom,prenom,email,photo,motdepasse,role,iduniversite);
        Membre result;

        int val = bd.Add_Membre(newMembre);
        if (val == 1){
            result = newMembre;
        }else {
            result = new Membre();
        }

        return result;
    }

}
