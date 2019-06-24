package com.cwt.bpg.cbt.agent;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.agent.model.AgentInfo;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    
    @Cacheable(cacheNames = "agent", key="{#uid.toUpperCase(), #countryCode.toUpperCase()}", condition="#uid != null && #countryCode != null")
    public AgentInfo getAgent(String uid, String countryCode) {
        return agentRepository.getAgent(uid, countryCode);
    }

    public List<AgentInfo> getAgents() {
        return agentRepository.getAll();
    }

    public AgentInfo save(AgentInfo agentInfo) {
        return agentRepository.put(agentInfo);
    }

    public String remove(String id) {
        AgentInfo agentInfo = agentRepository.get(new ObjectId(id));
        if(agentInfo!=null) {
            remove(agentInfo.getUid(), agentInfo.getCountryCode());
        }
        return agentRepository.remove(new ObjectId(id));
    }
    
    public String remove(String uid, String countryCode) {
        return agentRepository.removeAgent(uid, countryCode);
    }
}
