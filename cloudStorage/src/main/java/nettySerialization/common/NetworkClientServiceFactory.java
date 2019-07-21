package nettySerialization.common;

import nettySerialization.network.repo.NetworkClientRepo;
import nettySerialization.network.service.NetworkClientService;

public class NetworkClientServiceFactory {

    private NetworkClientRepo networkClientRepo = new NetworkClientRepo();

    private NetworkClientService networkClientService = new NetworkClientService(networkClientRepo);

    public NetworkClientService getNetworkClientService() {
        return networkClientService;
    }
}
