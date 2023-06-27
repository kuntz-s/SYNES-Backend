package com.synes.controler.notification;

import com.synes.util.Notification;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

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

    @RequestMapping(value = "/getNotifocation", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object getNot(@RequestHeader("authorization") String token, HttpServletResponse response) throws InterruptedException, ParseException {
        return getNotif();
    }


    @MessageMapping("/sendNotificationto")
    @SendTo("/topic/sendNotificationto")
    public Object sendNotificationTo (Notification notification, int id) throws InterruptedException{
        Thread.sleep(2000);
        return notification ;
    }

}
