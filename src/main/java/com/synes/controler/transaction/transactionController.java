package com.synes.controler.transaction;

import com.synes.controler.notification.NotificationControler;
import com.synes.util.ApiError;
import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionTransaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@RestController
public class transactionController {



    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    NotificationControler notificationControler;
    BaseDeDonnee bd = new BaseDeDonnee();


    // creation de transactions
    //@MessageMapping("/sendNotificationCreateTransaction")
    //@SendTo("/topic/sendNotification")
    @RequestMapping(value = "/createTransaction", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object createTransaction(@RequestHeader("authorization") String token, @RequestBody Transaction transaction, HttpServletResponse response) throws InterruptedException, ParseException {

        System.out.println("    vvv  "+transaction.getRaison());
        System.out.println("    vvv  "+transaction.getType());
        System.out.println("    vvv  "+transaction.getMontant());
        System.out.println("    vvv  "+transaction.getMembre().getId());
        System.out.println("    vvv  "+transaction.getEvenements());


        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion transaction")) == 0) {
            int id = bd.getIdMemberByToken(token.substring(7));
            int idDestinateur = 0;

            int result = bd.createTransaction(transaction);

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu !","bad request");
            }else{
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
                Notification notification = new Notification(bd.getMemberById(id),"La transaction de Mr/Mme "+ bd.getMemberById(transaction.getMembre().getId()).getNoms()+" "+bd.getMemberById(transaction.getMembre().getId()).getPrenom()+" pour "+transaction.getRaison()+" a bien été reçu",date,"CONFIRMATION DE TRANSACTION "+ bd.getMemberById(transaction.getMembre().getId()).getNoms(),bd.getOrganeById(bd.getMemberOrgane(id)).getNom());
                System.out.println(id+"    vvv   "+bd.getMemberOrgane(id));
                System.out.println(id+"    vvnv   "+bd.getMemberById(transaction.getMembre().getId()).getNoms());
                bd.createNotif(notification);
                //notificationControler.sendNotificationTo(notification,idDestinateur);
                //notificationControler.sendNotification(notification);
                simpMessagingTemplate.convertAndSend("/specific/"+transaction.getMembre().getId(),notification);

                return result+"  TRANSACTION AJOUTEE";
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }

    // liste transactions
    @RequestMapping(value = "/listeTransaction", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeTransaction(){
        System.out.println("aaaaaaaaaaaaaaaaaa");
        return bd.getTransactions();
    }

    //liste des transaction éffectué par un membre
    @RequestMapping(value = "/memberTransactions/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object memberTransactions(@PathVariable("id") int id){
        return bd.getMembreTransactions(id);
    }

}
