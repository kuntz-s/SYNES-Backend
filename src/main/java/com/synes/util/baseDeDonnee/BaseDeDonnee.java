package com.synes.util.baseDeDonnee;

import com.synes.util.Notification;
import com.synes.util.gestionEvenement.Evenements;
import com.synes.util.gestionTransaction.SoldeBancaire;
import com.synes.util.gestionTransaction.Transaction;
import com.synes.util.gestionUtilisateur.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDeDonnee {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    //pour le login
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
                membre.setId(rs.getInt("id"));
                membre.setMatricule(rs.getString("matricule"));
                membre.setNoms(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUniversite(getUniversityById(rs.getInt("idUniversite")));
                membre.setRole(getRoleById(rs.getInt("idRole")));
                membre.setDateInscription(rs.getDate("dateInscription"));
                membre.setSuspendu(rs.getInt("suspendu"));


                System.out.println(membre);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  MemberConn well connect");

        return membre;

    }
    public Universite getUniversityById(int id ){

        Universite Universite = new Universite();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `universite` WHERE `id`='"+id+"'");



            while(rs.next()){
                Universite.setId(rs.getInt("id"));
                Universite.setNom(rs.getString("nom"));
                Universite.setLocalisation(rs.getString("localisation"));
                Universite.setLogo(rs.getString("logo"));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get univ name");

        return Universite;

    }
    public Role getRoleById(int id ){

        Role Role = new Role();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` WHERE `id`='"+id+"'");



            while(rs.next()){
                Role.setId(rs.getInt("id"));
                Role.setNom(rs.getString("nom"));
                Role.setDescription(rs.getString("description"));
                Role.setOrgane(getOrganeById(rs.getInt("idOrgane")));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get role info ; id = "+Role.getId());

        return Role;

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
                nom = getPermissionById(id).getNom();

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
    public Permission getPermissionById(int id ){


        Permission permission = new Permission();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `permissions` WHERE `id`='"+id+"'");



            while(rs.next()){

                permission.setId(rs.getInt("id"));
                permission.setNom(rs.getString("nom"));
                permission.setDescription(rs.getString("description"));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get permission name");

        return permission;

    }

    //pour ajouter le role pardefaut d'un membre a sa creation
    public int getRoleIdByUnivId(int id ){

        int idOrgane = getOrganeIdByUnivId(id);
        int idRole = getRoleIdByOrganeId(idOrgane);

        return idRole;

    }
    public int getRoleIdByOrganeId(int id ){

        int rid=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `role` WHERE `idOrgane`='"+id+"'");



            while(rs.next()){
                rid=rs.getInt("id");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get role id = " +rid);

        return rid;

    }
    public int getOrganeIdByUnivId(int id ){

        int oid=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `organe` WHERE `idUniversite`='"+id+"'");



            while(rs.next()){
                oid=rs.getInt("id");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get organe id = "+oid);

        return oid;

    }


    public int getOrganeIdByRoleId(int id ){

        int oid=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idOrgane` FROM `role` WHERE `id`='"+id+"'");



            while(rs.next()){
                oid=rs.getInt("idOrgane");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get role id = " +oid);

        return oid;

    }

    //1
    public List<Universite> getUniversitys(){
        System.out.println("  get univertys start");
        String nom="";
        int i=1;
        List<Universite> listUniv = new ArrayList<>();



        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `universite` ");



            while(rs.next()){
                Universite Universite = new Universite();

                Universite.setId(rs.getInt("id"));
                Universite.setNom(rs.getString("nom"));
                Universite.setLocalisation(rs.getString("localisation"));
                Universite.setLogo(rs.getString("logo"));

                System.out.println("nom univ: "+ Universite.getNom());

                listUniv.add(Universite);


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

    //2
    public List<Organe> getOrganes(){
        System.out.println("  get organes start");
        int i=1;
        List<Organe> listOrganes = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `organe` ");



            while(rs.next()){


                Organe Organe = new Organe();

                Organe.setId(rs.getInt("id"));
                Organe.setNom(rs.getString("nom"));
                Organe.setDescription(rs.getString("description"));
                Organe.setFondAlloue(rs.getInt("fondAlloue"));
                Organe.setUniversite(getUniversityById(rs.getInt("idUniversite")));


                System.out.println("nom organe: "+ Organe.getNom());

                listOrganes.add(Organe);


                System.out.println("organe n: "+i+" = "+ Organe.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listOrganes");
        }
        System.out.println("  list well getted");

        return listOrganes;
    }

    //3.1
    public List<Role> getRoles(){
        System.out.println("  get roles start");
        int i=1;
        List<Role> listRoles = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` ");



            while(rs.next()){


                Role Role = new Role();

                Role.setId(rs.getInt("id"));
                Role.setNom(rs.getString("nom"));
                Role.setDescription(rs.getString("description"));
                Role.setOrgane(getOrganeById(rs.getInt("idOrgane")));

                System.out.println("nom univ: "+ Role.getNom());

                listRoles.add(Role);


                System.out.println("role n: "+i+" = "+ Role.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listRole");
        }
        System.out.println("  list well getted");

        return listRoles;
    }

    //3.2
    public List<Role> getRolesByOrganes(int idOrgane){
        System.out.println("  get role/id start");
        int i=1;
        List<Role> listRole = new ArrayList<>();



        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` WHERE `idOrgane`='"+idOrgane+"'");



            while(rs.next()){

                Role Role = new Role();

                Role.setId(rs.getInt("id"));
                Role.setNom(rs.getString("nom"));
                Role.setDescription(rs.getString("description"));
                Role.setOrgane(getOrganeById(rs.getInt("idOrgane")));

                System.out.println("nom role: "+ Role.getNom());

                listRole.add(Role);


                System.out.println("role n: "+i+" = "+ Role.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listrole/id");
        }
        System.out.println("  list well getted");

        return listRole;
    }

    //4
    public List<updateMember> getMembres(){
        System.out.println("  get membres start");
        int i=1;
        List<updateMember> listMembres = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membre` ");



            while(rs.next()){

                updateMember updateMember = new updateMember();
                Membre membre = new Membre();
                List<Object> listAv = new ArrayList<>();

                membre.setMatricule(rs.getString("matricule"));
                membre.setNoms(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUniversite(getUniversityById(rs.getInt("idUniversite")));
                membre.setRole(getRoleById(rs.getInt("idRole")));
                membre.setDateInscription(rs.getDate("dateInscription"));
                membre.setSuspendu(rs.getInt("suspendu"));

                listAv.add(getMemberAvertissement(rs.getInt("id")));


                membre.setAvertissement(listAv);


                updateMember.setId(rs.getInt("id"));
                updateMember.setMembre(membre);


                System.out.println("nom membre: "+updateMember.getMembre().getNoms());

                listMembres.add(updateMember);


                System.out.println("membre n: "+i+" = "+updateMember.getMembre().getNoms());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listMembres");
        }
        System.out.println("  list well getted");

        return listMembres;
    }

    //5
    public List<updateMember> getMembresByOrganes(int idOrgane,int organeUtilisateur){
      if (idOrgane==organeUtilisateur){
          System.out.println("  get membre/organe start");
          int i=1;
          List<updateMember> listMembre = new ArrayList<>();


          try{

              System.out.println(i);

              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

              Statement stmt = con.createStatement();

              ResultSet rs = stmt.executeQuery("SELECT * FROM `membre` WHERE `idOrgane`='"+idOrgane+"'");



              while(rs.next()){

                  updateMember updateMember = new updateMember();
                  Membre membre = new Membre();

                  membre.setMatricule(rs.getString("matricule"));
                  membre.setNoms(rs.getString("nom"));
                  membre.setPrenom(rs.getString("prenom"));
                  membre.setEmail(rs.getString("email"));
                  membre.setPhoto(rs.getString("photo"));
                  membre.setMotdepasse(rs.getString("motDePasse"));
                  membre.setUniversite(getUniversityById(rs.getInt("idUniversite")));
                  membre.setRole(getRoleById(rs.getInt("idRole")));
                  membre.setDateInscription(rs.getDate("dateInscription"));
                  membre.setSuspendu(rs.getInt("suspendu"));

                  updateMember.setId(rs.getInt("id"));
                  updateMember.setMembre(membre);



                  System.out.println("nom membre: "+updateMember.getMembre().getNoms());

                  listMembre.add(updateMember);


                  System.out.println("membre n: "+i+" = "+updateMember.getMembre().getNoms());
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

    //6
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

                return 0;
            }else{
                return 1;
            }
        }else{
            System.out.println("this university alrady exist");

            return 1;
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
        String nomOr = "Section "+nom;
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


                System.out.println("register univOrgane successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){

                creatUniversityRole(nomOr);

                return 1;
            }else{
                return 0;
            }

    }
    public int creatUniversityRole(String nom){

        int rep=0;

        String nomRo = "Membre "+nom;
        String description = "Membre du syndicat appartenant a la  "+nom;
        int organeId = getOrganeId(nom);
        System.out.println("l'id de l'organe du nouveau role: "+organeId);



            try{
                String query="INSERT INTO `role`(`nom`, `description`, `idOrgane`) VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, nomRo);
                pst.setString(2, description);
                pst.setInt(3, organeId);

                pst.executeUpdate();


                System.out.println("register univRole successfully");
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
    public int getOrganeId(String nom){
        System.out.println("  get organe id start");
        int id=0;

        System.out.println("nom de l'organe dont je veux l'id : "+nom);

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
        System.out.println("  id de cet organe well getted");

        return id;
    }

    //7
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

    //8
    public int createRole(String nom, String description, int idOrgane){
        int rep=0,cnt=0;
        cnt=verif_double_role(nom);


        if(cnt==0){
            try{
                String query="INSERT INTO `role`(`nom`, `description`, `idOrgane`) VALUES  (?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, nom);
                pst.setString(2, description);
                pst.setInt(3, idOrgane);

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

    //9
    public int Add_Membre(Membre newMembre){


        int rep=0,cnt=0;
        cnt=verif_double(newMembre.getEmail());

       /* SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm:ss");
        Calendar calendar = Calendar.getInstance();*/
        String motDePasse = newMembre.getMatricule()+"_SYNES_"+newMembre.getNoms();

        String encryptPws = passwordEncoder.encode(motDePasse);


        LocalDateTime date = LocalDateTime.now();

        if(cnt==0){
            try{
                String query="INSERT INTO `membre`(`matricule`, `nom`, `prenom`, `email`, `photo`, `motDePasse`, `idRole`, `idUniversite`, `dateCreation`, `dateInscription`) VALUES  (?,?,?,?,?,?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, newMembre.getMatricule());
                pst.setString(2, newMembre.getNoms());
                pst.setString(3, newMembre.getPrenom());
                pst.setString(4, newMembre.getEmail());
                pst.setString(5, newMembre.getPhoto());
                pst.setString(6,encryptPws);
                pst.setInt(7, newMembre.getRole().getId());
                System.out.println("l'id role: "+ newMembre.getRole().getId());
                pst.setInt(8, newMembre.getUniversite().getId());
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

            ResultSet rs = stmt.executeQuery("SELECT `email` FROM `membre` WHERE `email`='"+login+"'");



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

    //10
    public int giveRoleByOrgane(int idOrganeA, int idRole, int idMembre){//idOrganeA = id de l'organe du membre qui veut faire la mise à jour
        int rep=0,cnt=0;

        cnt = verifOrgane(idOrganeA,idMembre);

        if(cnt==0){
            try{
                String query="UPDATE `membre` SET `idRole`=? WHERE `id` = ? ";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, idRole);
                pst.setInt(2, idMembre);

                pst.executeUpdate();


                System.out.println("successfully updated");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){
                updateMembreConn(idRole,idMembre);
                return 1;
            }else{
                return 0;
            }
        }else{
            System.out.println("vous avez pas cette permission sur ce membre");

            return 0;
        }

    }
    public int getMemberOrgane(int id){

        int idRole=0,idOrg=0;
        System.out.println("  get member organe start");

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idRole` FROM `membre`  WHERE `id`='"+id+"'");



            while(rs.next()){
                idRole = rs.getInt("idRole");

                idOrg = getOrganeIdByRoleId(idRole);

                System.out.println("id role: "+idOrg);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member organe");
        }
        System.out.println("  id well getted");

        return idOrg;
    }
    public Membre getMemberById(int id){

        System.out.println("  get member start");

        Membre membre = new Membre();

        try{
            System.out.println("je recupère les infos du membre11");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membre`  WHERE `id`='"+id+"'");



            while(rs.next()){

                System.out.println("je recupère les infos du membre");

                membre.setId(rs.getInt("id"));
                membre.setMatricule(rs.getString("matricule"));
                membre.setNoms(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUniversite(getUniversityById(rs.getInt("idUniversite")));
                membre.setRole(getRoleById(rs.getInt("idRole")));
                membre.setDateInscription(rs.getDate("dateInscription"));
                membre.setSuspendu(rs.getInt("suspendu"));




                System.out.println("nom membre: "+membre.getNoms());

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member ");
        }
        System.out.println("  id well getted");

        return membre;
    }
    public int verifOrgane(int idOrgane, int idMembre){

        int idMembreOrgane = getMemberOrgane(idMembre);

        if (idOrgane == idMembreOrgane){
            return 0;
        }else {
            return 1;
        }

    }
    public int updateMembreConn(int idRole, int idMembre){
        int rep=0,cnt=0;



            try{
                String query="UPDATE `membreconnected` SET `nomRole`=? WHERE `id` = ? ";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, getRoleById(idRole).getNom());
                pst.setInt(2, idMembre);

                pst.executeUpdate();


                System.out.println("successfully updated");
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

    //11
    public int giveRole(int idRole, int idMembre){
        int rep=0;


            try{
                String query="UPDATE `membre` SET `idRole`=? WHERE `id` = ? ";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, idRole);
                pst.setInt(2, idMembre);

                pst.executeUpdate();


                System.out.println("successfully updated");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){
                return 1;
            }else {
                return 0;
            }

    }

    //12.1
    public Object givePermission(int idRole,int permission){

        int rep=0,cnt=0;

            try{
                String query="INSERT INTO `avoirpermission`(`idPermission`, `idRole`) VALUES  (?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, permission);
                pst.setInt(2, idRole);

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }
            if (rep==1){
                return "insert idRole: "+idRole+"; idPermission: "+permission;
            }else {
                return 1;
            }


    }
   /* public int givePermissionToRole(int idPermission, int idRole){

        int rep=0;


            try{
                String query="UPDATE `avoirpermission` SET `idPermission`=? WHERE `idRole` = ? ";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, idPermission);
                pst.setInt(2, idRole);

                pst.executeUpdate();


                System.out.println("successfully updated");
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

    }*/
    public int deletePermission(int id){

        int rep=0;


        try{
            String query="DELETE FROM `avoirpermission` WHERE `idRole`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully delete");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }
    /*public int verif_double_permission(int idPermis ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idPermission` FROM `avoirpermission` WHERE `idPermission`='"+idPermis+"'");



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

    }*/
    public int getIdPermission(String nom ){

        int id=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `permissions` WHERE `nom`='"+nom+"'");



            while(rs.next()){
                id=rs.getInt("id");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get permission id");

        return id;

    }

    //12.2
    public int updateMembre(int id, Membre membre){
        int rep=0;

        String encryptPws = passwordEncoder.encode(membre.getMotdepasse());


        try{
            String query="UPDATE `membre` SET `nom`=?,`prenom`=?,`email`=?,`photo`=?,`motDePasse`=?,`idRole`=?,`idUniversite`=?,`dateInscription`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, membre.getNoms());
            pst.setString(2, membre.getPrenom());
            pst.setString(3, membre.getEmail());
            pst.setString(4, membre.getPhoto());
            pst.setString(5, encryptPws);
            pst.setInt(6,membre.getRole().getId());
            pst.setInt(7, membre.getUniversite().getId());
            pst.setObject(8,membre.getDateInscription());
            pst.setInt(9,id);

            pst.executeUpdate();


            System.out.println("member successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    //14
    public int updateUniversite(int id, String nom, String localisation, String logo){
        int rep=0;


        try{
            String query="UPDATE `universite` SET `nom`=?,`localisation`=?,`logo`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, nom);
            pst.setString(2, localisation);
            pst.setString(3, logo);
            pst.setInt(4,id);

            pst.executeUpdate();


            System.out.println("university successfully updated");
            updateOrganeUniv(id,"Section "+nom);
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }
    public int updateOrganeUniv( int idUniv,String nom){
        int rep=0;

        try{
            String query="UPDATE `organe` SET `nom`=? WHERE `idUniversite` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, nom);
            pst.setInt(2, idUniv);

            pst.executeUpdate();


            System.out.println("organe successfully updated");

            int idOrgane = getOrganeId(nom);
            updateRoleUniv("Membre "+nom,idOrgane);

            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }
    public int updateRoleUniv(String nom, int idOrgane){
        int rep=0;


        try{
            String query="UPDATE `role` SET `nom`=? WHERE `idOrgane` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, nom);
            pst.setInt(2, idOrgane);

            pst.executeUpdate();


            System.out.println("role successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    //15
    public int updateRole(int id, String nom, String description, int idOrgane){
        int rep=0;


        try{
            String query="UPDATE `role` SET `nom`=?,`description`=?,`idOrgane`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, nom);
            pst.setString(2, description);
            pst.setInt(3, idOrgane);
            pst.setInt(4,id);

            pst.executeUpdate();


            System.out.println("role successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    //16
    public int updateOrgane(int id, String nom, String description,int fond){
        int rep=0;

        try{
            String query="UPDATE `organe` SET `fondAlloue`=?,`description`=?,`nom`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, fond);
            pst.setString(2, description);
            pst.setString(3, nom);
            pst.setInt(4, id);

            pst.executeUpdate();


            System.out.println("organe successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    public int verif_permission(int idRole, int idPermission){

        int h=0;
        int id=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idPermission` FROM `avoirpermission` WHERE `idRole`='"+idRole+"'");



            while(rs.next()){
                id=rs.getInt("idPermission");
                System.out.println("le role : "+idRole+" a les permissions : "+id);
                if (idPermission==id) {
                    h = h + 1;
                }
            }

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(h>0){
            return 0;
        }else{
            return 1;
        }

    }
    /*int getPermissionId(String nom){
        System.out.println("  get perm id start");
        int id=0;

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `permission`  WHERE `nom`='"+nom+"'");



            while(rs.next()){
                id = rs.getInt("id");

                System.out.println("id permission: "+id);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id permission");
        }
        System.out.println("  id well getted");

        return id;
    }*/

    public UseConnectInfo getCurrentUser(String token){
        UseConnectInfo useConnectInfo = new UseConnectInfo();


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membreconnected` WHERE `token`='"+token+"'");



            while(rs.next()){
                useConnectInfo.setMembreId(rs.getInt("idMembre"));
                useConnectInfo.setNomRole(rs.getString("nomRole"));
                useConnectInfo.setNomUniversite(rs.getString("nomUniversite"));
                useConnectInfo.setToken(rs.getString("token"));


                System.out.println("ttttt"+useConnectInfo.getNomRole());
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
            System.out.println("swfdxgfchvjbknlk,ml;ùmfhcgvjhbkjnlk,m 2");
        }
        System.out.println("  MemberConn well connect");

        return useConnectInfo;
    }


    public int AddConnectedMembre(UseConnectInfo useConnectInfo){


        int rep=0,cnt=0;
        cnt=verif_double_conn(getIdMemberByMatricule(useConnectInfo.getMembre().getMatricule()));

        System.out.println("id du membre que je connecte "+getIdMemberByMatricule(useConnectInfo.getMembre().getMatricule()));

        String permissions = "";
        for (int i=0; i<useConnectInfo.getListPermission().size(); i++){
            permissions = permissions+";"+useConnectInfo.getListPermission().get(i);
        }

        if(cnt==0){
            try{
                String query="INSERT INTO `membreconnected`(`idMembre`, `nomRole`, `nomUniversite`, `token`, `permissions`) VALUES  (?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, getIdMemberByMatricule(useConnectInfo.getMembre().getMatricule()));
                pst.setString(2, useConnectInfo.getNomRole());
                pst.setString(3, useConnectInfo.getNomUniversite());
                pst.setString(4, useConnectInfo.getToken());
                pst.setString(5, permissions);

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
            try{
                String query="UPDATE `membreconnected` SET `token`=? WHERE `idMembre`=?";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, useConnectInfo.getToken());
                pst.setInt(2, getIdMemberByMatricule(useConnectInfo.getMembre().getMatricule()));

                pst.executeUpdate();


                System.out.println("update successfully");
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


    } ///gerer l'envoie d'email
    public int verif_double_conn(int idMembre ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idMembre` FROM `membreConnected` WHERE `idMembre`='"+idMembre+"'");



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
    public int getIdMemberByMatricule(String matricule){
        System.out.println("  get membres start");
        int id=0;


        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `membre` WHERE `matricule` ='"+matricule+"'");



            while(rs.next()){
                id = rs.getInt("id");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listMembres");
        }
        System.out.println("  list well getted");

        return id;
    }

    public int getRoleId(String nom){
        System.out.println("  get role id start");
        int id=0;

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `role`  WHERE `nom`='"+nom+"'");



            while(rs.next()){
                id = rs.getInt("id");

                System.out.println("id role: "+id);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id role");
        }
        System.out.println("  id well getted");

        return id;
    }

    public Organe getOrganeById(int id){
        System.out.println("  get organes start");

        Organe Organe = new Organe();


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `organe` WHERE `id`='"+id+"'");



            while(rs.next()){


                Organe.setId(rs.getInt("id"));
                Organe.setNom(rs.getString("nom"));
                Organe.setDescription(rs.getString("description"));
                Organe.setFondAlloue(rs.getInt("fondAlloue"));
                Organe.setUniversite(getUniversityById(rs.getInt("idUniversite")));

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listOrganes");
        }
        System.out.println("  list well getted");

        return Organe;
    }

    public List<Permission> getPermissions(){
        System.out.println("  get roles start");
        int i=1;
        List<Permission> listPermissions = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `permissions` ");



            while(rs.next()){

                Permission permission = new Permission();

                permission.setId(rs.getInt("id"));
                permission.setNom(rs.getString("nom"));
                permission.setDescription(rs.getString("description"));

                System.out.println("nom permission: "+permission.getNom());

                listPermissions.add(permission);


                System.out.println("permission n: "+i+" = "+permission.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listPermissions");
        }
        System.out.println("  list well getted");

        return listPermissions;
    }

    public List<Permission> getPermissionsByRoles(int idRole) {
            System.out.println("  get roles start");
            int i=1;
            List<Permission> listPermissions = new ArrayList<>();


            try{

                System.out.println(i);

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT * FROM `avoirpermission` WHERE `idRole`='"+idRole+"'");



                while(rs.next()){


                    int idPermis = rs.getInt("idPermission");
                    int iddRole = rs.getInt("idRole");

                    Permission permission = getPermissionById(idPermis);
                    permission.setRole(getRoleById(iddRole));

                    System.out.println("nom permission: "+permission.getNom());

                    listPermissions.add(permission);


                    System.out.println("permission n: "+i+" = "+permission.getNom());
                    i++;
                }

            }
            catch (Exception exc){
                System.out.println(exc+"  error connect listPermissions");
            }
            System.out.println("  list well getted");

            return listPermissions;
        }


    //delete

    public int getIdRoleByNom(String nomRole ){
        int res = 0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `role` WHERE `nom`='"+nomRole+"'");



            while(rs.next()){

                res = rs.getInt("id");

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }

        return res;

    }
    public int updateRoleMembre(int idRole, int newRole){
        int rep=0;


        try{
            String query="UPDATE `membre` SET `idRole`=? WHERE `idRole` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, newRole);
            pst.setInt(2,idRole);

            pst.executeUpdate();


            System.out.println("member successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 1;
        }else {
            return 0;
        }

    }

    //supprimer une role
    public int deleteRole(int id){

        String nomRole = "Membre "+getOrganeById(getUniversityOrgane(id)).getNom();
        int newRole = getIdRoleByNom(nomRole);
        updateRoleMembre(id,newRole);

        int rep=0;


        try{
            String query="DELETE FROM `role` WHERE `id`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted  the role");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }
    public int deleteRoleByOrgane(int id){

        int rep=0;


        try{
            String query="DELETE FROM `role` WHERE `idOrgane`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted  the role");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }

    //supprimer une organe
    public int deleteOrgane(int id){

        int rep=0;
        deleteRoleByOrgane(id);


        try{
            String query="DELETE FROM `organe` WHERE `id`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted the organe");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }
    public int getUniversityOrgane(int id){

        int idOrg=0;
        System.out.println("  get member organe start");

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `organe`  WHERE `idUniversite`='"+id+"'");



            while(rs.next()){
                idOrg = rs.getInt("id");

                System.out.println("id organe: "+idOrg);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member organe");
        }
        System.out.println("  id well getted");

        return idOrg;
    }

    /*recupère les role liez a une université début*/
    public int getUniversityRole(int idUniv){

        int idRole=0;
        int idOrg=getUniversityOrgane(idUniv);
        System.out.println("  get start");

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `role`  WHERE `idOrgane`='"+idOrg+"'");



            while(rs.next()){
                idRole = rs.getInt("idOrgane");

                System.out.println("id role: "+idRole);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error ");
        }
        System.out.println("  id well getted");

        return idRole;
    }
    /*recupère les role liez a une université fin*/
    public int deleteOrganeByUniversity(int id){

        int rep=0;
        deleteRoleByOrgane(getUniversityOrgane(id));


        try{
            String query="DELETE FROM `organe` WHERE `idUniversite`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted the organe");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }

    //supprimer une université
    public int deleteUniversite(int id){

        deleteOrganeByUniversity(id);

        int rep=0;


        try{
            String query="DELETE FROM `universite` WHERE `id`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted the university");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }



    public int getIdMemberByToken(String token){
        System.out.println("  get membres start");
        int id=0;


        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idMembre` FROM `membreconnected` WHERE `token` ='"+token+"'");



            while(rs.next()){
                id = rs.getInt("idMembre");
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listMembres");
        }
        System.out.println("  user id well getted");

        return id;
    }

    public int giveAvertissement(Avertissement avertissement){
        int rep=0,cnt=0;



            try{
                String query="INSERT INTO `avoiravertissement`(`idMembre`, `idAvertissement` )  VALUES  (?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, avertissement.getIdMembre());
                pst.setObject(2, avertissement.getId());

                pst.executeUpdate();


                System.out.println("advertise successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){
                return 0;
            }else{
                return 1;
            }


    }
    public Avertissement getAvertissementById(int id ){

        Avertissement avertissement = new Avertissement();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `avertissement` WHERE `id`='"+id+"'");



            while(rs.next()){
                avertissement.setId(rs.getInt("id"));
                avertissement.setRaison(rs.getString("raison"));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }

        return avertissement;

    }
    public List<Avertissement> getAvertissement(int id ){

        List<Avertissement> listA = new ArrayList<>();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `avertissement` WHERE `id`='"+id+"'");



            while(rs.next()){
                Avertissement avertissement = new Avertissement();

                avertissement.setId(rs.getInt("id"));
                avertissement.setRaison(rs.getString("raison"));

                listA.add(avertissement);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }

        return listA;

    }
    public List<Avertissement> listAvertissements(){
        System.out.println("  get avertissement start");
        int i=1;
        List<Avertissement> listAvertissement = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `avoiravertissement` ");



            while(rs.next()){

                Avertissement avertissement = new Avertissement();

                avertissement.setId(rs.getInt("idAvertissement"));
                avertissement.setIdMembre(rs.getInt("idMembre"));
                avertissement.setRaison(getAvertissementById(rs.getInt("idAvertissement")).getRaison());


                listAvertissement.add(avertissement);



                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listAvertissement");
        }
        System.out.println("  list well getted");

        return listAvertissement;
    }

    public List getMemberAvertissement(int id){

        int idAvertissement=0;
        System.out.println("  get member avertissement start");
        List listAv = new ArrayList();

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idAvertissement` FROM `avoiravertissement`  WHERE `idMembre`='"+id+"'");



            while(rs.next()){
                idAvertissement = rs.getInt("idAvertissement");


                listAv.add(getAvertissementById(idAvertissement));

                System.out.println("id avertissement: "+idAvertissement);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member avertissement");
        }
        System.out.println("  id  ii well getted");

        return listAv;
    }

    public int suspendreMembre(int id){
        int rep=0;

        Membre membre = getMemberById(id);
        String supEmail = "Suspendu"+membre.getEmail();

        try{
            String query="UPDATE `membre` SET `email`=?,`suspendu`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, supEmail);
            pst.setInt(2, 1);
            pst.setInt(3, id);

            pst.executeUpdate();


            System.out.println("suspendu successfully");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    public int reabiliterMembre(int id){
        int rep=0;

        Membre membre = getMemberById(id);
        String validEmail = membre.getEmail().substring(8);

        try{
            String query="UPDATE `membre` SET `email`=?,`suspendu`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, validEmail);
            pst.setInt(2, 0);
            pst.setInt(3, id);

            pst.executeUpdate();


            System.out.println("reabiliter successfully ");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    public List<updateMember> listSuspention(){
        System.out.println("  get avertissement start");
        int i=1;
        List<updateMember> listSuspendu = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membre` WHERE `suspendu` = '"+1+"' ");



            while(rs.next()){



                Membre membre = new Membre();
                updateMember updateMember = new updateMember();


                membre.setMatricule(rs.getString("matricule"));
                membre.setNoms(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUniversite(getUniversityById(rs.getInt("idUniversite")));
                membre.setRole(getRoleById(rs.getInt("idRole")));
                membre.setDateInscription(rs.getDate("dateInscription"));


                updateMember.setMembre(membre);
                updateMember.setId(rs.getInt("id"));


                listSuspendu.add(updateMember);



                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listSuspendu");
        }
        System.out.println("  list well getted");

        return listSuspendu;
    }



    /////////////////////////* NOTIFICAION *///////////////////////////

    public int createNotifG(Notification notification){
        System.out.println("creat notif start");
        int rep=0,cnt=0;

        try{
            String query="INSERT INTO `notification`(`typeMessage`, `envoyéLe`, `contenu`)   VALUES  (?,?,?)";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, notification.getTypeMessage());
            pst.setObject(2, notification.getEnvoyéLe());
            pst.setString(3, notification.getContenu());

            pst.executeUpdate();


            System.out.println("register successfully");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }
    public int getNotifId(Notification notification){
        System.out.println("  get id notif start  "+notification.getContenu()+"   "+notification.getEnvoyéLe());
        int i=1,idNot=0;


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `notification` WHERE `typeMessage`='"+notification.getTypeMessage()+"' ");



            while(rs.next()){

                Notification notif = new Notification();

                notification.setId(rs.getInt("id"));
                notification.setTypeMessage(rs.getString("typeMessage"));
                notification.setEnvoyéLe(rs.getDate("envoyéLe"));
                notification.setContenu(rs.getString("contenu"));

                System.out.println(" Notif : "+notification.getTypeMessage());

                idNot = rs.getInt("id");


                System.out.println("notif n: "+i+" = "+notification.getTypeMessage());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id Notif");
        }
        System.out.println("   well getted");

        return idNot;
    }
    public int createNotif(Notification notification){
        System.out.println(getNotifId(notification) +"  creat notif start"+notification.getMembre().getId());
        createNotifG(notification);
        int rep=0,cnt=0;

        try{
            String query="INSERT INTO `recevoirnotification`(`idNotification`, `idMembre`, `circonscription`)  VALUES  (?,?,?)";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            System.out.println("sdddddddddddddddddddd "+getNotifId(notification));
            pst.setInt(1, getNotifId(notification));
            pst.setInt(2, notification.getMembre().getId());
            pst.setString(3, notification.getCirconscription());

            pst.executeUpdate();


            System.out.println("register successfully");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }

    public List<Notification> getAllNotifs(){
        System.out.println("  get notif start");
        int i=1;
        List<Notification> listNotifs = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `notification` ");



            while(rs.next()){

                Notification notification = new Notification();

                notification.setId(rs.getInt("id"));
                notification.setTypeMessage(rs.getString("typeMessage"));
                notification.setEnvoyéLe(rs.getDate("envoyéLe"));
                notification.setContenu(rs.getString("contenu"));

                System.out.println(" Notif : "+notification.getTypeMessage());

                listNotifs.add(notification);


                System.out.println("notif n: "+i+" = "+notification.getTypeMessage());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listNotifs");
        }
        System.out.println("  list well getted");

        return listNotifs;
    }

    public Notification getNotifById(int idNot){
        System.out.println("  get id notif start");
        int i=1;
        Notification notification = new Notification();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `notification` WHERE `id`='"+idNot+"' ");



            while(rs.next()){

                notification.setId(rs.getInt("id"));
                notification.setTypeMessage(rs.getString("typeMessage"));
                notification.setEnvoyéLe(rs.getDate("envoyéLe"));
                notification.setContenu(rs.getString("contenu"));

                System.out.println(" Notif : "+notification.getTypeMessage());

                idNot = rs.getInt("id");


                System.out.println("notif n: "+i+" = "+notification.getTypeMessage());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id Notif");
        }
        System.out.println("   well getted");

        return notification;
    }
    public List<Notification> getNotifs(){
        System.out.println("  get notif start");
        int i=1;
        List<Notification> listNotifs = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `recevoirnotification` WHERE `idMembre`='"+0+"' ");



            while(rs.next()){

                Notification notification = new Notification();

                notification.setId(rs.getInt("id"));
                notification.setCirconscription(rs.getString("circonscription"));

                Notification not = getNotifById(rs.getInt("id"));


                notification.setTypeMessage(not.getTypeMessage());
                notification.setEnvoyéLe(not.getEnvoyéLe());
                notification.setContenu(not.getContenu());


                System.out.println(" Notif : "+notification.getTypeMessage());

                listNotifs.add(notification);


                System.out.println("notif n: "+i+" = "+notification.getTypeMessage());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listNotifs");
        }
        System.out.println("  list well getted");

        return listNotifs;
    }
    public List<Notification> getPrivateNotifs(int id){
        System.out.println("  get notif start");
        int i=1;
        List<Notification> listNotifs = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `recevoirnotification` WHERE `idMembre`='"+id+"' ");


            while(rs.next()){

                Notification notification = new Notification();

                notification.setId(rs.getInt("id"));
                notification.setCirconscription(rs.getString("circonscription"));

                Notification not = getNotifById(rs.getInt("id"));
                Membre membre = getMemberById(rs.getInt("idMembre"));


                notification.setTypeMessage(not.getTypeMessage());
                notification.setMembre(membre);
                notification.setEnvoyéLe(not.getEnvoyéLe());
                notification.setContenu(not.getContenu());


                System.out.println(" Notif : "+notification.getTypeMessage());

                listNotifs.add(notification);


                System.out.println("notif n: "+i+" = "+notification.getTypeMessage());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listNotifs");
        }
        System.out.println("  list well getted");

        return listNotifs;
    }



    /////////////////////////* CRUD EVENEMENTS *///////////////////////////

    public int createEvent(Evenements evenements,int id){
        System.out.println("creat event start");
        int rep=0,cnt=0;
        cnt=verif_double_event(evenements.getNom());

        if(cnt==0){
            try{
                String query="INSERT INTO `evenement`(`nom`, `dateDebut`, `dateFin`, `description`, `idMembre`, `photo`)  VALUES  (?,?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, evenements.getNom());
                pst.setObject(2, evenements.getDateDebut());
                pst.setObject(3, evenements.getDateFin());
                pst.setString(4, evenements.getDescription());
                pst.setInt(5, id);
                pst.setString(6, evenements.getPhoto());

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){
                return 0;
            }else{
                return 1;
            }
        }else{
            System.out.println("this event alrady exist");

            return 0;
        }


    }

    public int verif_double_event(String nom ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `evenement` WHERE `nom`='"+nom+"'");



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
    public Membre eGetMemberById(int id){

        System.out.println("  get member start");

        Membre membre = new Membre();

        try{
            System.out.println("je recupère les infos du membre  11");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `membreconnected`  WHERE `idMembre`='"+id+"'");



            while(rs.next()){

                System.out.println("je recupère les infos du membre");

                int idDuM = rs.getInt("idMembre");

                membre = new Membre(getMemberById(idDuM));

                membre.setId(idDuM);
               /* membre.setMatricule(mI.getMatricule());
                membre.setNoms(mI.getNoms());
                membre.setPrenom(mI.getPrenom());
                membre.setEmail(mI.getEmail());
                membre.setPhoto(mI.getPhoto());
                membre.setMotdepasse(mI.getMotdepasse());
                membre.setUniversite(mI.getUniversite());
                membre.setRole(mI.getRole());
                membre.setDateInscription(mI.getDateInscription());
                membre.setSuspendu(mI.getSuspendu());*/



                System.out.println("nom du fameux membre: "+membre.getNoms());

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member ");
        }
        System.out.println("  id well getted");

        return membre;
    }
    public List<Evenements> getEvents(){
        System.out.println("  get events start");
        int i=1;
        List<Evenements> listEvents = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `evenement` ");



            while(rs.next()){

                Evenements evenements = new Evenements();

                evenements.setId(rs.getInt("id"));
                evenements.setNom(rs.getString("nom"));
                evenements.setDateDebut(rs.getDate("dateDebut"));
                evenements.setDateFin(rs.getDate("dateFin"));
                evenements.setDescription(rs.getString("description"));
                evenements.setPhoto(rs.getString("photo"));
                evenements.setMembre(getMemberById(rs.getInt("idMembre")));



                System.out.println("nom event: "+evenements.getNom()+"vvv"+rs.getInt("idMembre")+"  "+evenements.getMembre().getNoms());

                listEvents.add(evenements);


                System.out.println("event n: "+i+" = "+evenements.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listEvents");
        }
        System.out.println("  list well getted");

        return listEvents;
    }
    private Evenements getEvenementById(int idEvenement) {

        System.out.println("  get events start");
        int i=1;
        Evenements evenements = new Evenements();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `evenement` WHERE `id`= '"+idEvenement+"'");



            while(rs.next()){


                evenements.setId(rs.getInt("id"));
                evenements.setNom(rs.getString("nom"));
                evenements.setDateDebut(rs.getDate("dateDebut"));
                evenements.setDateFin(rs.getDate("dateFin"));
                evenements.setDescription(rs.getString("description"));
                evenements.setPhoto(rs.getString("photo"));
                evenements.setMembre(getMemberById(rs.getInt("idMembre")));



                System.out.println("nom event: "+evenements.getNom()+"vvv"+rs.getInt("idMembre")+"  "+evenements.getMembre().getNoms());


                System.out.println("event n: "+i+" = "+evenements.getNom());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listEvents");
        }
        System.out.println("  list well getted");

        return evenements;

    }

    public int updateEvent(Evenements evenements){
        int rep=0;

        try{
            String query="UPDATE `evenement` SET `nom`=?,`dateDebut`=?,`dateFin`=?,`description`=?,`photo`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setString(1, evenements.getNom());
            pst.setObject(2, evenements.getDateDebut());
            pst.setObject(3, evenements.getDateFin());
            pst.setString(4, evenements.getDescription());
            pst.setString(5, evenements.getPhoto());
            pst.setInt(6, evenements.getId());

            pst.executeUpdate();


            System.out.println("event successfully updated");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else {
            return 1;
        }

    }

    public int deleteEvent(int id){

        int rep=0;
        deleteRoleByOrgane(id);


        try{
            String query="DELETE FROM `evenement` WHERE `id`=?";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, id);

            pst.executeUpdate();


            System.out.println("successfully deleted the event");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }



    /////////////////////////* TRANSACTION *///////////////////////////


    public int createTransaction(Transaction transaction) throws ParseException {
        System.out.println("create Transaction start");
        int rep=0,cnt=0;

            try{
                String query="INSERT INTO `transaction`(`montant`, `type`, `raison`, `idMembre`, `idEvenement`) VALUES  (?,?,?,?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, transaction.getMontant());
                pst.setString(2, transaction.getType());
                pst.setString(3, transaction.getRaison());
                pst.setInt(4, transaction.getMembre().getId());
                pst.setInt(5, transaction.getEvenements().getId());

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            int idTrans = getTransactionId(transaction.getRaison(),transaction.getMembre().getId());
            transaction.setId(idTrans);


            if(rep==1){
                if(transaction.getType().equals("retrait")){
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());

                    SoldeBancaire soldeBancaire = new SoldeBancaire();
                    soldeBancaire.setSolde(transaction.getMontant());
                    soldeBancaire.setTransaction(transaction);
                    soldeBancaire.setModifieLe(date);

                    updateSoldeBancaire(soldeBancaire,1);
                }else {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());

                    SoldeBancaire soldeBancaire = new SoldeBancaire();
                    soldeBancaire.setSolde(transaction.getMontant());
                    soldeBancaire.setTransaction(transaction);
                    soldeBancaire.setModifieLe(date);

                    updateSoldeBancaire(soldeBancaire,0);
                }

                return 0;
            }else{
                return 1;
            }

    }

    public List<Transaction> getTransactions(){
        System.out.println("  get Transactions start");
        int i=1;
        List<Transaction> listTrans = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `transaction` ");



            while(rs.next()){

                Transaction transaction = new Transaction();

                transaction.setId(rs.getInt("id"));
                transaction.setRaison(rs.getString("raison"));
                transaction.setType(rs.getString("type"));
                transaction.setMontant(rs.getInt("montant"));
                transaction.setMembre(getMemberById(rs.getInt("idMembre")));
                transaction.setEvenements(getEvenementById(rs.getInt("idEvenement")));



                System.out.println("nom event: "+transaction.getRaison()+"vvv"+rs.getInt("idMembre")+"  "+transaction.getMembre().getNoms());

                listTrans.add(transaction);


                System.out.println("event n: "+i+" = "+transaction.getRaison());
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listEvents");
        }
        System.out.println("  list well getted");

        return listTrans;
    }



    public int getTransactionId(String raison, int idM){
        System.out.println("  get Transaction id start");
        int id=0;

        System.out.println("nom de la Transaction dont je veux l'id : "+raison);

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `transaction`  WHERE `raison`='"+raison+"' AND `idMembre`='"+idM+"' ");



            while(rs.next()){
                id = rs.getInt("id");

                System.out.println("id Transaction: "+id);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id Transaction");
        }
        System.out.println("  id de cet Transaction well getted");

        return id;
    }

    public int updateSoldeBancaire(SoldeBancaire soldeBancaire,int isNeg){
        System.out.println("create soldebancaire start");
        int rep=0,solde = 0;
        System.out.println("le solde avant : "+getSolde());
        if (isNeg==1){
            solde = getSolde()-soldeBancaire.getSolde();
            System.out.println(getSolde()+"-"+soldeBancaire.getSolde());
            System.out.println(solde);
        }else {
            solde = getSolde()+soldeBancaire.getSolde();
            System.out.println(getSolde()+"+"+soldeBancaire.getSolde());
            System.out.println(solde);
        }



        try{
            String query="INSERT INTO `soldebancaire`(`solde`, `modifieLe`, `idTransaction`) VALUES  (?,?,?)";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, solde);
            pst.setObject(2, soldeBancaire.getModifieLe());
            pst.setInt(3, soldeBancaire.getTransaction().getId());

            pst.executeUpdate();


            System.out.println("register successfully");
            rep=1;

        }
        catch (Exception exc){
            System.out.println(exc);
        }

        if(rep==1){
            return 0;
        }else{
            return 1;
        }

    }

    public int getSolde(){
        System.out.println("  get solde start");
        int i=1;
        int solde=0;


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `soldebancaire` ");



            while(rs.next()){

                solde = rs.getInt("solde");
                System.out.println("solde n: "+i+" = "+rs.getInt("id"));
                i++;
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listEvents");
        }
        System.out.println("  solde well getted");

        return solde;
    }

}



