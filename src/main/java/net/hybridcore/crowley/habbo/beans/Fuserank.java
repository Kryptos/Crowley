package net.hybridcore.crowley.habbo.beans;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Fuserank {
    private Long id;
    private String name;
    private Set<Fuseright> rights;

    public Fuserank() {}

    public Fuserank(String name) {
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

    public Set<Fuseright> getRights() {
        return rights;
    }

    public void setRights(Set<Fuseright> rights) {
        this.rights = rights;
    }
}
