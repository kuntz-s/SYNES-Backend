package com.synes.controler.gestionEvenement;

import com.synes.controler.notification.NotificationControler;
import com.synes.util.ApiError;
import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionEvenement.Evenements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.time.LocalDateTime;


@RestController
public class eventController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    NotificationControler notificationControler;
    BaseDeDonnee bd = new BaseDeDonnee();


    // creation d'évenement
    //@MessageMapping("/sendNotificationCreateEvent")
    //@SendTo("/topic/sendNotification")
    @RequestMapping(value = "/createEvent", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerEvenement(@RequestHeader("authorization") String token, @RequestBody Evenements evenements, HttpServletResponse response) throws InterruptedException, ParseException {

        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion Evènement")) == 0) {
            int id = bd.getIdMemberByToken(token.substring(7));
            System.out.println(id+"    vvv");

            int result = bd.createEvent(evenements,id);

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
                LocalDateTime date = LocalDateTime.now();
                Notification notification = new Notification(bd.getMemberById(0),"Evènement "+evenements.getNom()+" a été créé",date,"EVENEMENT "+evenements.getDescription(),bd.getOrganeById(bd.getMemberOrgane(id)).getNom());
                bd.createNotif(notification);
                //notificationControler.sendNotification(notification);
                simpMessagingTemplate.convertAndSend("/topic/sendNotification",notification);

                return result+"  EVENEMENT CREER";
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }

    // liste évènements
    @RequestMapping(value = "/listeEvents", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeEvents(){

        return bd.getEvents();
    }

    // mise a jour info évenement
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

    //solde des cotribution pour un evenement
    @RequestMapping(value = "/soldeEvent/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object soldeEvent(@PathVariable("id") int id){

        return bd.getSoldeEvenement(id);
    }

    //liste des membres ayant contribué a un évènement
    @RequestMapping(value = "/eventMembers/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object eventMembers(@PathVariable("id") int id){

        return bd.getEventMembres(id);
    }

    //liste des évènements auxquels un membres a participé
    @RequestMapping(value = "/memberEvents/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object memberEvents(@PathVariable("id") int id){

        return bd.getMembreEvents(id);
    }

}
