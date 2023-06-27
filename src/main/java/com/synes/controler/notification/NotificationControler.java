package com.synes.controler.notification;

import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotificationControler {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    BaseDeDonnee bd = new BaseDeDonnee();

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


    @RequestMapping("/getNotification")
    public Object getNotif() throws InterruptedException{
        Thread.sleep(2000);
        return bd.getNotifs();
    }
    @RequestMapping("/getPrivateNotification/{id}")
    public Object getPrivateNotification (@PathVariable("id") int id) throws InterruptedException{
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
