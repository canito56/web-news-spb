package com.jb.news.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jb.news.interfaceService.INewsService;
import com.jb.news.interfaceService.IUserService;
import com.jb.news.model.News;
import com.jb.news.model.User;

@Controller
@RequestMapping
public class NewsController {
	@Autowired
	private INewsService serviceNews;
	@Autowired
	private IUserService serviceUser;
	private String message;
	private String msgUser;

	@GetMapping("/news")
	public String signin(Model model) { 
		model.addAttribute("user", new User());
		model.addAttribute("message", message);
		message = "";
		return "signin";
	}
	
	@PostMapping("/signin")
	public String getUser(User u, Model model) {
		message = "";
		Optional<User> us = serviceUser.getUser(u.getSuser());
		if (!us.isPresent()) {
			message = "Invalid User or Password";
			return "redirect:/news";
		};
		if (!us.get().getPassword().equals(serviceUser.getSecurePassword(u.getPassword()))) {
			message = "Invalid Password";
			return "redirect:/news";
		} 
		msgUser = u.getSuser();
		return "redirect:/list";
	}

	@GetMapping("/signup")
	public String signup(User u, Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("message", message);
		message = "";
		return "signup";
	}

	@PostMapping("/signup")
	public String addUser(User u, Model model) {
		message = "";
		Optional<User> us = serviceUser.getUser(u.getSuser());
		if (us.isPresent()) {
			message = "User already exists";
			return "redirect:/signup";
		}
		if (serviceUser.addUser(u) == 1) {
			message = "User registered, enter with new user";
			return "redirect:/news";
		} else {
			message = "User not registered";
			return "redirect:/signup";
		}
	}
	
	@GetMapping("/chgpwd")
	public String chgpwd(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("message", message);
		model.addAttribute("msgUser", msgUser);
		message = "";
		return "chgpwd";
	}

	@PostMapping("/chgpwd")
	public String chgPassword(@RequestBody String body, User u, Model model) {		
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
						if (serviceUser.updateUser(us.get()) == 1) {
							message = "Signin with your new password";
							return "redirect:/news";						
						} else {
							message = "Save password failed!, contact with IT admin";
							return "redirect:/chgpwd";						
						}
					} else {
						message = "New passwords are diferent";
						return "redirect:/chgpwd";
					}					
				} else {
					message = "the new password must be different from the current one";
					return "redirect:/chgpwd";
				}
			} else {
				message = "Invalid Password";
				return "redirect:/chgpwd";
			}
		} else {
			message = "User not found!";
			return "redirect:/chgpwd";			
		} 
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		String title = "";
		List<News> lnews = serviceNews.list(title);
		model.addAttribute("lnews", lnews);
		model.addAttribute("news", new News());
		model.addAttribute("msgUser", msgUser);
		return "list";
	}
	
	@PostMapping("/search")
	public String search(News n, Model model) {
		List<News> lnews = serviceNews.list(n.getTitle());
		model.addAttribute("lnews", lnews);
		model.addAttribute("msgUser", msgUser);
		return "list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("news", new News());
		model.addAttribute("message", message);
		message = "";
		return "add";
	}
	
	@PostMapping("/add")
	public String save(News n, Model model) {
		message = "";
		List<News> lnews = serviceNews.list(n.getTitle());
		if (!lnews.isEmpty()) {
			message = "News already exists!";
			return "redirect:/add";
		} else {
			if (serviceNews.addNews(n) == 1) {
				return "redirect:/list";
			}
			message = "Save News fail!, contact with IT admin";
			return "redirect:/add";			
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
		return "redirect:/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		serviceNews.deleteNews(id);
		return "redirect:/list";
	}

}
