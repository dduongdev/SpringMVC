package com.abc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abc.entities.Post;
import com.abc.entities.User;
import com.abc.services.FollowService;
import com.abc.services.PostService;
import com.abc.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
    private PostService postService;
    private FollowService followService;
    private UserService userService;
    
    @Autowired
	public HomeController(PostService postService, FollowService followService, UserService userService) {
		this.postService = postService;
		this.followService = followService;
		this.userService = userService;
	}
	@RequestMapping(value = "/")
    public String homeController(Model model, HttpSession httpSession,
    		@RequestParam(name = "search-keyword", defaultValue = "") String searchKeyword) {
		User user = (User) httpSession.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}

		
        List<User> suggestFollow = followService.getSuggestFollow(user.getId());
        List<User> usersf = followService.getUserFollower(user.getId());
        List<User> userfed = followService.getUserFollowed(user.getId());
        List<Post> posts = postService.getAllPost(user.getId());
        
        List<User> searchResult = new ArrayList<User>();
        boolean hasSearch = false;
        if (!searchKeyword.isEmpty()) {
        	searchResult = userService.searchOnUsername(searchKeyword);
        	hasSearch = true;
        }

        model.addAttribute("suggestfollow", suggestFollow);
        model.addAttribute("usersf",usersf);
        model.addAttribute("userfed",userfed);
        model.addAttribute("posts", posts);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("hasSearch", hasSearch);
        
        return "home";
    }
}
