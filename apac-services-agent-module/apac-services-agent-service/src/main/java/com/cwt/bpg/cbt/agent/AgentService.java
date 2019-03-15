package com.cwt.bpg.cbt.agent;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.agent.model.AgentInfo;

@Service
public class AgentService {

	@Resource
	private AgentService proxy;

	@Autowired
	private AgentRepository agentRepository;
	
	@Cacheable(cacheNames = "agent", key="{#uid, #countryCode}", condition="#uid != null && #countryCode != null")
	public AgentInfo getAgent(String uid, String countryCode) {
		return agentRepository.getAgent(uid, countryCode);
	}

    public List<AgentInfo> getAgents() {
        return agentRepository.getAll();
    }

    @CacheEvict(cacheNames = "agent", allEntries=true)
	public AgentInfo save(AgentInfo agentInfo) {
		AgentInfo savedAgentInfo = agentRepository.put(agentInfo);
		return savedAgentInfo;
	}

	public String remove(String id) {
		AgentInfo agentInfo = agentRepository.get(new ObjectId(id));
		if(agentInfo!=null) {
			remove(agentInfo.getUid(), agentInfo.getCountryCode());
		}
		return agentRepository.remove(new ObjectId(id));
	}
	
	@CacheEvict(cacheNames = "agent", key="{#uid, #countryCode}", condition="#uid != null && #countryCode != null")
	public String remove(String uid, String countryCode) {
		return agentRepository.removeAgent(uid, countryCode);
	}
}
