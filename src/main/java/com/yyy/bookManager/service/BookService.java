package com.yyy.bookManager.service;


import com.yyy.bookManager.dao.BookDao;

import com.yyy.bookManager.model.Book;
import com.yyy.bookManager.model.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 该类是对BookService的封装。
*在BookService.java中，首先持有一个BookDAO的对象，这个对象由Spring自动帮你注入（@Autowired），
*你不用亲自去实例化，Spring已经很聪明的帮你实例化了。你需要的是将BookDao的方法“包装”一下，供上层的类去调用。
*这种包装也可以理解为一种代理模式。
*不同的层关心的东西不一样，DAO层关心的就是跟数据库打交道，这样
*所有的方法名都应该要尽量的去描述自己的功能；而Service层关心的是功能，
* */
//@Service定义这个类是业务层bean
@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.selectAll();
    }

    public int addBooks(Book book) {
        return bookDao.addBook(book);
    }

    public void deleteBooks(int id) {
        bookDao.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
    }

    public void recoverBooks(int id) {
        bookDao.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }

}
