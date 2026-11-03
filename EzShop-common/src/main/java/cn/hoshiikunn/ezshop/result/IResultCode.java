package cn.hoshiikunn.ezshop.result;

/**
 * 响应码接口
 * <p>
 * 通用响应码与各服务业务响应码的统一契约，
 * 各服务的业务枚举实现此接口，供 {@code BusinessException} 统一消费
 */
public interface IResultCode {

    /**
     * 响应码
     */
    Integer getCode();

    /**
     * 消息内容
     */
    String getMsg();
}
