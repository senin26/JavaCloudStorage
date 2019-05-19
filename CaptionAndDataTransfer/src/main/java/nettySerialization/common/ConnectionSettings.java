package nettySerialization.common;

public final class ConnectionSettings {

    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8189;

    public static final String getIP_ADDRESS() {
        return IP_ADDRESS;
    }

    public static final int getPORT() {
        return PORT;
    }
}
