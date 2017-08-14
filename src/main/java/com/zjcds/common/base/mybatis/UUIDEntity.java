package com.zjcds.common.base.mybatis;

import com.zjcds.common.base.domain.BaseBean;
import com.zjcds.common.base.utils.UUIDUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * created date：2017-08-07
 * @author niezhegang
 */
@Getter
@Setter
@MappedSuperclass
public abstract class UUIDEntity extends BaseBean{
    //初始化赋值一个uuid，等同于为新建实体对象赋一个id值
    @Id
    private String id = UUIDUtils.randomUUID();

}
