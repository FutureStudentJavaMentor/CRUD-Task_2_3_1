package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String getUserList(Model model) {
        List<User> usersList = userService.getUsersList();
        model.addAttribute("users", usersList);
        return "users";
    }

    @GetMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveNewUser(user);
        return "redirect:/users";
    }


    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id,Model model){
        model.addAttribute("user",userService.findById(id));
        return "/user-update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user")User  user,@PathVariable("id") long id){
        userService.updateUser(user,id);
    return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
     userService.deleteUser(id);
        return "redirect:/users";
    }


}
