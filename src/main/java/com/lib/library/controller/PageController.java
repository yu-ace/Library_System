package com.lib.library.controller;

import com.lib.library.model.Book;
import com.lib.library.model.History;
import com.lib.library.model.User;
import com.lib.library.service.IBookService;
import com.lib.library.service.IHistoryService;
import com.lib.library.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    IBookService bookService;
    @Autowired
    IHistoryService historyService;
    @Autowired
    IUserService userService;

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String index(){
        return "login";
    }

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String index1(){
        return "login";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(path = "/readerBoard",method = RequestMethod.GET)
    public String readerBoard(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        model.addAttribute("user",user);
        return "readerBoard";
    }

    @RequestMapping(path = "/userBoard",method = RequestMethod.GET)
    public String userBoard(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        model.addAttribute("user",user);
        return "userBoard";
    }

    @RequestMapping(path = "/bookList",method = RequestMethod.GET)
    public String bookList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest page = PageRequest.of(0, 10);
        Page<Book> bookList = bookService.getBookList(page);
        model.addAttribute("bookList", bookList);
        return "bookList";
    }

    @RequestMapping(path = "/borrowBook",method = RequestMethod.GET)
    public String borrowBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "borrowBook";
    }

    @RequestMapping(path = "/returnBook",method = RequestMethod.GET)
    public String returnBook(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "returnBook";
    }

    @RequestMapping(path = "/readerChangePassword",method = RequestMethod.GET)
    public String changePassword(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "readerChangePassword";
    }



    @RequestMapping(path = "/newBook",method = RequestMethod.GET)
    public String newBook(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "newBook";
    }

    @RequestMapping(path = "/history",method = RequestMethod.GET)
    public String history(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(0,10);
        Page<History> historyList = historyService.getHistoryList(o);
        model.addAttribute("historyList",historyList);
        return "history";
    }

    @RequestMapping(path = "/newUser",method = RequestMethod.GET)
    public String newUser(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "newUser";
    }

    @RequestMapping(path = "/userList",method = RequestMethod.GET)
    public String userList(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(0,10);
        Page<User> userList = userService.getUserList(0,o);
        model.addAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(path = "/deleteUser",method = RequestMethod.GET)
    public String deleteUser(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "deleteUser";
    }

    @RequestMapping(path = "/userChangePassword",method = RequestMethod.GET)
    public String userChangePassword(Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        return "userChangePassword";
    }
}
