package com.abc.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.servlet.http.HttpServletRequest;
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

		return "editprofile";
	}

	@PostMapping("/edit")
	public String handleEdit(@ModelAttribute("extendedUser") ExtendedUser extendedUser,
			BindingResult bindingResult,
			@RequestParam(name = "avatar", required = false) MultipartFile avatarFile, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
	        bindingResult.getAllErrors().forEach(error -> System.out.println(error));
	        redirectAttributes.addFlashAttribute("currentPageState", extendedUser);
	        return "redirect:/profile/edit";
	    }
		boolean isEmailValid = true;
		List<String> emailStateMessages = new ArrayList<String>();
		if (!Validator.matchesFormat(extendedUser.getEmail(), Constants.EMAIL_PATTERN)) {
			emailStateMessages.add("Email không đúng định dạng.");
			isEmailValid = false;
		}

		if (userService.isEmailExists(extendedUser.getEmail())) {
			emailStateMessages.add("Email đã tồn tại.");
			isEmailValid = false;
		}

		boolean isBirthDateValid = true;
		String birthDateStateMessage = "";
		if (Period.between(extendedUser.getBirthDate(), LocalDate.now()).getYears() <= 15) {
			birthDateStateMessage = "Tuổi phải trên 15.";
			isBirthDateValid = false;
		}

		boolean isAvatarFileValid = true;
		List<String> avatarFileStateMessage = new ArrayList<String>();
		if (avatarFile != null) {
			isAvatarFileValid = true;
			if (avatarFile.getSize() > 200 * 1024) {
				avatarFileStateMessage.add("Kích thước ảnh không được vượt quá 200KB.");
				isAvatarFileValid = false;
			}

			String contentType = avatarFile.getContentType();
			if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
				avatarFileStateMessage.add("Chỉ chấp nhận ảnh định dạng JPG hoặc PNG.");
				isAvatarFileValid = false;
			}
		}

		redirectAttributes.addFlashAttribute("isAvatarFileValid", isAvatarFileValid);
		if (!isAvatarFileValid) {
			redirectAttributes.addFlashAttribute("avatarFileStateMessage", avatarFileStateMessage);
		}
		
		redirectAttributes.addFlashAttribute("isEmailValid", isEmailValid);
		if (!isEmailValid) {
			redirectAttributes.addFlashAttribute("emailStateMessages", emailStateMessages);
		}

		redirectAttributes.addFlashAttribute("isBirthDateValid", isBirthDateValid);
		if (!isBirthDateValid) {
			redirectAttributes.addFlashAttribute("birthDateStateMessage", birthDateStateMessage);
		}

		redirectAttributes.addFlashAttribute("currentPageState", extendedUser);

		if (isAvatarFileValid && isEmailValid && isBirthDateValid) {
            try {
            	String uploadPath = request.getServletContext().getRealPath("/resource/images");
    			File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String fileName = avatarFile.getOriginalFilename();
                File dest = new File(uploadPath + File.separator + fileName);
				avatarFile.transferTo(dest);
				
				User user = (User) session.getAttribute("user");
				
				extendedUser.setId(user.getId());
				extendedUser.setAvatarFileName(fileName);
				
				userService.update(extendedUser);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            return "redirect:/";
		}
		
		return "redirect:/profile/edit";
	}
}
