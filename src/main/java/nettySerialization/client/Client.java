package nettySerialization.client;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import nettySerialization.common.Chunk;
import nettySerialization.common.ConnectionSettings;
import nettySerialization.common.NetworkClientServiceSupplier;
import nettySerialization.message.*;
import nettySerialization.network.service.NetworkClientService;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;

public class Client {

        private static Socket socket;
        private static ObjectOutput out;
        private static ObjectInput in;

    static {
        NetworkClientServiceSupplier.newInstance();
    }



    public static void main(String[] args) {
        /*String pathName = "clientstorage/".concat("myFile.txt");
        String str = "This is a fucking cool file";
        try {
            Files.createFile(Paths.get(pathName));
            Path file = Paths.get(pathName);
            System.out.println("length before " + Files.newByteChannel(Paths.get(pathName)).size());
            Files.write(Paths.get(pathName), str.getBytes(), StandardOpenOption.WRITE);
            System.out.println("length after " + Files.newByteChannel(Paths.get(pathName)).size());
            System.out.println(file.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        NetworkClientService networkClientService = NetworkClientServiceSupplier.getInstance().getNetworkClientService();

            networkClientService.start();
            //networkClientService.uploadFile(new FileMessage("bigMovieC.avi", SenderType.CLIENT));
            networkClientService.sendRequest(new RequestMessage("bigMovieC.avi"));
            networkClientService.downloadFile();

    }
}


//todo
/*
* 1) enum Request
* UPLOAD_REQUEST
* DOWNLOAD_REQUEST
*
* */