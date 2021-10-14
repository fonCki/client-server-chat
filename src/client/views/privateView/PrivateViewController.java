package client.views.privateView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;

public class PrivateViewController implements ViewController {
    PrivateViewModel privateViewModel;
    private User receiver;

    @FXML private TextArea messageTextArea;
    @FXML private TextArea userTextArea;
    @FXML private TextField messageTextField;



    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver) {

        this.receiver = receiver;
        this.privateViewModel = viewModelFactory.getPrivateViewModel();
        privateViewModel.setReceiver(receiver);
        userTextArea.textProperty().setValue(receiver.getID() + "\n" +
                                            receiver.getNickName() + "\n" +
                                            receiver.getIp() + "\n" +
                                            receiver.getCreated());

        messageTextArea.textProperty().bind(privateViewModel.messageProperty());
        privateViewModel.shareStage(stage);
        messageTextArea.textProperty().addListener( new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                }
        });
        }

    public void onSend(ActionEvent actionEvent) {
        privateViewModel.sendMessage(messageTextField.getText());
        messageTextField.clear();
    }
}

