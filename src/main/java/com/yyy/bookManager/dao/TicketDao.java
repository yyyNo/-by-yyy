package com.yyy.bookManager.dao;


import com.yyy.bookManager.model.Ticket;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TicketDao {

    String table_name = " ticket ";
    String insert_field = " userId, ticket, expiredAt ";
    String select_field = " id, " + insert_field;

    //当我在service层的代码调用addUser方法时，就相当于对数据库进行查询操作
    @Insert({"insert into", table_name, "(", insert_field,
        ") values (#{userId},#{ticket},#{expiredAt})"})
    int addTicket(Ticket ticket);

    @Select({"select", select_field, "from", table_name, "where userId=#{uid}"})
    Ticket selectByUserId(int uid);

    @Select({"select", select_field, "from", table_name, "where ticket=#{t}"})
    Ticket selectByTicket(String t);

    @Delete({"delete from", table_name, " where id=#{tid}"})
    void deleteTicketById(int tid);

    @Delete({"delete from", table_name, " where ticket=#{t}"})
    void deleteTicket(String t);


}
