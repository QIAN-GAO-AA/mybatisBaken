import com.df.mybatis.binding.MapperProxyFactory;
import com.df.mybatis.test.dao.IUserDao;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

//    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_MapperProxyFactory() {
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
        Map<String, String> sqlSession = new HashMap<>();
        sqlSession.put("com.df.mybatis.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中SQL 语句的操作；查询用户姓名");
        sqlSession.put("com.df.mybatis.test.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中SQL 语句的操作；查询用户年龄");


        IUserDao userDao = factory.newInstance(sqlSession);
        String res = userDao.queryUserName("1007");
        System.out.println("测试结果：" + res);

        String integer = userDao.queryUserAge("1007");
        System.out.println(integer);
    }

}
