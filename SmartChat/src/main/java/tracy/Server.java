package tracy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import tracy.handler.WebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    //用于保存映射关系
    public static final Map<String,Channel> USERS=new ConcurrentHashMap<>();
    //添加一个channel组，用于实现群聊一对多通信
    public static final ChannelGroup CHANNEL_GROUP=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //启动服务器
    public static void start () throws InterruptedException{
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
                        //获取pipeline，pipeline的工作是基于责任链模式
                        ChannelPipeline pipeline=socketChannel.pipeline();
                        //添加一些handler
                        //http编码解码器
                        pipeline.addLast(new HttpServerCodec())
                                //对大数据量的支持
                                .addLast(new ChunkedWriteHandler())
                                //对http消息进行聚合
                                .addLast(new HttpObjectAggregator(1024*24))
                                //对websocket进行支持
                                .addLast(new WebSocketServerProtocolHandler("/"))
                                //websocket具体怎么处理，需要自定义
                                .addLast(new WebSocketHandler());
                    }
                });
        //监听端口
        bootstrap.bind(8080);
    }
}
