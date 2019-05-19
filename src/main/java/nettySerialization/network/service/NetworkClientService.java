package nettySerialization.network.service;


import nettySerialization.message.AbstractMessage;
import nettySerialization.message.FileMessage;
import nettySerialization.message.RequestMessage;
import nettySerialization.network.Network;
import nettySerialization.network.repo.NetworkClientRepo;

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

    public boolean sendRequest(RequestMessage request) {
        return repo.sendRequest(network.getOutput(), request);
    }

    public boolean uploadFile(FileMessage fMsg) {
        return repo.sendFile(network.getOutput(), network.getInput(), fMsg);
    }

    public boolean downloadFile() {
        return repo.getFile(network.getInput(), network.getOutput());
    }


}
