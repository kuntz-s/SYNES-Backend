package com.synes.controler.notification;

import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationControler {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    BaseDeDonnee bd = new BaseDeDonnee();
/*
    @MessageMapping("/sendNotification")
    @SendTo("/topic/sendNotification")
    public Object sendNotification (Notification notification) throws InterruptedException{
        Thread.sleep(2000);
        return notification ;
    }

    @MessageMapping("/sendPrivateNotification")
    public void sendPrivateNotification (@Payload Notification notification) throws InterruptedException{
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(notification.getMembre().getId()),"/specific",notification);
    }

*/

    @MessageMapping("/solde")
    //@RequestMapping(value = "/solde", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public void solde () throws InterruptedException{
        simpMessagingTemplate.convertAndSend("/getSolde",bd.getSolde());
    }

    @RequestMapping(value = "/getNotification", method = RequestMethod.GET)
    public Object getNotif() {
        return bd.getNotifs();
    }

    @RequestMapping(value = "/getPrivateNotification", method = RequestMethod.GET)
    public Object getPrivateNotification (@RequestHeader("authorization") String token) {
        int id = bd.getIdMemberByToken(token.substring(7));
        return bd.getPrivateNotifs(id);
    }

   /* @MessageMapping("/getPrivateNotification/{id}")
    public void getPrivateNotification () throws InterruptedException{
        Thread.sleep(2000);
        return bd.getPrivateNotifs();
    }



    @MessageMapping("/sendNotificationto")
    @SendTo("/topic/sendNotificationto")
    public Object sendNotificationTo (Notification notification, int id) throws InterruptedException{
        Thread.sleep(2000);
        return notification ;
    }*/

}
