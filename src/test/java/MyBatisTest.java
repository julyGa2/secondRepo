import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
///ga2board/webapp/WEB-INF/config/dispatcher-servlet.xml
///ga2board/src/main/resources/spring/context-datasource.xml
//@ContextConfiguration( locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@ContextConfiguration( locations ={"file:src/main/resources/spring/context-datasource.xml"})
public class MyBatisTest {
	
	@Autowired 
	private SqlSessionFactory sqlFactory;
	
	@Test 
	public void testFactory(){
		System.out.println(sqlFactory); 
	}
	
	@Test 
	public void testSession() throws Exception{
		try(SqlSession session = sqlFactory.openSession()){
			System.out.println(session); 
			}
		catch(Exception e){
			e.printStackTrace();
			} 
		}



}
