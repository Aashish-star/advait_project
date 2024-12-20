package org.advait.assignment.service;

import java.util.HashSet;
import java.util.Objects;

import org.advait.assignment.entity.UserDetailEntity;
import org.advait.assignment.model.JwtResponse;
import org.advait.assignment.model.UserRequest;
import org.advait.assignment.repository.UserRepository;
import org.advait.assignment.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	HashSet<String> blackListedToken = new HashSet<>();

	public UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String createUser(UserRequest userSignupRequest) {
		if (Objects.isNull(userSignupRequest) || userSignupRequest.getEmail().isEmpty()
				|| userSignupRequest.getUserName().isEmpty()) {
			throw new IllegalArgumentException("Mandotory field is null");
		} else {
			UserDetailEntity detailEntity = userRepository.findByEmail(userSignupRequest.getEmail()).orElse(null);
			if (Objects.isNull(detailEntity)) {
				UserDetailEntity entity = new UserDetailEntity();
				entity.setEmail(userSignupRequest.getEmail());
				entity.setPassword(passwordEncoder.encode(userSignupRequest.getPassword()));
				entity.setUserName(userSignupRequest.getUserName());
				userRepository.save(entity);
				return "User created successgully";
			}
			return "";
		}

	}

	public ResponseEntity<JwtResponse> signIn(UserRequest userSignupRequest) {
		UserDetailEntity detailEntity = userRepository.findByEmail(userSignupRequest.getEmail()).orElse(null);
		if (Objects.nonNull(detailEntity)
				&& passwordEncoder.matches(userSignupRequest.getPassword(), detailEntity.getPassword())) {
			String accessToken = "Bearer " + TokenUtil.generateAccessToken(userSignupRequest.getUserName());
			String refreshToken = "Bearer " + TokenUtil.generateRefreshToken(userSignupRequest.getUserName());
			return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
		} else {
			throw new IllegalArgumentException("Invalid credentials");
		}
	}

	public String authorizeToken(String token, UserRequest userSignupRequest) throws IllegalAccessException {
		String message = "Token is valid";
		try {
			if (!StringUtils.isEmpty(token) && token.contains("Bearer ") && !blackListedToken.contains(token)) {
				token = token.substring(7);
				TokenUtil.validateToken(token, userSignupRequest.getUserName());

			}
		} catch (IllegalAccessException e) {
			message = e.getMessage();
		}
		return message;
	}

	public ResponseEntity<JwtResponse> refreshToken(String refreshToken) throws IllegalAccessException {
		try {
			if (!StringUtils.isEmpty(refreshToken) && refreshToken.contains("Bearer ")
					&& !blackListedToken.contains(refreshToken)) {
				refreshToken = refreshToken.substring(7);
				String userName = TokenUtil.extractAllClaims(refreshToken).getSubject();
				System.out.println(userName);
				UserDetailEntity detailEntity = userRepository.findByUserName(userName).orElse(null);
				if (Objects.nonNull(detailEntity)) {
					boolean isValid = TokenUtil.validateToken(refreshToken, userName);
					if (isValid) {
						String newAccessToken = "Bearer " + TokenUtil.generateAccessToken(userName);
						String newRefreshToken = "Bearer " + TokenUtil.generateRefreshToken(userName);
						return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefreshToken));
					}
				}

			}
		} catch (Exception e) {
			throw new IllegalAccessException("Refresh token is invalid");
		}
		return null;

	}

	public String revokeToken(String revokeToken) throws IllegalAccessException {
		blackListedToken.add(revokeToken);
		return "Token revoked";
	}
}
