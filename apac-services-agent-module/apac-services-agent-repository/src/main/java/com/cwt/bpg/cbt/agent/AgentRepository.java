package com.cwt.bpg.cbt.agent;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.agent.model.AgentInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AgentRepository extends CommonRepository<AgentInfo, String> {

	public static final String KEY_COLUMN = "uid";

	public AgentRepository() {
		super(AgentInfo.class, KEY_COLUMN);
	}

	public AgentInfo get(String uid) {
		final Query<AgentInfo> query = morphia.getDatastore().createQuery(AgentInfo.class);
		query.field(KEY_COLUMN).equal(uid);
		return query.get();
	}
}
