package com.synes.controler;

import com.synes.config.JwtTokenUtil;
import com.synes.service.JwtUserDetailsService;
import com.synes.util.BaseDeDonnee;
import com.synes.util.JwtRequest;
import com.synes.util.JwtResponse;
import com.synes.util.Membre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	BaseDeDonnee bd = new BaseDeDonnee();
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtTokenUtil.generateToken(userDetails);

		System.out.println("ce token est pour le user : "+jwtTokenUtil.getUsernameFromToken(token));

		return ResponseEntity.ok(new JwtResponse(token));
	}


	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> saveUser(@RequestBody Membre user) throws Exception {
		return ResponseEntity.ok(bd.Add_Membre(user));
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e)
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}


	private void authenticate(String username, String password)
	{
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			/// ???
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch (DisabledException e)
		{
			System.out.println("UTENTE DISABILITATO");
			//throw new AuthenticationException("UTENTE DISABILITATO",e);
		}
		catch (BadCredentialsException e)
		{
			System.out.println("CREDENTIAL ERROR");
			//throw new AuthenticationException("CREDENTIAL ERROR", e);
		}
	}
}