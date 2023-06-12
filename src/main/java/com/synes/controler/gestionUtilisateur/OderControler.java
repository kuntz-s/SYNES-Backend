package com.synes.controler.gestionUtilisateur;

import com.synes.util.ApiError;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionUtilisateur.*;
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
class organes{
    int id;
    String nom;
    String description;
    int fondAlloue;

    public organes(int id, String nom, String description, int fondAlloue) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.fondAlloue = fondAlloue;
    }
}

class role{
    String nom;
    String description;
    int idOrgane;

    public role(String nom, String description, int idOrgane) {
        this.nom = nom;
        this.description = description;
        this.idOrgane = idOrgane;
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
    int idRole;
    List<Integer> newPermissions;

    public permis(int idRole, List<Integer> newPermissions) {
        this.idRole = idRole;
        this.newPermissions = newPermissions;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public List<Integer> getNewPermissions() {
        return newPermissions;
    }

    public void setNewPermissions(List<Integer> newPermissions) {
        this.newPermissions = newPermissions;
    }
}

@RestController
public class OderControler {

    BaseDeDonnee bd = new BaseDeDonnee();


    //1 liste université
    @RequestMapping(value = "/listeUniversite", method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
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
    public Object listeRoleOrgane(@PathVariable("id") int idOrgane, HttpServletResponse response){

        return bd.getRolesByOrganes(idOrgane);
    }

    // liste permissions
    @RequestMapping(value = "/listePermissions", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listePermis(){

        return bd.getPermissions();
    }

    // liste permissions d'un roles
    @RequestMapping(value = "/listePermissionsRole/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listePermisRole(@PathVariable("id") int idRole, HttpServletResponse response){

        return bd.getPermissionsByRoles(idRole);
    }

    //4 liste des membres
    @RequestMapping(value = "/listeMembres", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeMembres(){

        return bd.getMembres();
    }

    //5 liste des membres d'un organe
    @RequestMapping(value = "/listeMembres/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeMembresOrgane(@RequestHeader("authorization") String token, @PathVariable("id") int idOrgane, HttpServletResponse response){

        return bd.getMembresByOrganes(idOrgane,bd.getMemberOrgane(bd.getCurrentUser(token.substring(7)).getMembreId()));
    }

    //6 creation université
    @RequestMapping(value = "/createUniv", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerUniv(@RequestHeader("authorization") String token, @RequestBody() univ newUniv, HttpServletResponse response) {
        System.out.println(newUniv.nom+" mmmmm "+ newUniv.localisation+" mmmmm "+ newUniv.logo );



        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {

            int result = bd.createUniversity(newUniv.nom, newUniv.localisation, newUniv.logo);

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                return result;
            }

        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }

    //7 creation organe
    @RequestMapping(value = "/createOrgane", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerOrgane(@RequestHeader("authorization") String token,@RequestBody organe organe, HttpServletResponse response){

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            return bd.createOrgane(organe.nom,organe.description,organe.fondAlloue);
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }

    //8 creation role
    @RequestMapping(value = "/createRole", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerRole(@RequestHeader("authorization") String token,@RequestBody role role, HttpServletResponse response){


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            return bd.createRole(role.nom,role.description,role.idOrgane);
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }



    //10 attribution role a un membre d'un organe
    @RequestMapping(value = "/giveRoleOrgane", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuRoleOrgane(@RequestHeader("authorization") String token,@RequestBody roleToMembre roleToMembre, HttpServletResponse response) {

        int idOrgane = bd.getOrganeId(bd.getCurrentUser(token.substring(7)).getNomRole());

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Attribution role organe")) == 0) {
            return bd.giveRoleByOrgane(idOrgane, roleToMembre.idRole, roleToMembre.idMembre);
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //11 attribution role a un membre du systeme
    @RequestMapping(value = "/giveRoleSystem", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuRoleSystem(@RequestHeader("authorization") String token,@RequestBody roleToMembre roleToMembre, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Attribution role système")) == 0) {
            return bd.giveRole(roleToMembre.idRole,roleToMembre.idMembre);
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //12.1 attribution permissions
    @RequestMapping(value = "/givePremission", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object attribuPermis(@RequestHeader("authorization") String token, @RequestBody permis permis, HttpServletResponse response) {

        List list = new ArrayList();

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion syndicat")) == 0) {

            bd.deletePermission(permis.idRole);
            for (int i=0; i<permis.newPermissions.size(); i++){
                list.add(bd.givePermission(permis.idRole,permis.newPermissions.get(i)));
            }

            return list;
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //12.2 mise a jour info membres
    @RequestMapping(value = "/updateMembre", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatMember(@RequestHeader("authorization") String token, @RequestBody updateMember updateMember, HttpServletResponse response) {


      /*  if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Attribution role organe")) == 0) {
            return bd.updateMembre(updateMember.id,updateMember.membre);
        }else {
            return "vous n'avez pas le droit d'effectuer cette operation";
        }*/

        int result = bd.updateMembre(updateMember.getId(),updateMember.getMembre());

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }

    }

    //14 mise a jour iinfo université
    @RequestMapping(value = "/updateUniversite", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatUniv(@RequestHeader("authorization") String token, @RequestBody Universite univ, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {

            int result = bd.updateUniversite(univ.getId(),univ.getNom(),univ.getLocalisation(),univ.getLogo());

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                return result;
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //15 mise a jour info role
    @RequestMapping(value = "/updateRole", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatRole(@RequestHeader("authorization") String token, @RequestBody Role Role, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {
            int result = bd.updateRole(Role.getId(), Role.getNom(), Role.getDescription(), Role.getOrgane().getId());

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                return result;
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //16 mise a jour info organe
    @RequestMapping(value = "/updateOrgane", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatOrgane(@RequestHeader("authorization") String token, @RequestBody organes organes, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Syndicat")) == 0) {

            int result = bd.updateOrgane(organes.id, organes.nom, organes.description, organes.fondAlloue);

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                return result;
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }

    }

    //supprimer un role
    @RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.DELETE, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object deleteRole(@RequestHeader("authorization") String token, @PathVariable("id") int id, HttpServletResponse response){

        int result = bd.deleteRole(id);

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }
    }

    //supprimer un organe
    @RequestMapping(value = "/deleteOrgane/{id}", method = RequestMethod.DELETE, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object deleteOrgane(@RequestHeader("authorization") String token, @PathVariable("id") int id, HttpServletResponse response){

        int result = bd.deleteOrgane(id);

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }
    }

    //supprimer une université
    @RequestMapping(value = "/deleteUniversity/{id}", method = RequestMethod.DELETE, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object deleteUniversity(@RequestHeader("authorization") String token, @PathVariable("id") int id, HttpServletResponse response) {

        int result = bd.deleteUniversite(id);

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }
    }



}
