package com.cwt.bpg.cbt.agent;

import java.util.List;

import javax.annotation.Resource;

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
	
	@Cacheable(cacheNames = "agent", key="#uid", condition="#uid != null")
	public AgentInfo getAgent(String uid, String countryCode) {
		return agentRepository.get(uid, countryCode);
	}

    public List<AgentInfo> getAgents() {
        return agentRepository.getAll();
    }

    @CacheEvict(cacheNames = "agent", key = "#uid", condition="#uid != null")
	public AgentInfo save(AgentInfo agentInfo) {
		return agentRepository.put(agentInfo);
	}

	@CacheEvict(cacheNames = "agent", key = "#uid", condition="#uid != null")
	public String delete(String uid, String countryCode) {
		return agentRepository.remove(uid, countryCode);
	}

}
