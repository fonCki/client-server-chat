package client.views.tabView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class TabViewController implements ViewController {
    @FXML private TextArea messageTextArea;
    @FXML private TextArea messageTextField;
    @FXML private Button onSendButton;
    @FXML private VBox vBox;
    @FXML private Text receiverNickNameText;
    @FXML private Circle avatarCircle;

    private User receiver;
    private TabViewModel tabViewModel;
    private String messageContent;



    public void initialize() {
        onSendButton.disableProperty().bind(Bindings.isEmpty(messageTextField.textProperty()));

    }

    private void setText(Message message) {
        if (message != null) {
            messageContent += "\n" +
                    message.getSender().getNickName() + "> " +
                    message.getContent();
        } else {
            messageContent = "";
        }
        messageTextArea.setText(messageContent);
    }

    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        if ((newMessage.getReceiver() == null) && receiver == null) {
            setText(newMessage); // Send to ALL
        } else if (newMessage.getReceiver() != null && receiver != null) {
            if (newMessage.getReceiver().equals(receiver) && newMessage.getSender().equals(tabViewModel.getIdentity())) {
                setText(newMessage); // Send private if I sent
            } else if (newMessage.getReceiver().equals(tabViewModel.getIdentity()) && newMessage.getSender().equals(receiver)) {
                setText(newMessage); // Send private sent to me
            }

        }
    }



    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver, Message initMessage) {
        messageContent = "";
        messageTextArea.setText(messageContent);
        setText(initMessage);
        this.receiver = receiver;
        this.tabViewModel = viewModelFactory.getTabViewModel();
        tabViewModel.addListener("NEW_MESSAGE", this::onNewMessage);

        onSendButton.disableProperty().bind(Bindings.isEmpty(messageTextField.textProperty()));

        //Prepare the tab to be All
        if (receiver == null) {
            vBox.setVisible(false);
            vBox.setManaged(false);
        } else {
            receiverNickNameText.setText(receiver.getNickName());
            //Image avatarImg = new Image(getClass().getResourceAsStream("../../../design/avatar_default.jpeg"));
           // BufferedImage bufferedAvatar = null;
           // try {
           //     bufferedAvatar = javax.imageio.ImageIO.read(new ByteArrayInputStream(receiver.getRawAvatar()));
           //     avatarImg = SwingFXUtils.toFXImage(bufferedAvatar, null);
          //  } catch (IOException e) {
          //      e.printStackTrace();
          //  }
            avatarCircle.setFill(new ImagePattern(receiver.getAvatar()));
        }

        messageTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onSend(null);
            }
        });
    }


    public void onSend(ActionEvent actionEvent) {
        if (!(messageTextField.getText() == null)  &&
                (!(messageTextField.getText() == "")))
            tabViewModel.sendMessage(messageTextField.getText(), receiver);
            messageTextField.clear();
    }

}
