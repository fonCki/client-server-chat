package client.views.main.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.util.ArrayList;
import java.util.List;

public class TabList {
    private class MyTab {
        private String ID;
        private Tab tab;
        @FXML private TextArea textArea;
        @FXML private HBox hBox;

        public MyTab(String ID, String nickName) {
            this.ID = ID;
            this.tab = new Tab(nickName);
            textArea = new TextArea();
            hBox = new HBox();
            tab.setContent(hBox);
           hBox.getChildren().add(textArea);

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
