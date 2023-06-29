package tracy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void start(){
        //主线程池
        EventLoopGroup bossPool=new NioEventLoopGroup();
        //副线程池
        EventLoopGroup workPool=new NioEventLoopGroup();
        //用于监听端口
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(bossPool,workPool)//放入两个线程池
                .channel(NioServerSocketChannel.class)//指定channel
                .childHandler(new ChannelInitializer<SocketChannel>() {//初始化channel
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
        //监听端口
        ChannelFuture future=bootstrap.bind(8080);
    }
}
