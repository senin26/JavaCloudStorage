package common;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseFilesHandler {

    public static void getFile(InputStream in, OutputStream outputFileStream) throws Exception {
        int chunk;
        while ((chunk = in.read())!=-1) {
            outputFileStream.write(chunk);
        }
    }

    public static void sendFile(OutputStream outputSocketStream) throws Exception {
        outputSocketStream.write(FilesPreparationUtility.prepareFile());
    }

}
