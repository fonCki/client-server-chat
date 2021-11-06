package shared.transferobjects;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Avatar implements Serializable {
    private String fileName, extension;
    private byte[] rawAvatar;

    public Avatar() {
        this.fileName = "";
        this.extension = "jpeg";
        try {
            this.rawAvatar = giveMeRawAvatar( ImageIO.read(getClass().getResource("../../design/avatar_default.jpeg")));
        } catch (IOException e) {
            System.out.println("gran error");
            this.rawAvatar = null;
        }
    }

    public Avatar(BufferedImage bufferedImage, String file) {
        this.fileName = file.substring(0,file.lastIndexOf('.'));
        this.extension = file.substring(file.lastIndexOf('.') + 1, (file.length()));
        this.rawAvatar = giveMeRawAvatar(bufferedImage);
    }

    private byte[] giveMeRawAvatar(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            javax.imageio.ImageIO.write(bufferedImage, extension, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public Image getAvatar() {
        BufferedImage bufferedAvatar = null;
        try {
            bufferedAvatar = javax.imageio.ImageIO.read(new ByteArrayInputStream(rawAvatar));
            Image image = SwingFXUtils.toFXImage(bufferedAvatar, null);
            return image;
        } catch (IOException e) {
        }
        return new Image(getClass().getResourceAsStream("../../design/avatar_default.jpeg"));
    }

}
