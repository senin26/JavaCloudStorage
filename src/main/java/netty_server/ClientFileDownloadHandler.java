package netty_server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import message.FileMessage;
import message.RequestMessage;
import message.SenderType;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientFileDownloadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientFileDownloadHandler: Client connected...");
        try {
            if (msg == null) {
                return;
            }
            else if (msg instanceof RequestMessage) {
                    RequestMessage rm = (RequestMessage) msg; // in case of downloading files from the server
                if (Files.exists(Paths.get("serverstorage/".concat(rm.getFileName())))) {
                    FileMessage fm = new FileMessage(rm.getFileName(), SenderType.SERVER);
                    System.out.println("File message is created! " + fm.getFileName() + " " + fm.getSenderType());
                    ctx.writeAndFlush(fm);
                    //ctx.flush();
                    //ctx.close();
                }
            }
            //else ctx.fireChannelRead(msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
