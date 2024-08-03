package com.nc13.JPA_Board2.controller;

import com.nc13.JPA_Board2.model.UserDTO;
import com.nc13.JPA_Board2.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {
    //생성자 주입
    private final UserService userService;

    // 회원가입 페이지 출력 요청
    @GetMapping("register")
    public String register(){

        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute UserDTO userDTO) {
        System.out.println("UseController.register");
        System.out.println("userDTO =" +userDTO);
        userService.register(userDTO);

        return "login";
    }

    @GetMapping("login")
    public String loinForm() {
        return "login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO logIn= userService.login(userDTO);
        if(logIn != null) {
            // 로그인 성공
            session.setAttribute("logIn",logIn.getUsername());
            return "main";
        } else {
            //로그인 실패
            return "login";
        }
    }

    @GetMapping("showList")
    public String selectAll(Model model) {
        List<UserDTO> memberList= userService.selectAll();
        model.addAttribute("memberList",memberList);
        return "showList";
    }

    @GetMapping("showOne/{id}")
    public String selectOne(@PathVariable Long id, Model model) {
        UserDTO userDTO=userService.selectOne(id);
        model.addAttribute("user",userDTO);

        return "detail";
    }

    @GetMapping("update")
    public String update(HttpSession session,Model model) {
        String myUsername=(String) session.getAttribute("logIn");
        UserDTO userDTO=userService.updateForm(myUsername);
        model.addAttribute("updateUser",userDTO);
        return "update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return "redirect:/user/showOne/"+userDTO.getId();
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/user/showList";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @PostMapping("username-check")
    //ajax의 경우 리턴 부분에 @ResponseBody를 넣어줘야 한다.
    public @ResponseBody String usernameCheck(@RequestParam("username") String memberUsername) {
        System.out.println("username= "+memberUsername);
        String checkResult = userService.usernameCheck(memberUsername);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
//        return "체크완료";
    }
}
