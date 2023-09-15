package com.jdbc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.dao.SignUpDao;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")

public class SignUpController {

	@Autowired
	SignUpDao dao;

	@PostMapping("/create")
	public ResponseEntity<Response> createUser(@RequestBody SignUpModel values) {
		return ResponseEntity.ok(dao.createUser(values));
	}
	
	@GetMapping("/get")
	public ResponseEntity<Response> getUser() {
		return ResponseEntity.ok(dao.getUser());
	}
	
	@GetMapping("/getOne")
	public ResponseEntity<Response> getOneUser(@RequestParam String sno) {
		return ResponseEntity.ok(dao.getOneUser(sno));
	}
	
	@PostMapping("/update")
	public ResponseEntity<Response> updateEmail(@RequestBody SignUpModel values){
		return ResponseEntity.ok(dao.updateEmail(values)); 
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestParam String sno){
		return ResponseEntity.ok(dao.deleteUser(sno));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response>userLogin(@RequestParam String email,String password){
		return ResponseEntity.ok(dao.userLogin(email,password));
	}
	
	@PostMapping("/softDelete")
	public ResponseEntity<Response>softDelete(@RequestBody String sno,boolean isactive){
		return ResponseEntity.ok(dao.softDelete(sno,isactive));
	}
	
	@PostMapping("/updateValues")
	public ResponseEntity<Response> updateValues(@RequestBody SignUpModel values){
		return ResponseEntity.ok(dao.updateValues(values)); 
	}
	
	@PostMapping("/updateAllValues")
	public ResponseEntity<Response> updateAllValues(@RequestBody SignUpModel values){
		return ResponseEntity.ok(dao.updateAllValues(values)); 
	}
	
	@PostMapping("/otp")
	public ResponseEntity<Response> sendOTP(@RequestParam String toEmail){
		return ResponseEntity.ok(dao.sendOTP(toEmail));
	}
}
