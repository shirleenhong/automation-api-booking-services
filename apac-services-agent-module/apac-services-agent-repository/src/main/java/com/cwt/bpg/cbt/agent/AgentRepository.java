package com.cwt.bpg.cbt.agent;

import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.agent.model.AgentInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AgentRepository extends CommonRepository<AgentInfo, String> {

	private static final String ID_COLUMN = "id";
	private static final String UID_COLUMN = "uid";

	public AgentRepository() {
		super(AgentInfo.class, ID_COLUMN);
	}

	public AgentInfo get(String uid) {
		final Query<AgentInfo> query = morphia.getDatastore().createQuery(AgentInfo.class);
		query.field(UID_COLUMN).equal(uid);
		return query.get();
	}
}
