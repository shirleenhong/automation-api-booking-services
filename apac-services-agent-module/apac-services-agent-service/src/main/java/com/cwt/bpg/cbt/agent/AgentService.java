package com.cwt.bpg.cbt.agent;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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

	@Autowired
	private CacheManager cacheManager;
	
	@Cacheable(cacheNames = "agent", key="#uid", condition="#uid != null")
	public AgentInfo getAgent(String uid, String countryCode) {
		return agentRepository.get(uid, countryCode);
	}

    public List<AgentInfo> getAgents() {
        return agentRepository.getAll();
    }

    @CacheEvict(cacheNames = "agent", key = "#uid", condition="#uid != null")
	public AgentInfo save(AgentInfo agentInfo) {
		AgentInfo savedAgentInfo = agentRepository.put(agentInfo);
		clearCache();
		return savedAgentInfo;
	}

	@CacheEvict(cacheNames = "agent", key = "#uid", condition="#uid != null")
	public String delete(String uid, String countryCode) {
		String agentId = agentRepository.remove(uid, countryCode);
		clearCache();
		return agentId;
	}

	private void clearCache() {
		Cache cache = cacheManager.getCache("agent");
		if (cache != null) {
			cache.clear();
		}
	}

}
