package nettySerialization.common;

import nettySerialization.network.service.NetworkClientService;

public class NetworkClientServiceSupplier {

    private static volatile NetworkClientServiceSupplier INSTANCE;
    private NetworkClientServiceFactory serviceFactory;

    public static NetworkClientServiceSupplier getInstance() {
        return INSTANCE;
    }

    public static NetworkClientServiceSupplier newInstance() {

        if (INSTANCE == null) {
            synchronized (NetworkClientServiceSupplier.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetworkClientServiceSupplier();
                }
            }
        }
        return INSTANCE;
    }

    private NetworkClientServiceSupplier() {
        initServiceFactory();
    }

    private void initServiceFactory() {
        serviceFactory = new NetworkClientServiceFactory();
    }

    public NetworkClientService getNetworkClientService() {
        return serviceFactory.getNetworkClientService();
    }
}
