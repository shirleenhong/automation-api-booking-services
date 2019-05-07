package com.cwt.bpg.cbt.smartflow.service;

import com.cwt.bpg.cbt.smartflow.model.Codif;
import com.cwt.bpg.cbt.smartflow.repository.CodifRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodifService {

    @Autowired
    private CodifRepository codifRepository;

    @Cacheable(cacheNames = "codif", key = "{#gdsPropId.toUpperCase(), #keyType.toUpperCase()}", condition = "#gdsPropId != null && #keyType != null")
    public Codif getCodif(String gdsPropId, String keyType) {
        return codifRepository.getCodif(gdsPropId, keyType);
    }

    @Cacheable(cacheNames = "codif", key = "{#gdsPropId.toUpperCase(), #keyType.toUpperCase()}", condition = "#gdsPropId != null && #keyType != null")
    public List<Codif> getCodifsByGdsPropIdAndKeyType(String gdsPropId, String keyType) {
        return codifRepository.getCodifs(gdsPropId, keyType);
    }

    public List<Codif> getCodifs() {
        return codifRepository.getAll();
    }

    @CacheEvict(cacheNames = "codif", allEntries = true)
    public Codif save(Codif codif) {
        return codifRepository.put(codif);
    }

    public String remove(String id) {
        Codif codif = codifRepository.get(new ObjectId(id));
        if (codif != null) {
            return remove(codif.getGdsPropId(), codif.getKeyType());
        }
        return null;
    }

    @CacheEvict(cacheNames = "codif", allEntries = true)
    public Codif update(Codif codif) {
        codifRepository.updateCodif(codif);
        return codif;
    }

    @CacheEvict(cacheNames = "codif", key = "{#gdsPropId.toUpperCase(), #keyType.toUpperCase()}", condition = "#gdsPropId != null && #keyType != null")
    public String remove(String gdsPropId, String keyType) {
        return codifRepository.removeCodif(gdsPropId, keyType);
    }
}
