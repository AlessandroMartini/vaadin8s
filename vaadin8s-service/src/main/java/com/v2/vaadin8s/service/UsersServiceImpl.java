package com.v2.vaadin8s.service;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import com.v2.vaadin8s.model.entity.User;
import com.v2.vaadin8s.repository.UserRepository;

@ApplicationScoped
public class UsersServiceImpl implements UserService {
	
	private UserRepository userRepository = new UserRepository();

	public Collection<User> getAllUsers() {
		return userRepository.getAllUsers();
	}
	
	public List<User> fetchUsers(int offset, int limit) {
		return userRepository.fetchUsers(offset, limit);
	}
	
	public int countAllUsers() {
		System.out.println("service - countAllUsers");

		return userRepository.countAllUsers();
	}
	
	public boolean addUser(User user) {
		System.out.println("service");
		System.out.println(user);
		return userRepository.addUser(user);
	}
}
