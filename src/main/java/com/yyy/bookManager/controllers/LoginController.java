package com.yyy.bookManager.controllers;


import com.yyy.bookManager.biz.LoginBiz;
import com.yyy.bookManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.yyy.bookManager.service.UserService;
import com.yyy.bookManager.utils.CookieUtils;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/users/register"}, method = {RequestMethod.GET})
    public String register(){ return "login/register";}

    @RequestMapping(path = {"/users/register/do"}, method = {RequestMethod.POST})
    public String doRegister(
        Model model,
        HttpServletResponse response,
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password
        ){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        try{
            String t = loginBiz.register(user);
            CookieUtils.writeCookie("t", t, response);
            return "redirect:/index";

        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "404";
        }

    }

    @RequestMapping(path = {"/users/login"}, method = {RequestMethod.GET})
    public String login() {
        return "login/login";
    }

    /**
     * 我们直接调用了loginBiz.login(email, password);方法，这个方法里面抛出了我们自
     * 定义的异常，记得用try catch包起来。这里对于异常的处理是返回一个自定义的404页面，
     * 并在页面上渲染上出错的原因。
     * CookieUtils.writeCookie("t", t, response);这句将loginBiz.login(email, password);
     * 方法返回的t票字符串放入Cookie中，当你成功后，可以在你的浏览器中查看是否成功写入了Cookie。
     * 最后return "redirect:/index";这里实际上是一个跳转，跳转到了/index对应的Action（一般把带有web入口的方法称为Action）
     */
    @RequestMapping(path = {"/users/login/do"}, method = {RequestMethod.POST})
    public String doLogin(
            Model model,
            HttpServletResponse response,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        try {
            String t = loginBiz.login(email, password);
            CookieUtils.writeCookie("t", t, response);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "404";
        }

    }

    @RequestMapping(path = {"/users/logout/do"}, method = {RequestMethod.GET})
    public String doLogout(
            @CookieValue("t") String t
    ) {

        loginBiz.logOut(t);
        return "redirect:/index";

    }



}
