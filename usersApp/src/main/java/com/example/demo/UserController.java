package com.example.demo;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	private static final Logger logger=LogManager.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/check")
	public String checkService() {
		return "I am working !";
	}
	
	@PostMapping("/user")
	public ResponseEntity<Users> saveUser(@RequestBody Users users){
		logger.info("Enter Into saveUser Method ");
		try {
			Users userObj=userRepo.save(new Users(users.getFirstName(), users.getLastName(), users.getEmail(), users.getPhoneNumber(), users.getAbout()));
			return new ResponseEntity<Users>(userObj,HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("in exption block ==>"+e);
			return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
			// TODO: handle exception
		}
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Users>> getAllUsers(){
		try {
			return new ResponseEntity<>(userRepo.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
