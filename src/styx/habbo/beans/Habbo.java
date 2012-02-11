package styx.habbo.beans;

import java.util.Date;
import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Habbo {
    private Long id;
    private String name;
    private String figure;
    private String ssoTicket;
    private String ssoIp;
    private Date ssoExpires;
    private Fuserank fuserank;
    private Set<Ban> bans;
    private Set<Habbo> friends;
    private String realName;
    private String motto;
    private String gender;
    private Integer credits;
    private Integer activityPoints;

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
    
    public Fuserank getFuserank() {
        return fuserank;
    }
    
    public void setFuserank(Fuserank fuserank) {
        this.fuserank = fuserank;
    }

    public Set<Ban> getBans() {
        return bans;
    }

    public void setBans(Set<Ban> bans) {
        this.bans = bans;
    }

    public Set<Habbo> getFriends() {
        return friends;
    }

    public void setFriends(Set<Habbo> friends) {
        this.friends = friends;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(Integer activityPoints) {
        this.activityPoints = activityPoints;
    }
}
