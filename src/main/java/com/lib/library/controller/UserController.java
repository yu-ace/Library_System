package com.lib.library.controller;

import com.lib.library.model.User;
import com.lib.library.service.IUserService;
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
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String login(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password, Model model, HttpSession session){
        User user = userService.getUserByName(name);
        if(user == null){
            model.addAttribute("error","该用户不存在！");
            return "login";
        }
        if(user.getIsDelete() == 1){
            model.addAttribute("error","该用户已注销！");
            return "login";
        }
        if(user.getPassword().equals(password)){
            if(user.getIdentity().equals("读者")){
                session.setAttribute("user",user);
                return "redirect:/readerBoard";
            }else{
                session.setAttribute("user",user);
                return "redirect:/userBoard";
            }
        }else{
            model.addAttribute("error","密码错误");
            return "login";
        }
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,Model model){
        userService.register(name,password);
        model.addAttribute("error","注册成功，请登录！");
        return "login";
    }

    @RequestMapping(path = "/readerChangePassword",method = RequestMethod.POST)
    public String readerChangePassword(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,
            Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","对不起，该用户不存在");
            return "readerChangePassword";
        }
        if(user1.getIdentity().equals("管理员")){
            model.addAttribute("tip","对不起，您没有权限对此用户进行更改");
            return "readerChangePassword";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","对不起，你输入的新密码与原密码相同，更改失败");
                return "readerChangePassword";
            }else if(!newPassword.equals(newPassword1)){
                model.addAttribute("tip","对不起，你两次输入的新密码不同，更改失败");
                return "readerChangePassword";
            }else {
                model.addAttribute("tip","更改成功");
                return "readerChangePassword";
            }
        }else{
            model.addAttribute("tip","密码错误");
            return "readerChangePassword";
        }
    }

    @RequestMapping(path = "/newUser",method = RequestMethod.POST)
    public String newUser(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        userService.newUser(name,password);
        model.addAttribute("tip","添加成功");
        return "newUser";
    }

    @RequestMapping(path = "/userListPage",method = RequestMethod.POST)
    public String userListPage(
            @RequestParam(name = "number")
            int n,
            Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        PageRequest o = PageRequest.of(n,10);
        Page<User> userList = userService.getUserList(0,o);
        model.addAttribute("userList",userList);
        return "userList";
    }

    @RequestMapping(path = "/deleteUser",method = RequestMethod.POST)
    public String deleteUser(
            @RequestParam(name = "id")
            int id,
            @RequestParam(name = "password")
            String password,Model model,HttpSession session){
        User user= (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserById(id);
        if(user1 == null){
            model.addAttribute("tip","用户不存在，删除失败");
            return "deleteUser";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","您没有权限删除该用户");
            return "deleteUser";
        }
        if(user1.getPassword().equals(password)){
            userService.deleteUser(id);
            model.addAttribute("tip","删除成功");
            return "deleteUser";
        }else{
            model.addAttribute("tip","密码错误，删除失败");
            return "deleteUser";
        }
    }

    @RequestMapping(path = "/userChangePassword",method = RequestMethod.POST)
    public String userChangePassword(
            @RequestParam(name = "name")
            String name,
            @RequestParam(name = "password")
            String password,
            @RequestParam(name = "newPassword")
            String newPassword,
            @RequestParam(name = "newPassword1")
            String newPassword1,
            Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("error","您已退出系统，请重新登陆");
            return "login";
        }
        User user1 = userService.getUserByName(name);
        if(user1 == null){
            model.addAttribute("tip","对不起，该用户不存在");
            return "userChangePassword";
        }
        if(user1.getName().equals("admin")){
            model.addAttribute("tip","对不起，您没有权限对此用户进行更改");
            return "userChangePassword";
        }
        if(user1.getPassword().equals(password)){
            if(newPassword.equals(password) || newPassword1.equals(password)){
                model.addAttribute("tip","对不起，你输入的新密码与原密码相同，更改失败");
                return "userChangePassword";
            }else if(!newPassword.equals(newPassword1)){
                model.addAttribute("tip","对不起，你两次输入的新密码不同，更改失败");
                return "userChangePassword";
            }else {
                model.addAttribute("tip","更改成功");
                return "userChangePassword";
            }
        }else{
            model.addAttribute("tip","密码错误");
            return "userChangePassword";
        }
    }
}
