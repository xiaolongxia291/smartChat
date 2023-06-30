package tracy.handler;

import io.netty.channel.ChannelHandlerContext;
import tracy.Server;
import tracy.util.Result;

public class JoinHandler {
    public static void execute(ChannelHandlerContext ctx){
        Server.CHANNEL_GROUP.add(ctx.channel());
        ctx.channel().writeAndFlush(Result.success("加入系统默认群聊成功"));
    }
}
