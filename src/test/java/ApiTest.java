import com.df.mybatis.binding.MapperRegistry;
import com.df.mybatis.session.SqlSession;
import com.df.mybatis.session.SqlSessionFactory;
import com.df.mybatis.session.defaults.DefaultSqlSessionFactory;
import com.df.mybatis.test.dao.ISchoolDao;
import com.df.mybatis.test.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_MapperProxyFactory() {
        // 1. 注册 Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.df.mybatis.test.dao");

        // 2. 从 SqlSession 工厂获取 Session
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 获取映射器代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        ISchoolDao schoolDao = sqlSession.getMapper(ISchoolDao.class);

        // 4. 测试验证
        String res = userDao.queryUserName("10001");

        // 4. 测试验证
        String school = schoolDao.querySchoolName("10001");


        logger.info("测试结果：{}", res);
        logger.info("school测试结果：{}", school);
    }

}