package cn.iflyapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: qfwang
 * Date: 2017-12-14 下午5:13
 */
@Controller
public class ThymeleafController {

    @GetMapping("hello")
    public ModelAndView hello(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("msg","hello world !");

        return mv;
    }
}
