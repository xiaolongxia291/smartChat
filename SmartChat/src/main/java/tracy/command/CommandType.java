package tracy.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandType{
    //指令：建立连接
    CONNECTION(1),
    //指令：聊天
    CHAT(2),
    //指令：加入群聊
    JOIN(3),
    //指令：错误指令
    ERROR(0),
    ;

    private final Integer code;

    public static CommandType match(Integer code){
        //遍历枚举类型的所有值，看输入的code是否能与某一个匹配上
        for (CommandType value:CommandType.values()){
            if(value.getCode().equals(code))return value;
        }
        //匹配不上，说明输入的指令不是合法的枚举类
        return ERROR;
    }
}