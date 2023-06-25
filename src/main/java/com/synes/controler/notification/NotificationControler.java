package com.synes.controler.notification;

import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationControler {

    BaseDeDonnee bd = new BaseDeDonnee();

    @MessageMapping("/sendNotification")
    @SendTo("/topic/sendNotification")
    public Object sendNotification (Notification notification) throws InterruptedException{
        Thread.sleep(2000);
        return notification ;
    }

    @MessageMapping("/getNotification")
    @SendTo("/topic/getNotification")
    public Object getNotif() throws InterruptedException{
        Thread.sleep(2000);
        return bd.getNotifs();
    }

}
