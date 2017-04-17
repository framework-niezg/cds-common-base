package com.zjcds.common.base.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis自定义枚举类型转换器(目前支持Integer和String类型值)
 * @see IEnumValueType
 * @author zhegang.nie
 */
public class CustomEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

	private Class<E> type;

	public CustomEnumTypeHandler(Class<E> type) {
		if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
	}

	private void valid(E parameter){
		if(!(parameter instanceof IEnumValueType)){
			throw new IllegalArgumentException("使用CustomEnumTypeHandler必须实现IEnumValueType接口");
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		valid(parameter);
		IEnumValueType iEnumValueType = (IEnumValueType) parameter;
		if (jdbcType == null) {
			ps.setString(i, iEnumValueType.getPersistValue().toString());
		} else {
			ps.setObject(i, iEnumValueType.getPersistValue(), jdbcType.TYPE_CODE); // see r3589
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);

		return parseToEnum(s);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return parseToEnum(s);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String s = cs.getString(columnIndex);
		return parseToEnum(s);
	}

	private E parseToEnum(String value){
		if(StringUtils.isNoneBlank(value)){
			E element = type.getEnumConstants()[0];
			valid(element);
			IEnumValueType iEnumValueType = (IEnumValueType) element;
			if(iEnumValueType.getPersistValueType().equals(Integer.class)){
				return (E) iEnumValueType.getEnumValue(Integer.parseInt(value));
			}
			else{
				return (E) iEnumValueType.getEnumValue(value);
			}
		}
		else
			return null;
	}

}
