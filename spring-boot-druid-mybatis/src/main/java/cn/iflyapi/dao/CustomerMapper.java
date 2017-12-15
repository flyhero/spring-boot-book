package cn.iflyapi.dao;

import cn.iflyapi.entity.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> findAll();

    @Select("select * from customer where last_name = #{lastName}")
    @Results({
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name")
    })
    Customer findByLastName(String lastName);

    @Insert("insert into customer(first_name,last_name) values (#{firstName},#{lastName})")
    void saveCustomer(Customer customer);

    @Update("update customer set first_name = #{firstName},last_name = #{lastName} where id = #{id}")
    void updateCustomer(Customer customer);

    @Delete("delete from customer where id = #{id}")
    void deleteCustomer(Long id);
}