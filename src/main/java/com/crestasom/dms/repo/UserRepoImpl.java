package com.crestasom.dms.repo;

import org.springframework.stereotype.Component;

import com.crestasom.dms.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserRepoImpl {

	private EntityManager em;

	public User findByUserName(String userName) {
		String sql = "select * from users where username=:userName";
		Query query = em.createNativeQuery(sql,User.class);
		query.setParameter("userName", userName);
		

		return (User) query.getResultList().get(0);
	}

}
