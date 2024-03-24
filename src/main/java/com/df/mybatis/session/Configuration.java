package com.df.mybatis.session;

import com.df.mybatis.binding.MapperRegistry;
import com.df.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    // 映射注册机
    protected MapperRegistry mapperRegistry = new MapperRegistry();

    // 映射的语句，存在map里
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }
}
