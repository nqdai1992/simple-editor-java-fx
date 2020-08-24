package sample.components;

import javafx.scene.control.TextArea;

public class Editor {
    public boolean modified = false;
    public TextArea textArea = new TextArea();
    public String filename = null;
    public String fullPath = null;

    public Editor(String filename) {
        this.filename = filename;
    }

    public Editor(String filename, String fullPath) {
        this(filename);
        this.fullPath = fullPath;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void setText (String text) {
        textArea.setText(text);
    }

    public String getText () {
        return textArea.getText();
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }
}
