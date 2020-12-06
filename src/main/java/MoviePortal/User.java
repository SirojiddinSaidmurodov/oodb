/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

package MoviePortal;

import ObjModelAnalysis.annotations.Column;
import ObjModelAnalysis.annotations.Entity;
import ObjModelAnalysis.annotations.Id;
import ObjModelAnalysis.annotations.OneToMany;

import java.util.List;

@Entity
public class User implements ORMManagement.Entity<Long> {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    @OneToMany
    private List<Rate> userRate;
    @Column
    private String eMail;

    private String passwordHash;

    public User(String name, List<Rate> userRate, String eMail, String passwordHash) {
        this.name = name;
        this.userRate = userRate;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rate> getUserRate() {
        return userRate;
    }

    public void setUserRate(List<Rate> userRate) {
        this.userRate = userRate;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userRate=" + userRate +
                ", eMail='" + eMail + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
