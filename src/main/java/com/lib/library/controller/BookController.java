package com.lib.library.controller;

import com.lib.library.model.Book;
import com.lib.library.model.User;
import com.lib.library.service.IBookService;
import com.lib.library.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class BookController {

    @Autowired
    IBookService bookService;

    @Autowired
    IHistoryService historyService;

    @RequestMapping(path = "/bookListByAnther",method = RequestMethod.POST)
    public String bookListByAnther(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "number")
            int n,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookListByAnther(name,o);
        model.addAttribute("bookList", bookList);
        return "bookList";
    }

    @RequestMapping(path = "/bookListByCategory",method = RequestMethod.POST)
    public String bookListByName(
            @RequestParam(name = "category")
            String category,
            @RequestParam(name = "number")
            int n,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookListByCategory(category,o);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "/bookListPage",method = RequestMethod.POST)
    public String bookListPage(
            @RequestParam(name = "number")
            int n,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<Book> bookList = bookService.getBookList(o);
        model.addAttribute("bookList",bookList);
        return "bookList";
    }

    @RequestMapping(path = "/borrowBook",method = RequestMethod.POST)
    public String borrowBook(
            @RequestParam(name = "id")
            int id,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        if(book == null){
            model.addAttribute("tip","您输入的书不存在");
            return "borrowBook";
        }
        if(book.getCount() == 0){
            model.addAttribute("tip","对不起,该书库存不足，借取失败");
            return "borrowBook";
        }
        book.setCount(book.getCount() - 1);
        historyService.borrowBook(id,user.getId());
        model.addAttribute("tip","借取成功");
        return "borrowBook";
    }

    @RequestMapping(path = "/returnBook",method = RequestMethod.POST)
    public String returnBook(
            @RequestParam(name = "id")
            int id,
            Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        Book book = bookService.getBookById(id);
        if(book == null){
            model.addAttribute("tip","您输入的书不存在");
            return "returnBook";
        }
        if(book.getCount() == book.getInitialQuantity()){
            model.addAttribute("tip","对不起,该书不是本图书馆的，归还失败");
            return "returnBook";
        }
        book.setCount(book.getCount() + 1);
        historyService.returnBook(id,user.getId());
        model.addAttribute("tip","归还成功");
        return "returnBook";
    }

    @RequestMapping(path = "/newBook",method = RequestMethod.POST)
    public String newBook(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "price")
            double price,
            @RequestParam(name = "count")
            int count,
            @RequestParam(name = "anther")
            String anther,
            @RequestParam(name = "category")
            String category,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        bookService.newBook(name,price,count,anther,category,count);
        model.addAttribute("tip","添加成功");
        return "newBook";
    }
}
