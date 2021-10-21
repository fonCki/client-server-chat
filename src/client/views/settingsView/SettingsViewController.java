package client.views.settingsView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsViewController implements ViewController {
    private SettingsViewModel settingsViewModel;
    private ViewHandler viewHandler;
    @FXML private TextField nickNameTextField;
    @FXML private Button onOkButton;
    @FXML private Button openButton;
    @FXML private Circle circle;
    public Image avatar;
    private BufferedImage bufferedImage = null;




    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver, Message initMessage) {
        this.settingsViewModel = viewModelFactory.getSettingsViewModel();
        this.viewHandler = viewHandler;
        nickNameTextField.textProperty().bindBidirectional(settingsViewModel.nickNameProperty());

        avatar = new Image(getClass().getResourceAsStream("../../../design/Steve_Jobs_icon-icons.com_54132.png"));
        circle.setFill(new ImagePattern(avatar));
        try {
            bufferedImage = ImageIO.read(getClass().getResource("../../../design/avatar_default.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChooser fileChooser = new FileChooser();
        openButton.setOnAction((final ActionEvent e) -> {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                bufferedImage = ImageIO.read(file);
                                avatar = SwingFXUtils.toFXImage(bufferedImage, null);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        circle.setFill(new ImagePattern(avatar));
                    });

        onOkButton.disableProperty().bind(Bindings.isEmpty(nickNameTextField.textProperty()));

        nickNameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onOkButton(null);
            }
        });
    }

    public void onOkButton(ActionEvent actionEvent) {
        if (!(nickNameTextField.textProperty().getValue() == null)) {
            if (!(nickNameTextField.textProperty().getValue().equals(""))) {
                settingsViewModel.newUser(bufferedImage);
                viewHandler.openMainView();
            }
        }
    }
}
