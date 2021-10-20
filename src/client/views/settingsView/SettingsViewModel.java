package client.views.settingsView;

import client.model.MessageModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;

public class SettingsViewModel {
    private MessageModel messageModel;
    private StringProperty nickName;

    public SettingsViewModel(MessageModel messageModel) {
        this.nickName = new SimpleStringProperty();
        this.messageModel = messageModel;
    }


    public void newUser( ) {
        messageModel.newUser(nickName.getValue());
    }


    public StringProperty nickNameProperty() {
        return nickName;
    }
}
