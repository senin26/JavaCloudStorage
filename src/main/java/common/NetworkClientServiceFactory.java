package common;

import basic_client.network.repo.NetworkClientRepo;
import basic_client.network.service.NetworkClientService;

public class NetworkClientServiceFactory {

    private NetworkClientRepo networkClientRepo = new NetworkClientRepo();

    private NetworkClientService networkClientService = new NetworkClientService(networkClientRepo);

    public NetworkClientService getNetworkClientService() {
        return networkClientService;
    }
}
