package com.synes.controler.notification;

import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
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
    public void solde () throws InterruptedException{
        simpMessagingTemplate.convertAndSend("/getSolde",bd.getSolde());
    }

    @RequestMapping(value = "/getNotification", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object getNotif() throws InterruptedException{
        Thread.sleep(2000);
        return bd.getNotifs();
    }

    @RequestMapping(value = "/getPrivateNotification", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object getPrivateNotification (@RequestHeader("authorization") String token) throws InterruptedException{
        int id = bd.getIdMemberByToken(token.substring(7));
        Thread.sleep(2000);
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
