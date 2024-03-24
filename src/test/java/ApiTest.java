import com.df.mybatis.io.Resources;
import com.df.mybatis.session.SqlSession;
import com.df.mybatis.session.SqlSessionFactory;
import com.df.mybatis.session.SqlSessionFactoryBuilder;
import com.df.mybatis.test.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void testA() throws IOException {

        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

        String res = iUserDao.queryUserInfoById("10001");
        System.out.println(res);
    }

}