package com.yyy.bookManager.dao;

//和数据库打交道

import com.yyy.bookManager.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

//使用@Mapper注解要定义成一个接口interface
//1.使用@Mapper将NewsDAO接口交给Spring进行管理
//2.不用写Mapper映射文件（XML）
//3.为这个DAO接口生成一个实现类，让别的类进行引用
@Mapper
public interface BookDao {
    String table_name = " book ";
    String insert_field = " name, author, price ";
    String select_field = " id, status, " + insert_field;


    //只需要在mapper中方法上加入@Select()，然后在括号中写入需要实现的sql语句即可
    @Select({"select", select_field, "from", table_name})
    List<Book> selectAll();

    //@Insert在实体类的Mapper类里,注解保存方法的SQL语句.@Insert是直接配置SQL语句
    @Insert({"insert into", table_name, "(", insert_field,
            ") values (#{name},#{author},#{price})"})
    int addBook(Book book);


    @Update({"update ", table_name, " set status=#{status} where id=#{id}"})
    void updateBookStatus(@Param("id") int id, @Param("status") int status);



}
