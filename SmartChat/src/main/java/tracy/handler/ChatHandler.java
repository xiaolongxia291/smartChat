package tracy.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import tracy.Server;
import tracy.command.Command;
import tracy.command.Message;
import tracy.command.MessageType;
import tracy.command.Result;

public class ChatHandler {
    //用户聊天逻辑
    public static void execute(Command command, ChannelHandlerContext ctx){
        try{
            Message message=command.getMessage();
            //按不同聊天类型进行处理
            switch(MessageType.match(message.getType())){
                //私聊
                case PRIVATE: {
                    //信息接收对象为空
                    String target=message.getTarget();
                    Channel channel=Server.USERS.get(target);
                    if(target==null||target.isEmpty()){
                        ctx.channel().writeAndFlush(Result.fail("接收者信息为空，请确认后再试"));
                        return;
                    }
                    //信息接收对象不存在
                    else if(channel==null){
                        ctx.channel().writeAndFlush(Result.fail("接收者"+target+"不存在，请确认后再试"));
                        return;
                    }
                    //信息接收对象下线了
                    else if(!channel.isActive()){
                        ctx.channel().writeAndFlush(Result.fail("接收者"+target+"已下线，请确认后再试"));
                        return;
                    }
                    else{
                        channel.writeAndFlush(Result.success("私聊消息（"+command.getNickname()+")",message.getContent()));
                    }
                };break;
                //群聊
                case GROUP: {
                    Server.CHANNEL_GROUP.writeAndFlush(Result.success("群聊消息（"+command.getNickname()+")",message.getContent()));
                };break;
                default: ctx.channel().writeAndFlush(Result.fail("不支持的TYPE"));
            }
        }catch (Exception e){
            e.printStackTrace();
            ctx.channel().writeAndFlush(Result.fail("发送消息格式错误，请确认后再试"));
        }

    }
}
