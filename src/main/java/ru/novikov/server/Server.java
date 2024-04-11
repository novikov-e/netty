package ru.novikov.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static void main( String[] args ) {
        // Для создания сервера создаётся два пула потоков
        // Для подключающихся клиентов
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // Для всего сетевого взаимодействия
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Конфигурация сервера
            ServerBootstrap bootstrap = new ServerBootstrap();
            // Назначение пулов потоков
            bootstrap.group(bossGroup, workerGroup)
                    // Назначение канала для подключения
                    .channel(NioServerSocketChannel.class)
                    // При  подключении вся информация лежит в SocketChannel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //Добавление Handler в цепочку
                            socketChannel.pipeline().addLast(
                                    new StringDecoder(),
                                    new StringEncoder(),
//                                    new MainHandler(),
                                    new SimpleMainHandler());
                        }
                    });
            // Запуск сервера на порту 8189
            // В ChannelFuture содержится вся информация о сервере
            ChannelFuture future = bootstrap.bind(8189).sync();
            // Ожидание выключения сервера
            future.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Удаление пулов потоков при выключении сервера
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
