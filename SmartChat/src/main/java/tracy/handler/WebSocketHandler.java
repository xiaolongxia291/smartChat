package tracy.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import tracy.command.*;
import tracy.command.Result;

/**
 * TextWebSocketFrame表示消息体为文本类型
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try{
            //将json形式的文本解析成Command类
            Command command= JSON.parseObject(msg.text(),Command.class);
            //每一种指令都定义一个对应的Handler来进行处理
            switch(CommandType.match(command.getCode())){
                case CONNECTION: ConnectionHandler.execute(command,ctx);break;
                case CHAT: ChatHandler.execute(command,ctx);break;
                case JOIN: JoinHandler.execute(ctx);break;
                default: ctx.channel().writeAndFlush(Result.fail("不支持的CODE"));
            }
        }catch (Exception e){
            e.printStackTrace();
            ctx.channel().writeAndFlush(Result.fail("发送消息格式错误，请确认后再试"));
        }
    }
}
