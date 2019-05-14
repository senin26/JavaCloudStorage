package netty_server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import message.FileMessage;

import java.nio.file.Files;
import java.nio.file.Paths;

// 'Upload' from the client side
public class ClientFileUploadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientFileUploadHandler: Client connected...");
        try {
            if (msg == null) {
                return;
            }
            else if (msg instanceof FileMessage) {
                    FileMessage fm = (FileMessage) msg; // in case of uploading files to the server
                    Files.write(Paths.get("serverstorage/".concat(fm.getFileName())),fm.getFileBytes());
                    ctx.writeAndFlush(fm);
                }
            else ctx.fireChannelRead(msg);
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
