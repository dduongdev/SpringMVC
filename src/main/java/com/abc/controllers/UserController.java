package com.abc.controllers;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abc.entities.ExtendedUser;
import com.abc.entities.Post;
import com.abc.entities.Province;
import com.abc.entities.User;
import com.abc.services.PostService;
import com.abc.services.ProvinceService;
import com.abc.services.UserService;
import com.abc.utils.Constants;
import com.abc.utils.Validator;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserController {

	private PostService postService;
	private UserService userService;
	private ProvinceService provinceService;

	@Autowired
	public UserController(PostService postService, UserService userService, ProvinceService provinceService) {
		this.postService = postService;
		this.userService = userService;
		this.provinceService = provinceService;
	}

	@GetMapping("/")
	public String profileUser(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null)
			return "redirect:/login";

		List<Post> posts = new ArrayList<Post>();

		posts = postService.getPostById(user.getId());

		model.addAttribute("user", user);
		model.addAttribute("posts", posts);

		return "profile";

	}

	@GetMapping("/edit")
	public String showEditPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null)
			return "redirect:/login";
		
		String username = user.getUsername();
		ExtendedUser extendedUser = userService.getExtendedUserByUsername(username);
		model.addAttribute("extendedUser", extendedUser);

		List<Province> provinces = provinceService.getAll();

		model.addAttribute("provinces", provinces);

		return "edit_profile";
	}

	@PostMapping("/edit")
	public String handleEdit(@ModelAttribute ExtendedUser extendedUser,
			@RequestParam("avatar") MultipartFile avatarFile, RedirectAttributes redirectAttributes) {
		boolean isEmailValid = true;
		List<String> emailStateMessages = new ArrayList<String>();

		if (!Validator.matchesFormat(extendedUser.getEmail(), Constants.EMAIL_PATTERN)) {

			emailStateMessages.add("Email không đúng định dạng.");
		}

		if (!userService.isEmailExists(extendedUser.getEmail())) {
			emailStateMessages.add("Email đã tồn tại.");
		}

		boolean isBirthDateValid = true;
		String birthDateStateMessage = "";
		if (Period.between(extendedUser.getBirthDate(), LocalDate.now()).getYears() <= 15) {
			birthDateStateMessage = "Tuổi phải trên 15.";
		}

		boolean isAvatarFileValid = true;
		List<String> avatarFileStateMessage = new ArrayList<String>();
		if (avatarFile.getSize() > 200 * 1024) {
			avatarFileStateMessage.add("Kích thước ảnh không được vượt quá 200KB.");
		}

		String contentType = avatarFile.getContentType();
		if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
			avatarFileStateMessage.add("Chỉ chấp nhận ảnh định dạng JPG hoặc PNG.");
		}

		redirectAttributes.addAttribute("isEmailValid", isEmailValid);
		if (!isEmailValid) {
			redirectAttributes.addAttribute("emailStateMessages", emailStateMessages);
		}

		redirectAttributes.addAttribute("isBirthDateValid", isBirthDateValid);
		if (!isBirthDateValid) {
			redirectAttributes.addAttribute("birthDateStateMessage", birthDateStateMessage);
		}

		redirectAttributes.addAttribute("isAvatarFileValid", isAvatarFileValid);
		if (!isAvatarFileValid) {
			redirectAttributes.addAttribute("avatarFileStateMessage", avatarFileStateMessage);
		}

		redirectAttributes.addAttribute("currentPageState", extendedUser);

		return "redirect:/edit";
	}
}
