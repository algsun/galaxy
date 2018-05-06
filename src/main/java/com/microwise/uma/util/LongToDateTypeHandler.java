package com.microwise.uma.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 类型转化
 * <pre>
 * Date => date.getTime():long
 * Date <= new Date(time:long)
 * </pre>
 *
 * java: Date
 * db: long
 *
 * @author gaohui
 * @date 13-4-28 12:53
 */
public class LongToDateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, date.getTime());
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String label) throws SQLException {
        long time = resultSet.getLong(label);
        return new Date(time);
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int index) throws SQLException {
        long time = resultSet.getLong(index);
        return new Date(time);
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        long time = callableStatement.getLong(i);
        return new Date(time);
    }
}
