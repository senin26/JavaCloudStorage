package basic_client.controller;

import basic_client.network.service.NetworkClientService;
import common.NetworkClientServiceSupplier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import message.AbstractMessage;
import message.FileMessage;
import message.RequestMessage;
import message.SenderType;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    TextField tfClientFileName;

    @FXML
    TextField serverFileName;

    @FXML
    ListView<String> filesList;

    static {
        NetworkClientServiceSupplier.newInstance();
    }

    NetworkClientService networkClientService = NetworkClientServiceSupplier.getInstance().getNetworkClientService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        networkClientService.start();
        Thread t = new Thread(() -> {
                while (true) {
                   networkClientService.downloadFile();
                }
        });
        t.setDaemon(true);
        t.start();
        refreshLocalFilesList();
        /*try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    public void pressOnDownloadBtn(ActionEvent actionEvent) {
        if (tfClientFileName.getLength() > 0) {
            networkClientService.sendRequest(new RequestMessage(tfClientFileName.getText()));
            tfClientFileName.clear();
        }
    }

    public void pressOnUploadBtn(ActionEvent actionEvent) {
        if (tfClientFileName.getLength() > 0) {
            networkClientService.uploadFile(new FileMessage(tfClientFileName.getText(), SenderType.CLIENT));
            tfClientFileName.clear();
        }
    }

    public void refreshLocalFilesList() {
        if (Platform.isFxApplicationThread()) {
            try {
                filesList.getItems().clear();
                Files.list(Paths.get("clientstorage")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> {
                try {
                    filesList.getItems().clear();
                    Files.list(Paths.get("clientstorage")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
