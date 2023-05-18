package com.synes.util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class BaseDeDonnee {





    public int Add_Membre(Membre newMembre){

        int rep=0,cnt=0;
        cnt=verif_double(newMembre.email );

       /* SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm:ss");
        Calendar calendar = Calendar.getInstance();*/


        LocalDateTime date = LocalDateTime.now();

        if(cnt==0){
            try{
                String query="INSERT INTO `membre`('matricule' , 'nom' , 'prenom', 'email' , 'photo' , 'motDePasse', 'role', 'idUniversite', dateCreation) VALUES (?,?,?,?,?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1,newMembre.matricule);
                pst.setString(2,newMembre.nom);
                pst.setString(3,newMembre.prenom);
                pst.setString(4,newMembre.email);
                pst.setString(5,newMembre.photo);
                pst.setString(6,newMembre.motdepasse);
                pst.setString(7,newMembre.role);
                pst.setInt(8,newMembre.iduniversite);
                pst.setObject(9,date);

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){
                return 1;
            }else{
                return 0;
            }
        }else{
            System.out.println("this member alrady exist");

            return 0;
        }


    }


    public int verif_double(String login ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `email` FROM `member` WHERE `email`='"+login+"'");



            while(rs.next()){
                h=h+1;
            }

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(h==0){
            return 0;
        }else{
            return 1;
        }

    }



    public int verif_Membre(String email ,String pws ){

        String passWord="",mail="";


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `motDePasse`,`email` FROM `membre` WHERE `email`='"+email+"'");



            while(rs.next()){
                passWord=rs.getString("motDePasse");
                mail=rs.getString("email");
                System.out.println(passWord+" "+mail);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }

        if(pws.equals(passWord)){
            return 1;
        }else{
            return 0;
        }

    }




}



