package com.v2.vaadin8s.service;

import java.util.Collection;
import java.util.List;

import com.v2.vaadin8s.model.entity.User;

public interface UserService {
		public Collection<User> getAllUsers();
		public List<User> fetchUsers(int offset, int limit);
		public int countAllUsers();
		public boolean addUser(User user);
}
