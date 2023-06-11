package com.synes.controler.gestionEvenement;

import com.synes.util.ApiError;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionEvenement.Evenements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;



@RestController
public class eventController {

    BaseDeDonnee bd = new BaseDeDonnee();


    // creation d'évenement
    @RequestMapping(value = "/createEvent", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerEvenement(@RequestHeader("authorization") String token, @RequestBody Evenements evenements, HttpServletResponse response){

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Evènement")) == 0) {
            evenements.setIdMembre(bd.getIdMemberByToken(token));

            int result = bd.createEvent(evenements);

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

    //16 mise a jour info évenement
    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object updatEvent(@RequestHeader("authorization") String token, @RequestBody Evenements evenements, HttpServletResponse response) {


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Evènement")) == 0) {

            int result = bd.updateEvent(evenements);

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

    //supprimer un évenement
    @RequestMapping(value = "/deleteEvent/{id}", method = RequestMethod.DELETE, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object deleteOrgane(@RequestHeader("authorization") String token, @PathVariable("id") int id, HttpServletResponse response){

        int result = bd.deleteEvent(id);

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }
    }


}
