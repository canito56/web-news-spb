package com.jb.news.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jb.news.interfaceservice.INewsService;
import com.jb.news.interfaceservice.IUserService;
import com.jb.news.model.News;
import com.jb.news.model.User;

@Controller
@RequestMapping
public class NewsController {
	
	private INewsService serviceNews;
	private IUserService serviceUser;

	private String message;
	private String msgUser;

	private static final String attMessage     = "message";
	private static final String attMsgUser     = "msgUser";
	private static final String redirectNews   = "redirect:/news";
	private static final String redirectList   = "redirect:/list";
	private static final String redirectChgpwd = "redirect:/chgpwd";
	private static final String redirectSignup = "redirect:/signup";
	private static final String redirectAdd    = "redirect:/add";

	public NewsController(INewsService serviceNews, IUserService serviceUser) {
		this.serviceNews = serviceNews;
		this.serviceUser = serviceUser;
	}
	
	@GetMapping("/news")
	public String signin(Model model) { 
		model.addAttribute("user", new User());
		model.addAttribute(attMessage, message);
		message = "";
		return "signin";
	}
	
	@PostMapping("/signin")
	public String getUser(User u, Model model) {
		message = "";
		Optional<User> us = serviceUser.getUser(u.getSuser());
		if (us.isEmpty()) {
			message = "Invalid User or Password";
			return redirectNews;
		}
		if (!us.get().getPassword().equals(serviceUser.getSecurePassword(u.getPassword()))) {
			message = "Invalid Password";
			return redirectNews;
		} 
		msgUser = u.getSuser();
		return redirectList;
	}

	@GetMapping("/signup")
	public String signup(User u, Model model) {
		model.addAttribute("user", new User());
		model.addAttribute(attMessage, message);
		message = "";
		return "signup";
	}

	@PostMapping("/signup")
	public String addUser(User u, Model model) {
		message = "";
		Optional<User> us = serviceUser.getUser(u.getSuser());
		if (us.isPresent()) {
			message = "User already exists";
			return redirectSignup;
		}
		u.setPassword(serviceUser.getSecurePassword(u.getPassword()));
		serviceUser.updateUser(u);
		message = "User registered, enter with new user";
		return redirectNews;
	}
	
	@GetMapping("/chgpwd")
	public String chgpwd(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute(attMessage, message);
		model.addAttribute(attMsgUser, msgUser);
		message = "";
		return "chgpwd";
	}

	@PostMapping("/chgpwd")
	public String chgPassord(@RequestBody String body, User u, Model model) {		
		message = "";
		String pwdnew1 = serviceUser.parseBody(body, "pwdnew1");
		String pwdnew2 = serviceUser.parseBody(body, "pwdnew2");
		String pwdold = serviceUser.getSecurePassword(u.getPassword());
		Optional<User> us = serviceUser.getUser(msgUser);
		if (us.isPresent()) {		
			if (us.get().getPassword().equals(pwdold)) {
				if (!pwdold.equals(serviceUser.getSecurePassword(pwdnew1))) {
					if (pwdnew1.equals(pwdnew2)) {
						us.get().setPassword(serviceUser.getSecurePassword(pwdnew1));
						serviceUser.updateUser(us.get()); 
						message = "Signin with your new password";
						return redirectNews;						
					} else {
						message = "New passwords are diferent";
						return redirectChgpwd;
					}					
				} else {
					message = "the new password must be different from the current one";
					return redirectChgpwd;
				}
			} else {
				message = "Invalid Password";
				return redirectChgpwd;
			}
		} else {
			message = "User not found!";
			return redirectChgpwd;			
		} 
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		String title = "";
		List<News> lnews = serviceNews.list(title);
		model.addAttribute("lnews", lnews);
		model.addAttribute("news", new News());
		model.addAttribute(attMsgUser, msgUser);
		return "list";
	}
	
	@PostMapping("/search")
	public String search(News n, Model model) {
		List<News> lnews = serviceNews.list(n.getTitle());
		model.addAttribute("lnews", lnews);
		model.addAttribute(attMsgUser, msgUser);
		return "list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("news", new News());
		model.addAttribute(attMessage, message);
		message = "";
		return "add";
	}
	
	@PostMapping("/add")
	public String save(News n, Model model) {
		message = "";
		List<News> lnews = serviceNews.list(n.getTitle());
		if (!lnews.isEmpty()) {
			message = "News already exists!";
			return redirectAdd;
		} else {
			serviceNews.updateNews(n);
			return redirectList;
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Optional<News> news = serviceNews.listId(id);
		model.addAttribute("news", news);
		return "edit";
	}
	
	@PostMapping("/update")
	public String update(News n, Model model) {
		serviceNews.updateNews(n);
		return redirectList;
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		serviceNews.deleteNews(id);
		return redirectList;
	}

}
