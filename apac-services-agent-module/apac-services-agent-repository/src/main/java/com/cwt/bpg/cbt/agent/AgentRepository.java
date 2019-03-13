//package com.cwt.bpg.cbt.agent;
//
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.mongodb.morphia.query.Query;
//import org.springframework.stereotype.Repository;
//
//import com.cwt.bpg.cbt.airline.model.Airline;
//import com.cwt.bpg.cbt.repository.CommonRepository;
//
//@Repository
//public class AgentRepository extends CommonRepository<Agent, String>{
//
//	static final String UID = "uid";
//	
//	public AgentRepository() {}
//	
//	public List<Agent> getAgentPhone(String uid) {
//		
//		final Query<Agent> query = morphia.getDatastore().createQuery(Agent.class);
//		
//		if(StringUtils.isNotBlank(airlineCode)) {
//			query.field(UID).equalIgnoreCase(uid);
//		}
//
//		return query.asList();	
//	}
//}
