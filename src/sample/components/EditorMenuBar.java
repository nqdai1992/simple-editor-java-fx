package sample.components;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import sample.EventManage;

public class EditorMenuBar {
    private javafx.scene.control.MenuBar menuBar;
    private EventManage eventManage;
    private static final String os = System.getProperty("os.name");
    public enum EventNames {
        OPEN_FILE("file.open"),
        NEW_FILE("file.new"),
        SAVE_FILE("file.save"),
        CLOSE_FILE("file.close");
        private String name;
        EventNames (String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public EditorMenuBar(EventManage evtManage) {
        eventManage = evtManage;
        menuBar = new javafx.scene.control.MenuBar();
        if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
        }
        menuBar.getMenus().add(createMenuFile());
    }

    public javafx.scene.control.MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu createMenuFile () {
        Menu menuFile = new Menu("File");

        MenuItem menuNewFile = new MenuItem("New");
        menuNewFile.setOnAction(eventManage.getEvent(EventNames.NEW_FILE.getName()));

        MenuItem menuOpenFile = new MenuItem("Open");
        menuOpenFile.setOnAction(eventManage.getEvent(EventNames.OPEN_FILE.getName()));

        MenuItem menuSaveFile = new MenuItem("Save");
        menuSaveFile.setOnAction(eventManage.getEvent(EventNames.SAVE_FILE.getName()));

        MenuItem menuCloseFile = new MenuItem("Close");
        menuCloseFile.setOnAction(eventManage.getEvent(EventNames.CLOSE_FILE.getName()));

        ObservableList<MenuItem> menuBoard = menuFile.getItems();
        menuBoard.addAll(menuNewFile, menuOpenFile, menuSaveFile ,menuCloseFile);
        return menuFile;
    }


}
