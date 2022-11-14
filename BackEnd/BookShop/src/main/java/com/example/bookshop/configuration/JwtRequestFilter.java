package com.example.bookshop.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bookshop.service.JwtService;
import com.example.bookshop.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	Logger logger  = Logger.getLogger(JwtRequestFilter.class);
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader("Authorization");
		
		String jwtToken = null;
		String userName = null;
		
		if(header !=null && header.startsWith("Bearer ")) {
			jwtToken= header.substring(7);
			try {
				userName =  jwtUtil.getUserNameFromToken(jwtToken);
				
				
			} catch (IllegalArgumentException e) {
				logger.error("Unable to get JWT Token");
				
			} catch(ExpiredJwtException e) {
				logger.error("JWT Token is expired");
			}
		}else
			logger.info("JWT Token Does Not Start With Bearer ");
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails =  jwtService.loadUserByUsername(userName);
			
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  
						new UsernamePasswordAuthenticationToken(userDetails,
								null,
								userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().
						buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
		filterChain.doFilter(request, response);
	}

}