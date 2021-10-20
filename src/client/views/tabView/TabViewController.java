package client.views.tabView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import client.views.main.tools.MessageList;
import client.views.main.tools.MessageListItem;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class TabViewController implements ViewController {
    @FXML private TextArea messageTextArea;
    @FXML private TextArea messageTextField;
    @FXML private Button onSendButton;
    @FXML private VBox vBox;
    @FXML private Text receiverNickNameText;
   // @FXML private Circle avatarCircle;

    private User receiver;
    private TabViewModel tabViewModel;
    private String messageContent;



    public void initialize() {
        onSendButton.disableProperty().bind(Bindings.isEmpty(messageTextField.textProperty()));

    }

    private void setText(Message message) {
        System.out.println(message + " lalaland");
        if (message != null) {
            System.out.println("Hiiiiiiii");
            messageContent += "\n" +
                    message.getSender().getNickName() + "> " +
                    message.getContent();
            messageTextArea.setText(messageContent);
        } else {
            messageContent = "";
            messageTextArea.setText(messageContent);
            System.out.println("HUUUUUU");
        }

    }

    private void onNewMessage(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        if ((newMessage.getReceiver() == null) && receiver == null) {
            setText(newMessage);
        } else if (newMessage.getReceiver() != null && receiver != null) {
            if (newMessage.getReceiver().equals(receiver) && newMessage.getSender().equals(tabViewModel.getIdentity())) {
                setText(newMessage);
            } else if (newMessage.getReceiver().equals(tabViewModel.getIdentity()) && newMessage.getSender().equals(receiver)) {
                setText(newMessage);
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



        if (receiver == null) {
            vBox.setVisible(false);
            vBox.setManaged(false);
        } else {
            receiverNickNameText.setText(receiver.getNickName());
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
