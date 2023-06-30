package tracy.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import tracy.Server;
import tracy.command.Command;
import tracy.command.Result;

public class ConnectionHandler {
    //用户上线处理逻辑
    public static void execute(Command command, ChannelHandlerContext ctx){
        //容错处理：避免相同昵称的用户重复上线
        if(Server.USERS.containsKey(command.getNickname())){
            ctx.channel().writeAndFlush(Result.fail("该用户已上线，请更换昵称后重试！"));
            ctx.channel().disconnect();
            return;
        }
        //将用户加入到服务端的映射队列中
        Server.USERS.put(command.getNickname(),ctx.channel());
        //返回一条表示用户上线成功的消息
        ctx.channel().writeAndFlush(Result.success("与服务端连接建立成功！"));
        //再以json字符串的形式返回当前在线的用户列表
        ctx.channel().writeAndFlush(Result.success(JSON.toJSONString(Server.USERS.keySet())));
    }
}
