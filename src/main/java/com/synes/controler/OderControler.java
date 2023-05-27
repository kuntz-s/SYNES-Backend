package com.synes.controler;

import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class OderControler {

    BaseDeDonnee bd = new BaseDeDonnee();


    @GetMapping(value = "/login")
    public String home(){
        return "welcome to the SYNES web site";
    }

    //liste université
    @RequestMapping(value = "/listeUniversite", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeUniversite(){

        return bd.getUniversitys();
    }

    //liste organes
    @RequestMapping(value = "/listeOrganes", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeOrganes(){

        return bd.getOrganes();
    }

    //liste roles
    @RequestMapping(value = "/listeRoles", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeRoles(){

        return bd.getRoles();
    }

    //liste roles d'un organe
    @RequestMapping(value = "/listeRoleOrgane/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeRoleOrgane(@PathVariable("id") int idOrgane){

        return bd.getRolesByOrganes(idOrgane);
    }

    //liste roles des membres
    @RequestMapping(value = "/listeMembres", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeMembres(){

        return bd.getMembres();
    }


}
