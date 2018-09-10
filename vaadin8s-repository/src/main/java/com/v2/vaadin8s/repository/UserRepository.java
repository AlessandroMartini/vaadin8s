package com.v2.vaadin8s.repository;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;
import com.v2.vaadin8s.model.entity.User;
import org.jnosql.artemis.document.DocumentTemplate;

@ApplicationScoped
public class UserRepository {
	
	@Inject
	private DocumentTemplate template;

	public boolean addUser(User user) {
		System.out.println("repository");
		System.out.println(user);
		return template.insert(user) != null;
	}

	public Collection<User> getAllUsers() {
		System.out.println("getAllUsers - antes");
		return template.select(select().from("User").build());
		
	}

	public int countAllUsers() {
		System.out.println("repository - countAllUsers");
		return getAllUsers().size();
	}

	public List<User> fetchUsers(int offset, int limit) {
		System.out.println("fetchUsers - antes");
		return template.select(select().from("User").start(offset).limit(limit).build());
	}

}
