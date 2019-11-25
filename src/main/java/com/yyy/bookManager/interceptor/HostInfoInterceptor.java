package com.yyy.bookManager.interceptor;

import com.yyy.bookManager.model.Ticket;
import com.yyy.bookManager.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.yyy.bookManager.service.TicketService;
import com.yyy.bookManager.service.UserService;
import com.yyy.bookManager.utils.ConcurrentUtils;
import com.yyy.bookManager.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 这个拦截器总是返回true，所以无论怎么样这个拦截器都能通过。这个拦截器试图通过请求中的Cookie来寻找t票，
 * 一旦寻找到t票并成功的从数据库中找到了对应的用户，就直接放入ConcurrentUtils中（别忘了ConcurrentUtils 和 HostHolder是一回事）。
 * 这里解释了，为什么你在登录一次之后，再进行其他的操作时，服务器都能识别操作用户是谁，甚至你关闭浏览器之后再次打开也不用重新登录，
 * 因为服务器跟你浏览器发送的请求中附带的Cookie对你的身份自动进行了认证。
 *
 * 如果没有找到host信息，也没关系，直接放行就行。
 */

@Component
public class HostInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    //为注入host信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        String t = CookieUtils.getCookie("t", request);
        if (!StringUtils.isEmpty(t)) {
            Ticket ticket = ticketService.getTicket(t);
            if (ticket != null && ticket.getExpiredAt().after(new Date())) {
                User host = userService.getUser(ticket.getUserId());
                ConcurrentUtils.setHost(host);
            }
        }
        return true;
    }




}
