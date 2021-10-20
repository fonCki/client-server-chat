package shared.transferobjects;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRequest implements Serializable {
    private String nickName;
    transient BufferedImage avatar;

    public UserRequest(String nickName, BufferedImage avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }
}