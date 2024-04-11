package ru.novikov.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// При приёме сообщений можно настраивать цепочки Handler которые служат для обработки входящих данных
// Бывают входящие(распаковывают сообщение) и исходящие(упаковывают сообщения)
public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelRegistered()");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelUnregistered()");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelActive()");
        System.out.println("Клиент подключился");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelInactive()");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("MainHandler - channelRead()");
        System.out.println("Получено сообщение");
        // Данные приходят и уходят ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        // Чтение
        while (buf.readableBytes() > 0) {
            System.out.println((char) buf.readByte());
        }
        // Освобождение буфера
        buf.release();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelReadComplete()");

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("MainHandler - userEventTriggered()");

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MainHandler - channelWritabilityChanged()");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("MainHandler - exceptionCaught()");

    }
}
