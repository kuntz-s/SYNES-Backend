package com.synes.controler.gestionAnnonce;

import com.synes.controler.notification.NotificationControler;
import com.synes.service.firebase.IImageService;
import com.synes.util.ApiError;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import com.synes.util.gestionAnnonce.Annonce;
import com.synes.util.gestionAnnonce.PieceJointe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
public class AnnonceController {



    @Autowired
    IImageService imageService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    NotificationControler notificationControler;
    BaseDeDonnee bd = new BaseDeDonnee();


    // creation d'Annonce
    @RequestMapping(value = "/createAnnonce", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object creerAnnonce(@RequestHeader("authorization") String token, @RequestBody Annonce annonce, HttpServletResponse response) throws InterruptedException, ParseException {

        int id = bd.getIdMemberByToken(token.substring(7));

        annonce.setMembre(bd.getMemberById(id));
        LocalDateTime date = LocalDateTime.now();
        annonce.setPosteLe(date);
        int result = bd.createAnnonce(annonce);


        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{

            simpMessagingTemplate.convertAndSend("/topic/sendAnnonce",annonce);

            return result+"  ANNONCE CREER";
        }
    }

    // creation de piece jointe
    @RequestMapping(value = "/createPieceJointe", method = RequestMethod.POST)
    public Object createPieceJointe(@RequestHeader("authorization") String token, @RequestParam(name = "file") MultipartFile[] files, @RequestParam(name = "id") int idAnnonce, HttpServletResponse response) throws InterruptedException, ParseException {

        PieceJointe pieceJointe = new PieceJointe();

        String imgName = "",imgPath="";

        for (MultipartFile file : files) {

            try {

                String fileName = imageService.save(file);

                String imageUrl = imageService.getImageUrl(fileName);

                imgName = fileName;
                // do whatever you want with that

            } catch (Exception e) {
                //  throw internal error;
            }
        }
        imgPath = "https://firebasestorage.googleapis.com/v0/b/synes-8e5b2.appspot.com/o/"+imgName+"?alt=media&token=3aa66789-4dcc-42c8-876f-48619a96d49a";

        pieceJointe.setUrl(imgPath);
        pieceJointe.setNom(imgName);
        pieceJointe.setIdAnnonce(idAnnonce);
        int result = bd.createPieceJointe(pieceJointe);


        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{

            return result+"  piece jointe CREER";
        }
    }

    // liste Annonces
    @RequestMapping(value = "/listeAnnonce", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object listeAnnonce(){

        return bd.listAnnonces();
    }

    //supprimer une Annonce
    @RequestMapping(value = "/deleteAnnonce/{id}", method = RequestMethod.DELETE, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object deleteAnnonce(@RequestHeader("authorization") String token, @PathVariable("id") int id, HttpServletResponse response){

        int result = bd.deleteAnnonce(id);

        if(result==1){
            response.setStatus(400);
            return new ApiError(400,"une erreur est survenu","bad request");
        }else{
            return result;
        }
    }

    //liste des piecesjointe d'une annonce
    @RequestMapping(value = "/pieceJointeAnnonce/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object pieceJointeAnnonce(@PathVariable("id") int id){

        return bd.getAnnoncePieceJointe(id);
    }

    //liste des annonces creer par un membre
    @RequestMapping(value = "/memberAnnonces/{id}", method = RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE)
    public Object memberAnnonces(@PathVariable("id") int id){

        return bd.getMemberAnnonces(id);
    }




}
