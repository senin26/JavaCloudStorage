package nettySerialization.client;

import nettySerialization.common.NetworkClientServiceSupplier;
import nettySerialization.common.PathUtil;
import nettySerialization.message.FileMessage;
import nettySerialization.message.RequestMessage;
import nettySerialization.message.SenderType;
import nettySerialization.network.service.NetworkClientService;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {

        private static Socket socket;
        private static ObjectOutput out;
        private static ObjectInput in;

    static {
        NetworkClientServiceSupplier.newInstance();
    }




    public static void main(String[] args) {

        NetworkClientService networkClientService = NetworkClientServiceSupplier.getInstance().getNetworkClientService();

            networkClientService.start();
            String fileName = "bigMovieC.avi";
            networkClientService.uploadFile(new FileMessage(PathUtil.getAbsoluteSubpath()+"/clientStorage/"+"bigMovieC.avi", SenderType.CLIENT));
            /*networkClientService.sendRequest(new RequestMessage("bigMovieC.avi"));
            networkClientService.downloadFile();*/

    }
}


//todo
/*
* 1) enum Request
* UPLOAD_REQUEST
* DOWNLOAD_REQUEST
*
* */