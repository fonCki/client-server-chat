package client.views.settingsView;

import client.model.MessageModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferobjects.Avatar;

import java.awt.image.BufferedImage;

public class SettingsViewModel {
    private MessageModel messageModel;
    private StringProperty nickName;

    public SettingsViewModel(MessageModel messageModel) {
        this.nickName = new SimpleStringProperty();
        this.messageModel = messageModel;
    }


    public void newUser(Avatar avatar) {
        messageModel.newUser(nickName.getValue(), avatar);
    }


    public StringProperty nickNameProperty() {
        return nickName;
    }
}
