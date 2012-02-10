package styx.habbo.beans;

import java.util.Date;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Habbo {
    private Long id;
    private String name;
    private User user;
    private String figure;
    private String ssoTicket;
    private String ssoIp;
    private Date ssoExpires;

    public Habbo() {}

    public Habbo(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getSsoTicket() {
        return ssoTicket;
    }

    public void setSsoTicket(String ssoTicket) {
        this.ssoTicket = ssoTicket;
    }

    public String getSsoIp() {
        return ssoIp;
    }

    public void setSsoIp(String ssoIp) {
        this.ssoIp = ssoIp;
    }

    public Date getSsoExpires() {
        return ssoExpires;
    }

    public void setSsoExpires(Date ssoExpires) {
        this.ssoExpires = ssoExpires;
    }
}
