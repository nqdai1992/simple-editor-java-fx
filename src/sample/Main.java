package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.components.Editor;
import sample.components.EditorMenuBar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        EventManage eventManage = new EventManage();
        BorderPane layout = new BorderPane();
        List<Editor> editors = new LinkedList<>();
        TabPane tabPane = new TabPane();

        eventManage.subscribe(
                EditorMenuBar.EventNames.SAVE_FILE.getName(),
                actionEvent -> {
                    int currentTabIndex = tabPane.getSelectionModel().getSelectedIndex();
                    Editor currEditor = editors.get(currentTabIndex);
                    try (FileOutputStream fos = new FileOutputStream(currEditor.getFullPath());
                         BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                        String text = currEditor.getText();
                        bos.write(text.getBytes());
                        bos.flush();
                    } catch (Exception e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
        );

        eventManage.subscribe(
                EditorMenuBar.EventNames.CLOSE_FILE.getName(),
                actionEvent -> System.out.println("Close file")
        );

        eventManage.subscribe(
                EditorMenuBar.EventNames.NEW_FILE.getName(),
                actionEvent -> {
                    Editor editor = new Editor("Untitle");
                    editors.add(editor);
                    Tab tab = new Tab();
                    tabPane.getTabs().add(tab);
                    tab.setText("New tab");
                    tab.setContent(editor.getTextArea());
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(tab);
                }
        );

        eventManage.subscribe(
                EditorMenuBar.EventNames.OPEN_FILE.getName(),
                actionEvent -> {
                    FileChooser fileChooser = new FileChooser();
                    File fileToOpen = fileChooser.showOpenDialog(null);
                    String absolutePath = fileToOpen.getAbsolutePath();
                    String fileName = fileToOpen.getName();
                    StringBuilder content = new StringBuilder();

                    try {
                        Scanner scanner = new Scanner(fileToOpen);
                        while (scanner.hasNextLine()) {
                            content.append(scanner.nextLine()).append(System.lineSeparator());
                        }
                        scanner.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Editor editor = new Editor(fileName, absolutePath);
                    editors.add(editor);
                    editor.setText(content.toString());

                    Tab tab = new Tab();
                    tabPane.getTabs().add(tab);
                    tab.setText(fileName);
                    tab.setContent(editor.getTextArea());
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(tab);
                    tab.setOnClosed(event -> {
                        editors.remove(editor);
                        System.out.println(editors.size());
                    });
                }
        );

        EditorMenuBar menuBar = new EditorMenuBar(eventManage);

        layout.setTop(menuBar.getMenuBar());
        layout.setCenter(tabPane);

        primaryStage.setTitle("Simple Editor");
        primaryStage.setScene(new Scene(layout, 600, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
