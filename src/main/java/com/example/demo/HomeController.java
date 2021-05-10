package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.AuthenticationRequest;
import com.example.models.AuthenticationResponse;

@RestController
public class HomeController {
	@Autowired
	private TodoService todoservice;
	
	@Autowired
    private AuthenticationManager authManager; 
	
	@Autowired
	private UserDetailsService uds;
	
	@Autowired
	private JWTUtil jwtu;
	
	@RequestMapping({ "/hello" })
	public String home() {
		return "welcome to the secret page";
	}
	
	@GetMapping("/todo")
	public ArrayList<Todo> getTodos(){
		return todoservice.getAll();
	}
	
	@PostMapping("/todo")
	public void create(Todo t) {
		todoservice.saveOrUpdate(t);
	}
	
	@PutMapping("/todo")
	public void update(Todo t) {
		todoservice.saveOrUpdate(t);
	}
	
	@GetMapping("/todo/{id}")
	public Todo getTodo(@PathVariable("id") int id){
	return todoservice.getTodo(id);
	}
	
	@DeleteMapping("/todo/{id}")
	public void delete(@PathVariable("id") int id) {
		todoservice.deleteByid(id);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest req) throws Exception {
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		}
		catch(BadCredentialsException e) {
		throw new Exception("username or password are incorrect",e);
		 
		}
	final UserDetails userdetaills=uds.loadUserByUsername(req.getUsername());
	 
	final String jwt= jwtu.generateToken(userdetaills);
	return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	}
	
	
}
