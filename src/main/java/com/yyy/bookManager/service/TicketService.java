package com.yyy.bookManager.service;



import com.yyy.bookManager.dao.TicketDao;
import com.yyy.bookManager.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    public void addTicket(Ticket t){ ticketDao.addTicket(t);}

    public Ticket getTicket(int uid){
        return ticketDao.selectByUserId(uid);
    }

    public Ticket getTicket(String t){
        return ticketDao.selectByTicket(t);
    }

    public void deleteTicket(int tid){
        ticketDao.deleteTicketById(tid);
    }

    public void deleteTicket(String t){
        ticketDao.deleteTicket(t);
    }

}
