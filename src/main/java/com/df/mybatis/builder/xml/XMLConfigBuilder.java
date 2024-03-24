package com.df.mybatis.builder.xml;

import com.df.mybatis.builder.BaseBuilder;
import com.df.mybatis.io.Resources;
import com.df.mybatis.mapping.MappedStatement;
import com.df.mybatis.mapping.SqlCommandType;
import com.df.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// xml配置构建器，建造者模式，继承BaseBuilder
// 对xml里的配置进行解析，，如mapper, select等等
public class XMLConfigBuilder extends BaseBuilder {
    private Element root;

    public XMLConfigBuilder(Reader reader) {
        // 调用父类初始化Configuration，因为xml解析完毕需要通过config类将代理类注册进去
        super(new Configuration());
        // 2. dom4j 处理 xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析配置:标签、id、sql、映射器等
     */
    public Configuration parse() {
        // 解析映射器
        try {
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;
    }

    // 将xml中的配置解析出来存储到对应的实体类中
    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            String resource = e.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();

            // 命名空间
            String namespace = root.attributeValue("namespace");

            // select
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配参数
                Map<Integer, String> paramter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    paramter.put(i, g2);
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();

                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

                MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType,
                        parameterType, resultType, sql, paramter).build();
                // 添加解析SQL
                configuration.addMappedStatement(mappedStatement);

            }
            // 注册代理注册器
            configuration.addMapper(Resources.classForName(namespace));
        }
    }
}