package com.df.mybatis.mapping;

import com.df.mybatis.session.Configuration;
import lombok.Data;

import java.util.Map;

// 映射语句类
// sql信息记录对象,记录包括：SQL类型，SQL语句，入参类型，出参类型等
@Data
public class MappedStatement {
    private Configuration configuration;
    private String id;
    private SqlCommandType sqlCommandType;


    private String parameterType;
    private String resultType;
    private String sql;
    private Map<Integer, String> parameter;

    //public MappedStatement(){}

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType,
                       String parameterType, String resultType, String sql, Map<Integer, String> parameter) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlCommandType = sqlCommandType;

            mappedStatement.parameterType = parameterType;
            mappedStatement.resultType = resultType;
            mappedStatement.sql = sql;
            mappedStatement.parameter = parameter;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }
    }

    // 省略set.get
}
