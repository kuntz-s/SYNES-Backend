package com.synes.controler;

import com.synes.util.Membre;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

class univ{
    String nom;
    String localisation;
    String logo;

    public univ(String nom, String localisation, String logo) {
        this.nom = nom;
        this.localisation = localisation;
        this.logo = logo;
    }
}
class organe{
    String nom;
    String description;
    int fondAlloue;

    public organe(String nom, String description, int fondAlloue) {
        this.nom = nom;
        this.description = description;
        this.fondAlloue = fondAlloue;
    }
}
class role{
    String nom;
    String description;
    String nomOrgane;

    public role(String nom, String description, String nomOrgane) {
        this.nom = nom;
        this.description = description;
        this.nomOrgane = nomOrgane;
    }
}
class roleToMembre{
    int idRole;
    int idMembre;

    public roleToMembre( int idRole, int idMembre) {
        this.idRole = idRole;
        this.idMembre = idMembre;
    }
}
class permis{
    String nom;
    String description;
    int idOrgane;

    public permis(String nom, String description, int idOrgane) {
        this.nom = nom;
        this.description = description;
        this.idOrgane = idOrgane;
    }
}
class updateMember{
    int id;
    Membre membre;

    public updateMember(int id, Membre membre) {
        this.id = id;
        this.membre = membre;
    }
}

@RestController
public class OderControler {

    BaseDeDonnee bd = new BaseDeDonnee();


    @GetMapping(value = "/login")
    public String home(){
        return "welcome to the SYNES web site";
    }

    //1 liste université
    @RequestMapping(value = "/listeUniversite", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeUniversite(){

        return bd.getUniversitys();
    }

    //2 liste organes
    @RequestMapping(value = "/listeOrganes", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeOrganes(){

        return bd.getOrganes();
    }

    //3.1 liste roles
    @RequestMapping(value = "/listeRoles", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeRoles(){

        return bd.getRoles();
    }

    //3.2 liste roles d'un organe
    @RequestMapping(value = "/listeRoleOrgane/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeRoleOrgane(@PathVariable("id") int idOrgane){

        return bd.getRolesByOrganes(idOrgane);
    }

    //4 liste des membres
    @RequestMapping(value = "/listeMembres", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeMembres(){

        return bd.getMembres();
    }

    //5 liste des membres d'un organe
    @RequestMapping(value = "/listeMembres/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeMembresOrgane(@PathVariable("id") int idOrgane){

        return bd.getMemberOrgane(idOrgane);
    }

    //6 creation université
    @RequestMapping(value = "/createUniv", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerUniv(@RequestHeader("token") String token, @RequestBody() univ newUniv, HttpServletResponse response) {
        System.out.println(newUniv.nom+" mmmmm "+ newUniv.localisation+" mmmmm "+ newUniv.logo);

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            return bd.createUniversity(newUniv.nom, newUniv.localisation, newUniv.logo);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }
    }

    //7 creation organe
    @RequestMapping(value = "/createOrgane", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerOrgane(@RequestHeader("token") String token,@RequestBody organe organe, HttpServletResponse response){

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            return bd.createOrgane(organe.nom,organe.description,organe.fondAlloue);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }
    }

    //8 creation role
    @RequestMapping(value = "/createRole", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerRole(@RequestHeader("token") String token,@RequestBody role role, HttpServletResponse response){


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            return bd.createRole(role.nom,role.description,role.nomOrgane);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }
    }



    //10 attribution role a un membre d'un organe
    @RequestMapping(value = "/giveRoleOrgane", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuRoleOrgane(@RequestHeader("token") String token,@RequestBody roleToMembre roleToMembre, HttpServletResponse response) {

        int idOrgane = bd.getOrganeId(bd.getCurrentUser(token).getNomRole());

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Attribution role organe")) == 0) {
            return bd.giveRoleByOrgane(idOrgane, roleToMembre.idRole, roleToMembre.idMembre);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }

    }

    //11 attribution role a un membre du systeme
    @RequestMapping(value = "/giveRoleSystem", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuRoleSystem(@RequestHeader("token") String token,@RequestBody roleToMembre roleToMembre, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Attribution role système")) == 0) {
            return bd.giveRole(roleToMembre.idRole,roleToMembre.idMembre);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }

    }

    //12.1 attribution permissions
    @RequestMapping(value = "/givePremission", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuPermis(@RequestHeader("token") String token, @RequestBody List<permis> permis, HttpServletResponse response) {

        List list = new ArrayList();

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Gestion syndicat")) == 0) {

            for (int i=0; i<permis.size(); i++){
                list.add(bd.givePermission(permis.get(i).nom,permis.get(i).description,permis.get(i).idOrgane));
            }

            return list;
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }

    }

    //12.2 mise a jour info membres
    @RequestMapping(value = "/updateMembre", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatMember(@RequestHeader("token") String token,@RequestBody updateMember updateMember, HttpServletResponse response) {


      /*  if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token).getNomRole()), bd.getIdPermission("Attribution role organe")) == 0) {
            return bd.updateMembre(updateMember.id,updateMember.membre);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }*/
        return bd.updateMembre(updateMember.id,updateMember.membre);

    }





}
