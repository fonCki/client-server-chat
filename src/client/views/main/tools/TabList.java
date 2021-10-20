package client.views.main.tools;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabList {
    private class MyTab {
        private String ID;
        private Tab tab;
        @FXML private TextArea textArea;
        @FXML private HBox hBox;

        public MyTab(String ID, String nickName) {
            this.tab = new Tab(nickName);
            this.ID = ID;
          //  try {
           /*     FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.load(getClass().getResource("../test.fxml"));
                ViewController viewController = (ViewController) fxmlLoader.getController();
                this.tab = (Tab) fxmlLoader.load();
                viewController.init(viewHandler, viewModelFactory, stage, null);


            */
             //   this.tab = (Tab) FXMLLoader.load(this.getClass().getResource("../test.fxml"));

          //  } catch (IOException e) {
          //      e.printStackTrace();
          //  }
            tab.setText(nickName);
        }


        public String getID() {
            return ID;
        }

        public Tab getTab() {
            return tab;
        }
    }

    private List<MyTab> tabList;

    public TabList() {
        this.tabList = new ArrayList<MyTab>();
    }

    public void addTab(User user) {
        if (!existTab(user.getID())) {
            MyTab myTab = new MyTab(user.getID(), user.getNickName());
            tabList.add(myTab);
        }
    }

    public void removeTab(String ID) {
        for (MyTab myTab: tabList) {
            if (myTab.getID().equals(ID)) {
                tabList.remove(myTab);
                break;
            }
        }
    }

    public Tab getTab(String ID) {
        for (MyTab myTab: tabList) {
            if (myTab.getID().equals(ID)) {
                return myTab.getTab();
            }
        }
        return null;
    }

    public boolean existTab(String ID) {
        return !(getTab(ID) == null);
    }

    public List<MyTab> getTabList() {
        return tabList;
    }

    public int getNumberOfTabs() {
        return tabList.size();
    }
}
