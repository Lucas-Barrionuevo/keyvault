package com.keyVault.app.service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyVault.app.dto.PasswordDTO;
import com.keyVault.app.dto.PasswordResponse;
import com.keyVault.app.dto.PasswordResponse2;
import com.keyVault.app.entity.Password;
import com.keyVault.app.exceptions.ResourceNotFoundException;
import com.keyVault.app.repository.CategoryRepository;
import com.keyVault.app.repository.IconRepository;
import com.keyVault.app.repository.PasswordRepository;
import com.keyVault.app.repository.UserRepository;
import com.keyVault.app.security.TokenUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
@Service
public class PasswordService {
	@Autowired
	private PasswordRepository passwordRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private IconRepository iconRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	private TokenUtils tokenUtils;
	private final static String ACCESS_TOKEN_SECRET = "$10$tTpH1I6caxJcn6uS.zWab.jiWcCQRp5SqklTezw2JUy21w3wBDRo";
	private HttpServletRequest request;
	
	public int decryptToken() {
		String bearerToken = request.getHeader("Authorization");
		String token = bearerToken.replace("Bearer ", "");
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		String idString = claims.getSubject();
		return Integer.parseInt(idString);
	}
	public PasswordResponse2 createPassword (PasswordDTO passwordDTO) {
		Password password = mappingEntity(passwordDTO);
		password.setCreatedAt(new Date());
		if(password.getCategory()!=null) {
			password.setCategory(categoryRepository.findById(password.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", password.getCategory().getId())));
		}
		if (password.getIcon() != null) {
			password.setIcon(iconRepository.findById(password.getIcon().getId()).orElseThrow(() -> new ResourceNotFoundException("Icon", "id", password.getIcon().getId())));
		}
		if(password.getUser()== null) {
			password.setUser(userRepository.findById(password.getUser().getId()).orElseThrow(()-> new ResourceNotFoundException("User", "id",password.getUser().getId() )));
		}
		Password newPassword = passwordRepository.save(password);
		
		PasswordDTO PasswordDTO = mappingDTO(newPassword);
		PasswordResponse2 passwordResponse = mappingResponse2(PasswordDTO);
		return passwordResponse;
	}
	
	public List<PasswordResponse2> findAllPasswordsForUser(){
		int user_id = decryptToken();
		List<Password> AllPasswords = passwordRepository.findByUser_id(user_id);
		List<PasswordDTO> AllPasswordsDTO = AllPasswords.stream().map(password -> mappingDTO(password)).collect(Collectors.toList());
		List<PasswordResponse2> AllResponsePasswords = AllPasswordsDTO.stream().map(passwordDTO -> mappingResponse2(passwordDTO)).collect(Collectors.toList());
		return AllResponsePasswords;
	}
	
	public PasswordResponse findPasswordById(int id) {
		Password password = passwordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Password", "id", id));
		password.setSeenqty(password.getSeenqty() + 1);
		return mappingResponse(mappingDTO(password));
	}
	
	public PasswordResponse updatePassword(PasswordDTO passwordDTO, int id) {
		Password password = passwordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Password", "id", id));
		
		password.setContent(passwordDTO.getContent());
		password.setUrl(passwordDTO.getUrl());
		password.setName(passwordDTO.getName());
		password.setCategory(passwordDTO.getCategory());
		password.setIcon(passwordDTO.getIcon());
		password.setUserOrMail(passwordDTO.getUserOrMail());
		
		Password updatedPassword = passwordRepository.save(password);
		return mappingResponse(mappingDTO(updatedPassword));
	}
	
	public void deletePassword(int id) {
		Password password = passwordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Password", "id", id));
		passwordRepository.delete(password);
	}
	public PasswordResponse2 mappingResponse2 (PasswordDTO passwordDTO) {
		PasswordResponse2 passwordResponse2 = new PasswordResponse2();
		passwordResponse2.setCategory(passwordDTO.getCategory());
		passwordResponse2.setCreatedAt(passwordDTO.getCreatedAt());
		passwordResponse2.setIcon(passwordDTO.getIcon());
		passwordResponse2.setId(passwordDTO.getId());
		passwordResponse2.setName(passwordDTO.getName());
		passwordResponse2.setSeenqty(passwordDTO.getSeenqty());
		passwordResponse2.setUrl(passwordDTO.getUrl());
		passwordResponse2.setUserOrMail(passwordDTO.getUserOrMail());
		return passwordResponse2;
	}
	public PasswordResponse mappingResponse (PasswordDTO passwordDTO) {
		PasswordResponse passwordResponse = new PasswordResponse();
		passwordResponse.setContent(passwordDTO.getContent());
		passwordResponse.setCategory(passwordDTO.getCategory());
		passwordResponse.setCreatedAt(passwordDTO.getCreatedAt());
		passwordResponse.setIcon(passwordDTO.getIcon());
		passwordResponse.setId(passwordDTO.getId());
		passwordResponse.setName(passwordDTO.getName());
		passwordResponse.setSeenqty(passwordDTO.getSeenqty());
		passwordResponse.setUrl(passwordDTO.getUrl());
		passwordResponse.setUserOrMail(passwordDTO.getUserOrMail());
		return passwordResponse;
	}
	public PasswordDTO mappingDTO (Password password) {
		PasswordDTO passwordDTO = modelMapper.map(password, PasswordDTO.class);
		return passwordDTO;
	}
	
	public Password mappingEntity (PasswordDTO passwordDTO) {
		Password password = modelMapper.map(passwordDTO, Password.class);
		return password;
	}
}
