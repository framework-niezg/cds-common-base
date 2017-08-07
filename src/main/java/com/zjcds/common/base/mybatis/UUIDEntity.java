package com.zjcds.common.base.mybatis;

import com.zjcds.common.base.domain.BaseBean;
import com.zjcds.common.base.utils.UUIDUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * created date：2017-08-07
 * @author niezhegang
 */
@Getter
@Setter
public abstract class UUIDEntity extends BaseBean{
    //初始化赋值一个uuid，等同于为新建实体对象赋一个id值
    private String id = UUIDUtils.randomUUID();

}
