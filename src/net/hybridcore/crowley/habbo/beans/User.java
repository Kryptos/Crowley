package net.hybridcore.crowley.habbo.beans;

import java.util.Date;
import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class User {
    private Long id;
    private String email;
    private Date signedUp;
    private String registrationIp;
    private String currentIp;
    private Set<Habbo> habbos;

    public User() {}

    public User(String email) {
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getSignedUp() {
        return signedUp;
    }

    public void setSignedUp(Date signedUp) {
        this.signedUp = signedUp;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }

    public Set<Habbo> getHabbos() {
        return habbos;
    }

    public void setHabbos(Set<Habbo> habbos) {
        this.habbos = habbos;
    }
}
