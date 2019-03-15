package com.cwt.bpg.cbt.agent;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.agent.model.AgentInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AgentRepository extends CommonRepository<AgentInfo, ObjectId> {
	
	private static final String ID_COLUMN = "id";
	private static final String UID_COLUMN = "uid";
	private static final String COUNTRY_CODE_COLUMN = "countryCode";
	
	public AgentRepository() {
		super(AgentInfo.class, ID_COLUMN);
	}
	
	public AgentInfo getAgent(String uid, String countryCode) {
		Optional<AgentInfo> agentInfo = Optional.ofNullable(morphia.getDatastore().createQuery(AgentInfo.class)
													.field(UID_COLUMN).equalIgnoreCase(uid)
													.field(COUNTRY_CODE_COLUMN).equalIgnoreCase(countryCode)
													.get());
		return agentInfo.orElse(new AgentInfo());
	}
	
	public String removeAgent(String uid, String countryCode) {
		final Datastore datastore = morphia.getDatastore();
        
		final Query<AgentInfo> query =  datastore.createQuery(AgentInfo.class);
 		query.field(UID_COLUMN).equalIgnoreCase(uid);
		query.field(COUNTRY_CODE_COLUMN).equalIgnoreCase(countryCode);
		
		datastore.delete(query);
        return uid;
    }
}
