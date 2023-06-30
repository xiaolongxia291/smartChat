package tracy.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    //用户昵称
    private String nickname;
    //指令
    private Integer code;
    //消息
    private Message message;
}
