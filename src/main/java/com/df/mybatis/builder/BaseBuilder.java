package com.df.mybatis.builder;

import com.df.mybatis.session.Configuration;

// 构造器基类
public class BaseBuilder {

    // 配置
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    // 获取configuration
    public Configuration getConfiguration() {
        return configuration;
    }
}