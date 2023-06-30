package tracy.command;

import com.alibaba.fastjson2.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Result {
    private String name;//消息类型
    private LocalDateTime time;
    private String message;//消息内容

    public static TextWebSocketFrame fail(String message){
        return new TextWebSocketFrame(JSON.toJSONString(new Result("系统消息",LocalDateTime.now(),message)));
    }

    public static TextWebSocketFrame success(String message){
        return new TextWebSocketFrame(JSON.toJSONString(new Result("系统消息",LocalDateTime.now(),message)));
    }

    public static TextWebSocketFrame success(String name,String message){
        return new TextWebSocketFrame(JSON.toJSONString(new Result(name,LocalDateTime.now(),message)));
    }
}
