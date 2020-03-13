package com.aabbou.ppm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aabbou.ppm.config.security.JwtTokenProvider;
import com.aabbou.ppm.config.security.SecurityConstants;
import com.aabbou.ppm.entity.User;
import com.aabbou.ppm.payload.JWTLoginSucessReponse;
import com.aabbou.ppm.payload.LoginRequest;
import com.aabbou.ppm.service.IUserService;
import com.aabbou.ppm.service.impl.MapValidationErrorService;
import com.aabbou.ppm.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	if (errorMap != null)
	    return errorMap;

	Authentication authentication = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

	return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
	// Validate passwords match
	userValidator.validate(user, result);

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	if (errorMap != null)
	    return errorMap;

	User newUser = userService.registerUser(user);

	return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
