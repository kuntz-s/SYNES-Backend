package com.synes.service;


import com.synes.util.BaseDeDonnee;
import com.synes.util.MemberConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	/*@Autowired
	private UserDao userDao;*/
	BaseDeDonnee bd = new BaseDeDonnee();

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		MemberConn memberConn = bd.verif_email(mail);

		if (memberConn == null) {
			throw new UsernameNotFoundException("User not found with username: " + mail);
		}
		return new User(memberConn.getEmail(), memberConn.getMotdepasse(),
				new ArrayList<>());


	}

	public MemberConn save(MemberConn member) {
		MemberConn memberConn = new MemberConn();
		memberConn.setEmail(member.getEmail());
		memberConn.setMotdepasse(bcryptEncoder.encode(member.getMotdepasse()));
		return memberConn;
	}
}