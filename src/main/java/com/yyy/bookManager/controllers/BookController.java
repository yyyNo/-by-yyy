package com.yyy.bookManager.controllers;

import com.yyy.bookManager.model.Book;
import com.yyy.bookManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.yyy.bookManager.service.BookService;
import com.yyy.bookManager.service.HostHolder;


//controllers包里的代码控制了怎么进入、怎么处理、怎么返回的所有操作
//@Controller在对应的方法上，视图解析器可以解析return 的jsp,html页面，
// 并且跳转到相应页面.若返回json等内容到页面，则需要加@ResponseBody注解.
//该类先注入service层的代码，service层又会自动注入DAO层代码
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private HostHolder hostHolder;


    //第一行告诉了web什么样的url才能进入这个方法
    //方法的主体部分：loadAllBooksView(model); 告诉web如何处理和组装Model
    //最后return "book/books";告诉web返回什么样的View
    @RequestMapping(path = {"/index"},method = {RequestMethod.GET})
    public String bookList(Model model){
        User host = hostHolder.getUser();
        if (host != null) {
            model.addAttribute("host", host);
        }
        loadAllBooksView(model);
        return "book/books";

    }

    @RequestMapping(path = {"/books/add"}, method = {RequestMethod.GET})
    public String addBook() {
        return "book/addbook";
    }

    @RequestMapping(path = {"/books/add/do"}, method = {RequestMethod.POST})
    public String doAddBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") String price
    ) {

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookService.addBooks(book);

        return "redirect:/index";

    }

    @RequestMapping(path = {"/books/{bookId:[0-9]+}/delete"}, method = {RequestMethod.GET})
    public String deleteBook(
            @PathVariable("bookId") int bookId
    ) {

        bookService.deleteBooks(bookId);
        return "redirect:/index";

    }

    @RequestMapping(path = {"/books/{bookId:[0-9]+}/recover"}, method = {RequestMethod.GET})
    public String recoverBook(
            @PathVariable("bookId") int bookId
    ) {

        bookService.recoverBooks(bookId);
        return "redirect:/index";

    }

    /**
     * 为model加载所有的book
     */
    private void loadAllBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
    }



}
