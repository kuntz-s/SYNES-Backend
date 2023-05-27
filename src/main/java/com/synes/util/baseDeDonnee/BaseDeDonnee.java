package com.synes.util.baseDeDonnee;

import com.synes.util.MemberConn;
import com.synes.util.Membre;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDonnee {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public int Add_Membre(Membre newMembre){


        int rep=0,cnt=0;
        cnt=verif_double(newMembre.getEmail());

       /* SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm:ss");
        Calendar calendar = Calendar.getInstance();*/

        String encryptPws = passwordEncoder.encode(newMembre.getMotdepasse());


        LocalDateTime date = LocalDateTime.now();

        if(cnt==0){
            try{
                String query="INSERT INTO `membre`(`matricule`, `nom`, `prenom`, `email`, `photo`, `motDePasse`, `idRole`, `idUniversite`, `dateCreation`, `dateInscription`) VALUES  (?,?,?,?,?,?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, newMembre.getMatricule());
                pst.setString(2, newMembre.getNom());
                pst.setString(3, newMembre.getPrenom());
                pst.setString(4, newMembre.getEmail());
                pst.setString(5, newMembre.getPhoto());
                pst.setString(6,encryptPws);
                pst.setInt(7, newMembre.getIdRole());
                pst.setInt(8, newMembre.getIduniversite());
                pst.setObject(9,date);
                pst.setObject(10,newMembre.getDateInscription());

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


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

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

        String mail="",encryptedPws="";


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `motDePasse`,`email` FROM `membre` WHERE `email`='"+email+"'");



            while(rs.next()){
                encryptedPws=rs.getString("motDePasse");
                mail=rs.getString("email");
                System.out.println(pws+" = "+encryptedPws+" "+mail);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }

        if(passwordEncoder.matches(pws,encryptedPws)){
            System.out.println("  Member well connect");
            return 1;
        }else{
            System.out.println(mail+" :fff: "+pws+" != "+encryptedPws+" :even the mail adress or the password is wrong");
            return 0;
        }

    }

    public MemberConn verif_email(String email ){

        String passWord="",mail="";
        MemberConn memberConn = null;


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `motDePasse`,`email` FROM `membre` WHERE `email`='"+email+"'");



            while(rs.next()){
                passWord=rs.getString("motDePasse");
                mail=rs.getString("email");
                System.out.println(passWord+" "+mail);
            }
            memberConn = new MemberConn(mail,passWord);

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  MemberConn well connect");

        return memberConn;

    }

    public Membre searchUser(String email ){

        String passWord="",mail="";
        Membre membre = new Membre();


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membre` WHERE `email`='"+email+"'");



            while(rs.next()){
                membre.setMatricule(rs.getString("matricule"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setIdRole(rs.getInt("idRole"));
                membre.setIduniversite(rs.getInt("idUniversite"));
                membre.setDateInscription(rs.getDate("dateInscription"));


                System.out.println(membre);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  MemberConn well connect");

        return membre;

    }

    public String getUniversityById(int id ){

        String nom="";

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `universite` WHERE `id`='"+id+"'");



            while(rs.next()){
                nom=rs.getString("nom");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get univ name");

        return nom;

    }


    public String getPermissionById(int id ){

        String nom="";

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `permissions` WHERE `id`='"+id+"'");



            while(rs.next()){
                nom=rs.getString("nom");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get permission name");

        return nom;

    }

    public String getRoleById(int id ){

        String nom="";

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `role` WHERE `id`='"+id+"'");



            while(rs.next()){
                nom=rs.getString("nom");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get role name");

        return nom;

    }

    public List<String> getPermission(int idRole ){

        System.out.println("  get list start");
        String nom="";
        int id=0;
        int i=1;
        List<String> listPermissions = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idPermission` FROM `avoirpermission` WHERE `idRole`='"+idRole+"'");



            while(rs.next()){
                id = rs.getInt("idPermission");

                System.out.println("permission id: "+id);
                nom = getPermissionById(id);

                listPermissions.add(nom);


                System.out.println("permission n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect pemission");
        }
        System.out.println("  list well getted");

        return listPermissions;

    }

    public List<String> getUniversitys(){
        System.out.println("  get univertys start");
        String nom="";
        int i=1;
        List<String> listUniv = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `universite` ");



            while(rs.next()){
                 nom = rs.getString("nom");

                System.out.println("nom univ: "+nom);

                listUniv.add(nom);


                System.out.println("univ n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listUniv");
        }
        System.out.println("  list well getted");

        return listUniv;

    }

    public List<String> getOrganes(){
        System.out.println("  get organes start");
        String nom="";
        int i=1;
        List<String> listOrganes = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `organe` ");



            while(rs.next()){
                nom = rs.getString("nom");

                System.out.println("nom organe: "+nom);

                listOrganes.add(nom);


                System.out.println("organe n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listOrganes");
        }
        System.out.println("  list well getted");

        return listOrganes;
    }

    public List<String> getRoles(){
        System.out.println("  get roles start");
        String nom="";
        int i=1;
        List<String> listRoles = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `role` ");



            while(rs.next()){
                nom = rs.getString("nom");

                System.out.println("nom univ: "+nom);

                listRoles.add(nom);


                System.out.println("role n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listRole");
        }
        System.out.println("  list well getted");

        return listRoles;
    }

    public List<String> getRolesByOrganes(int idOrgane){
        System.out.println("  get role/id start");
        String nom="";
        int i=1;
        List<String> listRole = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `role` WHERE `idOrgane`='"+idOrgane+"'");



            while(rs.next()){
                nom = rs.getString("nom");

                System.out.println("nom role: "+nom);

                listRole.add(nom);


                System.out.println("role n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listrole/id");
        }
        System.out.println("  list well getted");

        return listRole;
    }

    public List<String> getMembres(){
        System.out.println("  get membres start");
        String nom="";
        int i=1;
        List<String> listMembres = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `membre` ");



            while(rs.next()){
                nom = rs.getString("nom");

                System.out.println("nom role: "+nom);

                listMembres.add(nom);


                System.out.println("membre n: "+i+" = "+nom);
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listMembres");
        }
        System.out.println("  list well getted");

        return listMembres;
    }

    public List<String> getMembresByOrganes(int idOrgane,int organeUtilisateur){
      if (idOrgane==organeUtilisateur){
          System.out.println("  get membre/organe start");
          String nom="";
          int i=1;
          List<String> listMembre = new ArrayList<>();


          try{

              System.out.println(i);

              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

              Statement stmt = con.createStatement();

              ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `membre` WHERE `idOrgane`='"+idOrgane+"'");



              while(rs.next()){
                  nom = rs.getString("nom");

                  System.out.println("nom membre: "+nom);

                  listMembre.add(nom);


                  System.out.println("membre n: "+i+" = "+nom);
                  i++;
              }

          }
          catch (Exception exc){
              System.out.println(exc+"  error connect membre/organe");
          }
          System.out.println("  list well getted");

          return listMembre;
      }else {
          List list = new ArrayList();
          list.add("vous n'avez pas acces a cet organe");
          return list;
      }
    }

    public int createUniversity(String nom,String local,String logo){

        int rep=0,cnt=0;
        cnt=verif_double_univ(nom);


        if(cnt==0){
            try{
                String query="INSERT INTO `universite`(`nom`, `localisation`, `logo`) VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, nom);
                pst.setString(2, local);
                pst.setString(3, logo);

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){

                creatUniversityOrgane(nom);
                creatUniversityRole(nom);

                return 1;
            }else{
                return 0;
            }
        }else{
            System.out.println("this university alrady exist");

            return 0;
        }

    }

    public int verif_double_univ(String nom ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `universite` WHERE `nom`='"+nom+"'");



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

    public int creatUniversityOrgane(String nom){

        int rep=0;

        int fondA = 0;
        String nomOr = "section"+nom;
        String description = nomOr+" Organe du syndicat propre a l'université de "+nom;
        int univId = getUniversityId(nom);



            try{
                String query="INSERT INTO `organe`(`fondAlloue`, `description`, `nom`, `idUniversite`)  VALUES  (?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, fondA);
                pst.setString(2, description);
                pst.setString(3, nomOr);
                pst.setInt(4, univId);

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

    }

    public int creatUniversityRole(String nom){

        int rep=0;

        String nomRo = "membre"+nom;
        String description = "Membre du syndicat appartenant a l'université de "+nom;
        int organeId = getOrganeId(nom);



            try{
                String query="INSERT INTO `role`(`nom`, `description`, `idOrgane`) VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, nomRo);
                pst.setString(2, description);
                pst.setInt(3, organeId);

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

    }
    int getUniversityId(String nom){
        System.out.println("  get univ id start");
        int id=0;

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `universite`  WHERE `nom`='"+nom+"'");



            while(rs.next()){
                id = rs.getInt("id");

                System.out.println("id univ: "+id);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id univ");
        }
        System.out.println("  id well getted");

        return id;
    }

    int getOrganeId(String nom){
        System.out.println("  get organe id start");
        int id=0;

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `organe`  WHERE `nom`='"+nom+"'");



            while(rs.next()){
                id = rs.getInt("id");

                System.out.println("id organe: "+id);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id organe");
        }
        System.out.println("  id well getted");

        return id;
    }


    public int createOrgane(String nom, String description, int fondAlloue){
        int rep=0,cnt=0;
        cnt=verif_double_organe(nom);



        if(cnt==0){
            try{
                String query="INSERT INTO `organe`(`fondAlloue`, `description`, `nom`)  VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, fondAlloue);
                pst.setString(2, description);
                pst.setString(3, nom);

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
            System.out.println("this university alrady exist");

            return 0;
        }


    }

    public int createRole(String nom, String description, String nomOrgane){
        int rep=0,cnt=0;
        cnt=verif_double_role(nom);

        int idOr = getOrganeId(nomOrgane);


        if(cnt==0){
            try{
                String query="INSERT INTO `role`(`nom`, `description`, `idOrgane`) VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, nom);
                pst.setString(2, description);
                pst.setInt(3, idOr);

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
            System.out.println("this university alrady exist");

            return 0;
        }

    }

    public int verif_double_organe(String nom ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `organe` WHERE `nom`='"+nom+"'");



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

    public int verif_double_role(String nom ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `role` WHERE `nom`='"+nom+"'");



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

}



