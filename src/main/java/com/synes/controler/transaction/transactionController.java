package com.synes.controler.transaction;

import com.synes.controler.notification.NotificationControler;
import com.synes.util.ApiError;
import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionTransaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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


    // creation d'évenement
    @MessageMapping("/sendNotificationCreateTransaction")
    @SendTo("/topic/sendNotificationCreateTransaction")
   // @RequestMapping(value = "/createTransaction", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object createTransaction(@RequestHeader("authorization") String token, @RequestBody Transaction transaction, HttpServletResponse response) throws InterruptedException, ParseException {

        System.out.println("    vvv");
        if (bd.verif_permission(bd.getRoleId(bd.getCurrentUser(token.substring(7)).getNomRole()), bd.getIdPermission("Gestion transaction")) == 0) {
            int id = bd.getIdMemberByToken(token.substring(7));
            System.out.println(id+"    vvv");
            int idDestinateur = 0;

            int result = bd.createTransaction(transaction);

            if(result==1){
                response.setStatus(400);
                return new ApiError(400,"une erreur est survenu","bad request");
            }else{
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
                Notification notification = new Notification("La transaction de Mr/Mme "+ bd.getMemberById(transaction.getMembre().getId()).getNoms()+" "+bd.getMemberById(transaction.getMembre().getId()).getPrenom()+" pour "+transaction.getRaison()+" a bien été reçu",date,"CONFIRMATION DE TRANSACTION");
                bd.createNotif(notification);
                //notificationControler.sendNotificationTo(notification,idDestinateur);
                //notificationControler.sendNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(String.valueOf(notification.getMembre().getId()),"/specific",notification);

                return result+"  TRANSACTION AJOUTEE";
            }
        }else {
            response.setStatus(401);
            //throw new Error("user not found");
            return new ApiError(401,"vous n'avez pas le droit d'effectuer cette operation","Unauthorized");
        }
    }

}
