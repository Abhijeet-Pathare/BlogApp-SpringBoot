package com.codesoft.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesoft.blog.entities.User;
import com.codesoft.blog.exceptions.ResourceNotFoundException;
import com.codesoft.blog.payloads.UserDto;
import com.codesoft.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	//created an object of modelmapper because it was giving a null pointer exception
	//some problem in autowiring to be checked
	@Autowired
	private static ModelMapper modelMapper = new ModelMapper();

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		userRepo.save(user);
		return userDto;
	}

	//used OrElseThrow to throw exception using custom ResoureNotFoundException 
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ", userId));
		return userToDto(user);
	}

	//User Stream apis Map function to convert eact user to dto
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> userDtoList =  users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user =  userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User "," Id ", userId));
		userRepo.delete(user);
	}
	
	//modelMapper used instead of 
	private static User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto,User.class);
		return user;
	}
	private static UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
}
/**
 * 	//We have created these two methods to convert the dto to entity and vice versa
	private static User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto,User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
 * 	private static UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}
 */

