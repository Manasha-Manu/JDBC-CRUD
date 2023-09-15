package com.jdbc.demo.service;

import org.springframework.stereotype.Service;

import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;

@Service
public interface SignUpService {
	
	public Response createUser(SignUpModel values);
	
	public Response getUser();
	
	public Response getOneUser(String sno);
	
	public Response deleteUser(String sno);
	
	public Response userLogin(String email,String password);
	
	public Response softDelete(String sno,boolean isactive);
	
	public Response updateValues(SignUpModel values);
	
	public Response updateAllValues(SignUpModel values);
	
	public Response sendOTP(String toEmail);
	
	
		
}
