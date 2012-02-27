package net.hybridcore.crowley.habbo.beans;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Integer soundLevel;
    private boolean moderator;

    private List<Integer> friendUpdates;
    private String lastOnline;
    private Set<Habbo> friendRequests;

    public Habbo() {
        this.friendUpdates = new ArrayList<Integer>();
    }

    public Habbo(String name) {
        this.name = name;
        this.friendUpdates = new ArrayList<Integer>();
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
    
    public void addFriend(Habbo friend) {
        if (!this.friends.contains(friend)) {
            this.friends.add(friend);
        }
    }
    
    public void removeFriend(Habbo friend) {
        if (this.friends.contains(friend)) {
            this.friends.remove(friend);
        }
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

    public Integer getSoundLevel() {
        return soundLevel;
    }

    public void setSoundLevel(Integer soundLevel) {
        this.soundLevel = soundLevel;
    }

    public boolean isOnline() {
        return Crowley.getHabbo().getSessions().isOnline(this.getId());
    }

    public void serializeMessenger(ServerMessage serverMessage) {
        this.serializeMessenger(serverMessage, false);
    }
    
    public void serializeMessenger(ServerMessage serverMessage, boolean search) {
        if (search) {
            serverMessage.append(this.getId().intValue());
            serverMessage.appendString(this.getName());
            serverMessage.appendString(this.getMotto());
            serverMessage.append(this.isOnline());
            serverMessage.append(this.inRoom());
            serverMessage.appendString("");
            serverMessage.append(false);
            serverMessage.appendString(this.getFigure());
            serverMessage.append(this.getLastOnline());
            serverMessage.appendString(this.getRealName());
        } else {
            serverMessage.append(this.getId().intValue());
            serverMessage.appendString(this.getName());
            serverMessage.append(true);
            serverMessage.append(this.isOnline());
            serverMessage.append(this.inRoom());
            serverMessage.appendString(this.getFigure());
            serverMessage.append(false);
            serverMessage.appendString(this.getMotto());
            serverMessage.appendString(this.getLastOnline());
            serverMessage.appendString(this.getRealName());
        }
    }

    public void friendRequiresUpdate(Integer id) {
        this.friendUpdates.add(id);
    }

    public void friendUpdated(Integer id) {
        if (this.friendUpdates.contains(id)) {
            this.friendUpdates.remove(id);
        }
    }

    public List<Integer> getFriendUpdates() {
        return friendUpdates;
    }

    public boolean inRoom() {
        return false; //TODO: In Room?
    }

    public String getLastOnline() {
        if (this.isOnline()) {
            return DateTime.now();
        }

        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Set<Habbo> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(Set<Habbo> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void isModerator(boolean moderator) {
        this.moderator = moderator;
        //TODO: Enable mod tools
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Habbo habbo = (Habbo) o;

        if (fuserank != null && habbo.fuserank != null && !habbo.fuserank.getId().equals(fuserank.getId())) return false;
        if (soundLevel != habbo.soundLevel) return false;
        if (activityPoints != null ? !activityPoints.equals(habbo.activityPoints) : habbo.activityPoints != null)
            return false;
        if (credits != null ? !credits.equals(habbo.credits) : habbo.credits != null) return false;
        if (figure != null ? !figure.equals(habbo.figure) : habbo.figure != null) return false;
        if (gender != null ? !gender.equals(habbo.gender) : habbo.gender != null) return false;
        if (id != null ? !id.equals(habbo.id) : habbo.id != null) return false;
        if (motto != null ? !motto.equals(habbo.motto) : habbo.motto != null) return false;
        if (name != null ? !name.equals(habbo.name) : habbo.name != null) return false;
        if (realName != null ? !realName.equals(habbo.realName) : habbo.realName != null) return false;
        if (ssoExpires != null ? !ssoExpires.equals(habbo.ssoExpires) : habbo.ssoExpires != null) return false;
        if (ssoIp != null ? !ssoIp.equals(habbo.ssoIp) : habbo.ssoIp != null) return false;
        if (ssoTicket != null ? !ssoTicket.equals(habbo.ssoTicket) : habbo.ssoTicket != null) return false;

        return true;
    }
}
