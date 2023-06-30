package tracy.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    //私聊
    PRIVATE(1),
    //群聊
    GROUP(2),
    //错误
    ERROR(0);

    private Integer type;

    public static MessageType match(Integer type){
        //遍历枚举类型的所有值，看输入的type否能与某一个匹配上
        for (MessageType value:MessageType.values()){
            if(value.getType().equals(type))return value;
        }
        //匹配不上，说明输入的type不是合法的枚举类
        return ERROR;
    }
}
