package board.board.controller;

import board.board.repository.UserRepository;
import board.board.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome (){

        return "welcome";
    }

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String mainproject() {

        return "index";
    }


}