package nettySerialization.common;

import java.io.File;

public final class PathUtil {
    public static String getAbsoluteSubpath() {
        return new File("").getAbsolutePath()+"/cloudStorage/storage";
    }
}
