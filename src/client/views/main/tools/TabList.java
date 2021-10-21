package client.views.main.tools;

import javafx.scene.control.Tab;
import shared.transferobjects.User;
import java.util.ArrayList;
import java.util.List;

public class TabList {
    private class MyTab {
        private String ID;
        private Tab tab;

        public MyTab(String ID, String nickName) {
            this.tab = new Tab(nickName);
            this.ID = ID;
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
