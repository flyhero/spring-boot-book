package cn.iflyapi;

import cn.iflyapi.dao.CustomerMapper;
import cn.iflyapi.entity.Customer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDruidMybatisApplicationTests {

	@Autowired
	private CustomerMapper customerMapper;


	@Test
	public void testSave(){
		Customer customer = new Customer();
		customer.setFirstName("fei");
		customer.setLastName("wang");
		Assert.assertNotNull(customer);
		customerMapper.insertSelective(customer);
		System.out.println(customer.toString());
	}

	@Test
	public void testFind(){
		Customer customer = customerMapper.selectByPrimaryKey(1L);
		Assert.assertNotNull(customer);
		System.out.println(customer.toString());
	}
	@Test
	public void testFindAll(){
		PageHelper.startPage(1,2);
		List<Customer> list = customerMapper.findAll();
		PageInfo pageInfo = new PageInfo(list);
		Assert.assertNotNull(list);
		System.out.println(pageInfo.toString());
		list.forEach(customer -> {
			System.out.println(customer.toString());
		});


		PageInfo<Customer> page = PageHelper.startPage(2, 2).doSelectPageInfo(()-> customerMapper.findAll());
		Assert.assertNotNull(page);
		System.out.println(page.getPageSize());
		System.out.println(page.toString());
	}


}
