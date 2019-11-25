package com.yyy.bookManager.biz;
/**
 * 我们用了两个额外的依赖包，请见pom.xml文件，jodatime和common.lang3，jodatime提供了
 * 更加好用的时间操作，用来代替java.util.date。common.lang3是一个经典的项目，封装了
 * 一大批常用的工具类，我们主要使用StringUtils这个工具类，里面封装了大量常用的考虑了
 * null情况的String操作，不会引发String引起的NullPointerException。
 */

import com.yyy.bookManager.model.Ticket;
import com.yyy.bookManager.model.User;
import com.yyy.bookManager.model.exceptions.LoginRegisterException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yyy.bookManager.service.TicketService;
import com.yyy.bookManager.service.UserService;
import com.yyy.bookManager.utils.ConcurrentUtils;
import com.yyy.bookManager.utils.MD5;
import com.yyy.bookManager.utils.TicketUtils;

import java.util.Date;

/**
 * 登录方法两个参数用户email和password。密码检查的方法是将password散列，然后与数据库
 * 中村的加密密码对比。如果用户登录成功后，就会去数据库找用户的t票，进行一系列检查后，
 * 将t票更新为最新的t票，然后将用户信息加入ConcurrentUtils中，供HostHolder使用。最后
 * 返回t票的内容。这个类里我们持有了之前封装的UserService 和 TicketService，
 * 并直接用里面的方法很方便的操作数据库。
 */

  //这里注册的逻辑跟登录的逻辑其实是比较相似的，登出也是简单的将用户的T票从数据库删除

@Service
public class LoginBiz {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    /**
     * 登录逻辑，先检查邮箱和密码，然后更新t票。
     * @return 返回最新t票
     * @throws Exception 账号密码错误
     */
    public String login(String email, String password){

        User user = userService.getUser(email);

        //登入信息检查
        if (user == null){ throw new LoginRegisterException("邮箱不存在");}
        if (!StringUtils.equals(MD5.next(password),user.getPassword())){ throw new LoginRegisterException("密码不正确");}

        //检查ticket
        Ticket t = ticketService.getTicket(user.getId());
        //如果没有ticket，就生成一个
        if (t == null){
            t = ticketService.getTicket(user.getId());
            ticketService.addTicket(t);
            return t.getTicket();
        }

        //ticket是否过期
        if (t.getExpiredAt().before(new Date())){
            //删除
            ticketService.deleteTicket(t.getId());
        }

        t = TicketUtils.next(user.getId());
        ticketService.addTicket(t);

        ConcurrentUtils.setHost(user);
        return t.getTicket();
    }


    /**
     * 用户退出登录，只需要删除数据库中用户的t票
     * @param t
     */

    public void logOut(String t){ ticketService.deleteTicket(t);}

    /**
     * 注册一个用户，并返回用户t票
     *
     * @return 用户当前的t票
     */
    public String register(User user){
        //信息检查
        if (userService.getUser(user.getEmail()) != null) {
            throw new LoginRegisterException("邮箱已经被注册");
        }

        //密码加密
        String plain = user.getPassword();
        String md5 = MD5.next(plain);
        user.setPassword(md5);
        //数据库添加新用户
        userService.addUser(user);

        //添加完用户后生成t票
        Ticket ticket = TicketUtils.next(user.getId());
        //再数据库添加t票
        ticketService.addTicket(ticket);

        ConcurrentUtils.setHost(user);
        return ticket.getTicket();

    }



}
