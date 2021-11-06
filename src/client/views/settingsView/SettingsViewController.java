package client.views.settingsView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.beans.binding.Bindings;
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
import shared.transferobjects.Avatar;
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
    private Avatar avatar;
    private Image steveJobs;
    private BufferedImage bufferedImage = null;


    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver, Message initMessage) {
        this.settingsViewModel = viewModelFactory.getSettingsViewModel();
        this.viewHandler = viewHandler;
        nickNameTextField.textProperty().bindBidirectional(settingsViewModel.nickNameProperty());


        steveJobs = new Image(getClass().getResourceAsStream("../../../design/Steve_Jobs_icon-icons.com_54132.png"));
        circle.setFill(new ImagePattern(steveJobs));
        try {
            bufferedImage = ImageIO.read(getClass().getResource("../../../design/avatar_default.jpeg"));
            avatar = new Avatar(bufferedImage, "avatar_default.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        openButton.setOnAction((final ActionEvent e) -> {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                bufferedImage = ImageIO.read(file);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            avatar = new Avatar(bufferedImage, file.getName());
                        }
                        circle.setFill(new ImagePattern(avatar.getAvatar()));
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
                settingsViewModel.newUser(avatar);
                viewHandler.openMainView();
            }
        }
    }
}
