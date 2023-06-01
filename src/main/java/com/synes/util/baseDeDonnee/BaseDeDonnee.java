package com.synes.util.baseDeDonnee;

import com.synes.util.gestionUtilisateur.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                membre.setMatricule(rs.getString("matricule"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUpduniv(getUniversityById(rs.getInt("idUniversite")));
                membre.setUpdrole(getRoleById(rs.getInt("idRole")));
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
    public upduniv getUniversityById(int id ){

        upduniv upduniv = new upduniv();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `universite` WHERE `id`='"+id+"'");



            while(rs.next()){
                upduniv.setId(rs.getInt("id"));
                upduniv.setNom(rs.getString("nom"));
                upduniv.setLocalisation(rs.getString("localisation"));
                upduniv.setLogo(rs.getString("logo"));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get univ name");

        return upduniv;

    }
    public updrole getRoleById(int id ){

        updrole updrole = new updrole();

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` WHERE `id`='"+id+"'");



            while(rs.next()){
                updrole.setId(rs.getInt("id"));
                updrole.setNom(rs.getString("nom"));
                updrole.setDescription(rs.getString("description"));
                updrole.setUpdorgane(getOrganeById(rs.getInt("idOrgane")));
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  get role name");

        return updrole;

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
        System.out.println("  get role id");

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
        System.out.println("  get organe id");

        return oid;

    }

    //1
    public List<upduniv> getUniversitys(){
        System.out.println("  get univertys start");
        String nom="";
        int i=1;
        List<upduniv> listUniv = new ArrayList<>();



        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `universite` ");



            while(rs.next()){
                upduniv upduniv = new upduniv();

                upduniv.setId(rs.getInt("id"));
                upduniv.setNom(rs.getString("nom"));
                upduniv.setLocalisation(rs.getString("localisation"));
                upduniv.setLogo(rs.getString("logo"));

                System.out.println("nom univ: "+upduniv.getNom());

                listUniv.add(upduniv);


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
    public List<updorgane> getOrganes(){
        System.out.println("  get organes start");
        int i=1;
        List<updorgane> listOrganes = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `organe` ");



            while(rs.next()){


                updorgane updorgane = new updorgane();

                updorgane.setId(rs.getInt("id"));
                updorgane.setNom(rs.getString("nom"));
                updorgane.setDescription(rs.getString("description"));
                updorgane.setFondAlloue(rs.getInt("fondAlloue"));
                updorgane.setUpduniv(getUniversityById(rs.getInt("idUniversite")));


                System.out.println("nom organe: "+updorgane.getNom());

                listOrganes.add(updorgane);


                System.out.println("organe n: "+i+" = "+updorgane.getNom());
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
    public List<updrole> getRoles(){
        System.out.println("  get roles start");
        int i=1;
        List<updrole> listRoles = new ArrayList<>();


        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` ");



            while(rs.next()){


                updrole updrole = new updrole();

                updrole.setId(rs.getInt("id"));
                updrole.setNom(rs.getString("nom"));
                updrole.setDescription(rs.getString("description"));
                updrole.setUpdorgane(getOrganeById(rs.getInt("idOrgane")));

                System.out.println("nom univ: "+updrole.getNom());

                listRoles.add(updrole);


                System.out.println("role n: "+i+" = "+updrole.getNom());
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
    public List<updrole> getRolesByOrganes(int idOrgane){
        System.out.println("  get role/id start");
        int i=1;
        List<updrole> listRole = new ArrayList<>();



        try{

            System.out.println(i);

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `role` WHERE `idOrgane`='"+idOrgane+"'");



            while(rs.next()){

                updrole updrole = new updrole();

                updrole.setId(rs.getInt("id"));
                updrole.setNom(rs.getString("nom"));
                updrole.setDescription(rs.getString("description"));
                updrole.setUpdorgane(getOrganeById(rs.getInt("idOrgane")));

                System.out.println("nom role: "+updrole.getNom());

                listRole.add(updrole);


                System.out.println("role n: "+i+" = "+updrole.getNom());
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

                membre.setMatricule(rs.getString("matricule"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPhoto(rs.getString("photo"));
                membre.setMotdepasse(rs.getString("motDePasse"));
                membre.setUpduniv(getUniversityById(rs.getInt("idUniversite")));
                membre.setUpdrole(getRoleById(rs.getInt("idRole")));
                membre.setDateInscription(rs.getDate("dateInscription"));

                updateMember.setId(rs.getInt("id"));
                updateMember.setMembre(membre);


                System.out.println("nom membre: "+updateMember.getMembre().getNom());

                listMembres.add(updateMember);


                System.out.println("membre n: "+i+" = "+updateMember.getMembre().getNom());
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
                  membre.setNom(rs.getString("nom"));
                  membre.setPrenom(rs.getString("prenom"));
                  membre.setEmail(rs.getString("email"));
                  membre.setPhoto(rs.getString("photo"));
                  membre.setMotdepasse(rs.getString("motDePasse"));
                  membre.setUpduniv(getUniversityById(rs.getInt("idUniversite")));
                  membre.setUpdrole(getRoleById(rs.getInt("idRole")));
                  membre.setDateInscription(rs.getDate("dateInscription"));

                  updateMember.setId(rs.getInt("id"));
                  updateMember.setMembre(membre);



                  System.out.println("nom membre: "+updateMember.getMembre().getNom());

                  listMembre.add(updateMember);


                  System.out.println("membre n: "+i+" = "+updateMember.getMembre().getNom());
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
        String description = "Membre du syndicat appartenant a l'université de "+nom;
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
        String motDePasse = newMembre.getMatricule()+"_SYNES_"+newMembre.getNom();

        String encryptPws = passwordEncoder.encode(motDePasse);


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
                pst.setInt(7, newMembre.getUpdrole().getId());
                pst.setInt(8, newMembre.getUpduniv().getId());
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

        int idOrg=0;
        System.out.println("  get member organe start");

        try{

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `idOrgane` FROM `membre`  WHERE `id`='"+id+"'");



            while(rs.next()){
                idOrg = rs.getInt("idOrgane");

                System.out.println("id organe: "+idOrg);

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect id member organe");
        }
        System.out.println("  id well getted");

        return idOrg;
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
    public int givePermission(String permission, String description, int idRole){

        int rep=0,cnt=0;
        cnt=verif_double_permission(permission);


        if(cnt==0){
            try{
                String query="INSERT INTO `permission`(`nom`, `description`) VALUES  (?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setString(1, permission);
                pst.setString(2, description);

                pst.executeUpdate();


                System.out.println("register successfully");
                rep=1;

            }
            catch (Exception exc){
                System.out.println(exc);
            }

            if(rep==1){

                givePermissionToRole(getIdPermission(permission),idRole);

                return 1;
            }else{
                return 0;
            }
        }else{
            System.out.println("this permission alrady exist");

            return 0;
        }

    }
    public int givePermissionToRole(int idPermission, int idRole){

        int rep=0;


            try{
                String query="INSERT INTO `avoirpermission`(`idRole`, `idPermission`) VALUES  (?,?)";
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
                PreparedStatement pst=con.prepareStatement(query);

                pst.setInt(1, idRole);
                pst.setInt(2, idPermission);

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
    public int verif_double_permission(String nom ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `nom` FROM `permission` WHERE `nom`='"+nom+"'");



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

            pst.setString(1, membre.getNom());
            pst.setString(2, membre.getPrenom());
            pst.setString(3, membre.getEmail());
            pst.setString(4, membre.getPhoto());
            pst.setString(5, encryptPws);
            pst.setInt(6,membre.getUpdrole().getId());
            pst.setInt(7, membre.getUpduniv().getId());
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
            return 1;
        }else {
            return 0;
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
            return 1;
        }else {
            return 0;
        }

    }

    //16
    public int updateOrgane(int id, String nom, String description,int fond, int idUniv){
        int rep=0;

        try{
            String query="UPDATE `organe` SET `fondAlloue`=?,`description`=?,`nom`=?,`idUniversite`=? WHERE `id` = ? ";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");
            PreparedStatement pst=con.prepareStatement(query);

            pst.setInt(1, fond);
            pst.setString(2, description);
            pst.setString(3, nom);
            pst.setInt(4, idUniv);
            pst.setInt(5, id);

            pst.executeUpdate();


            System.out.println("organe successfully updated");
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

        if(h==1){
            return 0;
        }else{
            return 1;
        }

    }
    int getPermissionId(String nom){
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
    }

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


                System.out.println(useConnectInfo);
            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect");
        }
        System.out.println("  MemberConn well connect");

        return useConnectInfo;
    }


    public int AddConnectedMembre(UseConnectInfo useConnectInfo){


        int rep=0,cnt=0;
        cnt=verif_double_conn(useConnectInfo.getMembre().getEmail());

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
            System.out.println("this member alrady exist");

            return 0;
        }


    } ///gerer l'envoie d'email
    public int verif_double_conn(String token ){

        int h=0;

        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `token` FROM `membreConnected` WHERE `token`='"+token+"'");



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



    public updorgane getOrganeById(int id){
        System.out.println("  get organes start");

        updorgane updorgane = new updorgane();


        try{


            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/synes_db", "root", "");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM `organe` WHERE `id`='"+id+"'");



            while(rs.next()){


                updorgane.setId(rs.getInt("id"));
                updorgane.setNom(rs.getString("nom"));
                updorgane.setDescription(rs.getString("description"));
                updorgane.setFondAlloue(rs.getInt("fondAlloue"));
                updorgane.setUpduniv(getUniversityById(rs.getInt("idUniversite")));

            }

        }
        catch (Exception exc){
            System.out.println(exc+"  error connect listOrganes");
        }
        System.out.println("  list well getted");

        return updorgane;
    }

}



