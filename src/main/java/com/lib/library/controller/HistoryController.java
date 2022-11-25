package com.lib.library.controller;

import com.lib.library.model.History;
import com.lib.library.model.User;
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
public class HistoryController {

    @Autowired
    IHistoryService historyService;

    @RequestMapping(path = "/historyByBookId",method = RequestMethod.POST)
    public String historyByBookId(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "number")
            int n, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<History> historyByBookId = historyService.getHistoryByBookId(id,o);
        model.addAttribute("historyList",historyByBookId);
        return "history";
    }

    @RequestMapping(path = "/historyByUserId",method = RequestMethod.POST)
    public String historyByUserId(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "number")
            int n, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<History> historyByBookId = historyService.getHistoryByUserId(id,o);
        model.addAttribute("historyList",historyByBookId);
        return "history";
    }

    @RequestMapping(path = "/historyListPage",method = RequestMethod.POST)
    public String historyListPage(
            @RequestParam(name = "number")
            int n, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<History> historyByBookId = historyService.getHistoryList(o);
        model.addAttribute("historyList",historyByBookId);
        return "history";
    }
}
