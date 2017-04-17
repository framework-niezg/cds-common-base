package com.zjcds.common.base.mybatis;

/**
 * 自定义枚举类型处理器支持的枚举必须实现的接口
 * @author zhegang.nie
 * @date 2015/12/11 0011
 */
public interface IEnumValueType<T,E extends Enum<E>> {
    /**
     * 需要持久化值的类型信息
     * @return
     */
    public Class<T> getPersistValueType();

    /**
     * 获取持久化的值
     * @return
     */
    public T getPersistValue();

    /**
     * 持久化值对应的枚举值
     * @param value
     * @return
     */
    public E getEnumValue(T value);

}
