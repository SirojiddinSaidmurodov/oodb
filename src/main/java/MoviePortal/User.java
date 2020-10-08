package MoviePortal;

import java.util.List;

public class User {
    private String name;
    private List<Rate> userRate;
    private String eMail;

    private String passwordHash;

    public User(String name, List<Rate> userRate, String eMail, String passwordHash) {
        this.name = name;
        this.userRate = userRate;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
    }
    public User(){};
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
                "name='" + name + '\'' +
                ", userRate=" + userRate +
                ", eMail='" + eMail + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
