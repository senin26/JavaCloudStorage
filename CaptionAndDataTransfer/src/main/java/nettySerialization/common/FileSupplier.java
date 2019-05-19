package nettySerialization.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSupplier {

    public static Path getFile(String fileName, StorageType type) throws NoSuchFileException {
        switch (type) {
            case SERVER:
                return Paths.get("servertorage/".concat(fileName));
            case CLIENT:
                return Paths.get("servertorage/".concat(fileName));
        }
        return null;
    };



}
