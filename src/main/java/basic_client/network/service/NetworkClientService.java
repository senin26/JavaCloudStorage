package basic_client.network.service;

import basic_client.network.Network;
import basic_client.network.repo.NetworkClientRepo;
import message.AbstractMessage;
import message.FileMessage;

public class NetworkClientService {

    private Network network;

    private NetworkClientRepo repo;

    public NetworkClientService(NetworkClientRepo repo) {
        this.repo = repo;
        network = new Network();
    }

    public void start() {
        network.start();
    }

    public void stop() {
       network.stop();
    }

    public boolean sendRequest(AbstractMessage msg) {
        return repo.sendMsg(network.getOutput(), msg);
    }

    public boolean uploadFile(FileMessage fMsg) {
        return repo.sendMsg(network.getOutput(), fMsg);
    }

    public boolean downloadFile() {
        return repo.getMessage(network.getInput());
    }


}
