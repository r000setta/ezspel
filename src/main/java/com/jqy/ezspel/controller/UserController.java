package com.jqy.ezspel.controller;

import com.jqy.ezspel.config.KeywordProperties;
import com.jqy.ezspel.config.UserConfig;
import com.jqy.ezspel.service.UserService;
import com.jqy.ezspel.util.EvaluationContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    ExpressionParser parser = new SpelExpressionParser();


    @Autowired
    private KeywordProperties keywordProperties;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String admin(@CookieValue(value = "rememberMe", required = false) String rememberMe, HttpSession session, Model model) {
        if (rememberMe != null && !rememberMe.equals("")) {
            String username = userService.decryptRememberMe(rememberMe);
            if (username != null) {
                session.setAttribute("username", username);
            }
        }

        Object username = session.getAttribute("username");
        if (username != null && !username.toString().equals("")) {
            model.addAttribute("name", this.getSpelValue(username.toString()) + "test");
            return "hello";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("msg", "password or username is wrong!");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password,
                        @RequestParam(value = "remerberMe", required = false) String isRemember,
                        HttpServletResponse response, HttpSession session) {
        if (userConfig.getUsername().contentEquals(username) && userConfig.getPassword().contentEquals(password)) {
            session.setAttribute("username", username);
            if (isRemember != null && !isRemember.equals("")) {
                Cookie cookie = new Cookie("rememberMe", userService.encryptRememberMe());
                cookie.setMaxAge(259200);
                response.addCookie(cookie);
            }
            return "redirect:/";
        } else {
            return "redirect:/error";
        }
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handlerForbidden() {
        return "forbidden";
    }

    private String getSpelValue(String val) {
        String[] blackList = keywordProperties.getBlacklist();
        for (int i = 0; i < blackList.length; i++) {
            String keyword = blackList[i];
            Matcher matcher = Pattern.compile(keyword, 34).matcher(val);
            if (matcher.find()) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }
        ParserContext parserContext = new TemplateParserContext();
        Expression expression = this.parser.parseExpression(val, parserContext);
        EvaluationContextImpl context = new EvaluationContextImpl();
        return expression.getValue(context).toString();
    }

}
