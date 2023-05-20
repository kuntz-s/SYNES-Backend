package com.synes.controler.authentification;

import com.synes.config.authentification.JwtTokenUtil;
import com.synes.service.authentification.JwtUserDetailsService;
import com.synes.util.ApiError;
import com.synes.util.Membre;
import com.synes.util.UseConnectInfo;
import com.synes.util.authentification.JwtRequest;
import com.synes.util.baseDeDonnee.BaseDeDonnee;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public Object createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {

		UseConnectInfo useConnectInfo = null;

		int val = bd.verif_Membre(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		if (val == 1){

			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());


			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getEmail());

			String token = jwtTokenUtil.generateToken(userDetails);

			System.out.println("ce token est pour le user : "+jwtTokenUtil.getUsernameFromToken(token));
			Membre leMembre = bd.searchUser(jwtTokenUtil.getUsernameFromToken(token));
			System.out.println(leMembre);
			String nomUniv = bd.getUniversityById(leMembre.getIduniversite());
			System.out.println(nomUniv);
			String nomRole = leMembre.getRole();
			System.out.println(nomRole);
			List<String> listPermis = bd.getPermission(bd.getIdRole(nomRole));
			System.out.println(listPermis);

			useConnectInfo = new UseConnectInfo(token,leMembre,nomRole,listPermis,nomUniv);

			return useConnectInfo;
		}else {
			response.setStatus(404);
			//throw new Error("user not found");
			return new ApiError(404,"even the mail adress or the password is wrong","user not found");
		}



	}




	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> saveUser(@RequestBody Membre user) throws Exception {

		if (user.getMatricule().equals("")){

		}else if (user.getNom().equals("")){

		}else if (user.getPrenom().equals("")){

		}else if (user.getEmail().equals("")){

		}else if (user.getMotdepasse().equals("")){

		}else if (user.getRole().equals("")){

		}else if (Objects.equals(user.getIduniversite(), "")){

		}

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



