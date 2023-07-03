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
        String imgPath = "";
        for (MultipartFile file : files) {

            try {

                String fileName = imageService.save(file);

                String imageUrl = imageService.getImageUrl(fileName);

                imgPath = imageUrl;
                // do whatever you want with that

            } catch (Exception e) {
                //  throw internal error;
            }
        }

        bd.setImgProfile(imgPath,id);

        return imgPath;
    }

}
