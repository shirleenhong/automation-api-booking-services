package com.cwt.bpg.cbt.finance;

import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends CommonRepository<User, String>{

	private static final String UID = "uid";

	public UserRepository() {
		super(User.class, UID);
	}
}
