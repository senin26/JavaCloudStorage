package netty_server;

import common.FilesPreparationUtility;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;

import static common.FilesPreparationUtility.filePathDest;
import static common.FilesPreparationUtility.fileName;

public class ByteToFileHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Files.write(Paths.get(FilesPreparationUtility.fullFileName.apply(filePathDest, fileName)), buf.array());
        ctx.fireChannelRead(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}