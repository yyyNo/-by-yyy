package com.yyy.bookManager.interceptor;


import com.yyy.bookManager.model.Ticket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.yyy.bookManager.service.TicketService;
import com.yyy.bookManager.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TicketService ticketService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        //没有t票去登入
        String t = CookieUtils.getCookie("t",request);
        if(StringUtils.isEmpty(t)){
            response.sendRedirect("/users/login");
            return false;
        }

        //无效t票去登入
        Ticket ticket = ticketService.getTicket(t);
        if(ticket == null){
            response.sendRedirect("/users/login");
            return false;
        }

        //过期t票去登入
        if(ticket.getExpiredAt().before(new Date())){
            response.sendRedirect("/users/login");
            return false;
        }

        return true;


    }

}
