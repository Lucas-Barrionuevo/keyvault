package com.keyVault.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class Test {
	@GetMapping
	public ResponseEntity<?> test(){
		return ResponseEntity.ok("ON");
	}
}
