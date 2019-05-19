import nettySerialization.message.FileMessage;
import nettySerialization.message.SenderType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileMessageTest {

    FileMessage fm;

    @Before
    public void init() {
    fm = new FileMessage("myClientFile.txt", SenderType.CLIENT);
    }

    @Test
    public void checkByteSize() {
        int[] chunkSizesExp = {8, 8, 8, 6};
        int[] chunkSizeAct = new int[4];
        for (int i = 0; i < chunkSizeAct.length; i++) {
            fm.setBytesChunk();
            byte[] bytes = fm.getBytesChunk();
            chunkSizeAct[i] = bytes.length;
            try {
                Files.write(Paths.get("clientstorage/newClientFile"), bytes, StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Assert.assertArrayEquals(chunkSizesExp, chunkSizeAct);
    }

}



