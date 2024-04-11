package ru.novikov.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class SimpleMainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<Channel> channels = new ArrayList<>();
    private static int clientIndex = 1;
    private String clientName;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Получено сообщение: " + s);
        String message = String.format("[%s]: %s", clientName, s);
        for (Channel channel : channels) {
            channel.writeAndFlush(message);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("SimpleMainHandler - channelActive()");
        System.out.println("Клиент подключился");
        System.out.println(ctx);
        channels.add(ctx.channel());
        clientName = "Клиент #" + clientIndex;
        clientIndex++;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("MainHandler - exceptionCaught()");
    }
}
