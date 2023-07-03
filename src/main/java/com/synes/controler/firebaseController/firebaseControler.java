package com.synes.controler.firebaseController;

import com.synes.service.firebase.IImageService;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class firebaseControler {

    @Autowired
    IImageService imageService;

    BaseDeDonnee bd = new BaseDeDonnee();



    @RequestMapping(value = "/setImg", method = RequestMethod.POST)
    public Object create(@RequestHeader("authorization") String token, @RequestParam(name = "file") MultipartFile[] files) {

        int id = bd.getIdMemberByToken(token.substring(7));;
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
        bd.setImgProfile(imgPath,id);

        return imgPath;
    }

}
