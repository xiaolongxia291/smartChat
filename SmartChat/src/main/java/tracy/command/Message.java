package tracy.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message{
    //接收对象
    private String target;
    //内容
    private String content;
    //消息类型
    private Integer type;
}
