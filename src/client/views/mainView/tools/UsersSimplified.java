package client.views.mainView.tools;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import shared.transferobjects.User;

public class UsersSimplified {
    private String ID;
    private String nickName;
    private ImageView avatar;
    private Circle circle;


    public UsersSimplified(User user) {
        this.ID = user.getID();
        this.nickName = user.getNickName();
        this.avatar = new ImageView(user.getAvatar());
        this.circle = new Circle(20,20,20);
        this.circle.setFill(new ImagePattern(user.getAvatar()));
    }

    public String getID() {
        return ID;
    }

    public String getNickName() {
        return nickName;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "UsersSimplified{" +
                "ID='" + ID + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar=" + avatar +
                ", circle=" + circle +
                '}';
    }
}
