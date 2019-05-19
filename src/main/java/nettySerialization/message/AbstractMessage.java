package nettySerialization.message;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {
    protected String fileName;

    public AbstractMessage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
