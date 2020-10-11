package br.ufc.quixada.dsdm.trabalho4.Model;

import java.io.Serializable;

public class User extends Identification {

    private String email;

    public User() {
        super();
    }

    public User(String id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
