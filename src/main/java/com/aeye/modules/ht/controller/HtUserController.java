package com.aeye.modules.ht.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HtUserController {

	@GetMapping("/user/me")
	public Object getUser(Authentication authentication) {
		return authentication;
	}

	@ResponseBody
	@RequestMapping("/user/getCurrentUser")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
