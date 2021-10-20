package shared.transferobjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User implements Serializable {
    private String nickName;
    private String ip;
    private LocalDateTime created = LocalDateTime.now();
    private String ID;

    public User(String nickName) {
        this.ID = "#ID" +  ip.replace(".","") +  created.getNano() + (int) (nickName.hashCode() * Math.random());
        this.nickName = nickName;
        this.ip = "0.0.0.0";
    }

    public User(String nickName, String ip) {
        this.ID = "#ID" +  ip.replace(".","") +  created.getNano() + (int) (nickName.hashCode() * Math.random());
        this.ID.hashCode();
        this.nickName = nickName;
        this.ip = ip;
    }

    private User(String nickName, String ip, LocalDateTime created, String ID) {
        this.nickName = nickName;
        this.ip = ip;
        this.created = created;
        this.ID = ID;
    }


    public String getNickName() {
        return nickName;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getID() {
        return ID;
    }

    public User copy(){
        return new User(nickName, ip, created, ID);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        } else {
            return ((User) obj).nickName.equals(this.nickName) &&
                    ((User) obj).ip.equals(this.ip) &&
                    ((User) obj).created.equals(this.created)  &&
                    ((User) obj).ID.equals(this.ID);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                ", ID='" + ID + '\'' +
                '}';
    }
}
