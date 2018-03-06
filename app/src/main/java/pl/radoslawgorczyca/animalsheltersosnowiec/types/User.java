package pl.radoslawgorczyca.animalsheltersosnowiec.types;

import pl.radoslawgorczyca.animalsheltersosnowiec.security.AESCrypt;

/**
 * Created by Ebicom-RG on 06.03.2018.
 */

public class User {

    private long idUser;
    private String email;
    private String name;
    private String surname;
    private String password;

    public User(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        try {
            this.password = AESCrypt.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
