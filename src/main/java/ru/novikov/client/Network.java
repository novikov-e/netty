package ru.novikov.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Network {
    private SocketChannel channel;
    private Callback onMessageRecievedCallback;

    public Network(Callback onMessageRecievedCallback) {
        this.onMessageRecievedCallback = onMessageRecievedCallback;
        new Thread(() -> {
            // Пул потоков на обработку сетевых событий
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(),
                                        new SimpleChannelInboundHandler<String>() {
                                            @Override
                                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                                System.out.println(s);
                                                if(onMessageRecievedCallback != null) {
                                                    onMessageRecievedCallback.callback(s);
                                                }
                                            }
                                        });
                            }
                        });
                ChannelFuture future = bootstrap.connect("localhost", 8189).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    public void sendMessage(String message) {
        channel.writeAndFlush(message);
    }
}
