package nettySerialization.network;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import nettySerialization.common.Chunk;
import nettySerialization.common.ConnectionSettings;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;

public class Network {
    private Socket socket;
    private ObjectOutput output;
    private ObjectInput input;

    public void start() {
        try {
            socket = new Socket(ConnectionSettings.getIP_ADDRESS(), ConnectionSettings.getPORT());
            output = new ObjectEncoderOutputStream(socket.getOutputStream());
            input = new ObjectDecoderInputStream(socket.getInputStream(), (Chunk.CHUNK_SIZE.getSizeBytes()+1024*1024));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutput getOutput() {
        return output;
    }

    public ObjectInput getInput() {
        return input;
    }
}
