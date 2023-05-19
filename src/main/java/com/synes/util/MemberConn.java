package com.synes.util;

public class MemberConn {

    String email;
    String motdepasse;

    public MemberConn() {
    }
    public MemberConn(String email, String motdepasse) {
        this.email = email;
        this.motdepasse = motdepasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}
