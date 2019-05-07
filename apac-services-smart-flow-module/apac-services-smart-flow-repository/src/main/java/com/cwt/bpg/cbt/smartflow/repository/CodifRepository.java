package com.cwt.bpg.cbt.smartflow.repository;

import com.cwt.bpg.cbt.repository.CommonRepository;
import com.cwt.bpg.cbt.smartflow.model.Codif;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CodifRepository extends CommonRepository<Codif, ObjectId> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodifRepository.class.getSimpleName());

    private static final String ID_COLUMN = "id";
    private static final String GDS_PROP_ID_COLUMN = "gdsPropId";
    private static final String KEY_TYPE_COLUMN = "keyType";
    private static final String HARP_NO_COLUMN = "harpNo";
    private static final String CRS_CODE_COLUMN = "crsCode";
    private static final String STATUS_COLUMN = "status";
    private static final String LAST_UPDATED_COLUMN = "lastUpdated";

    public CodifRepository() {
        super(Codif.class, ID_COLUMN);
    }

    public Codif getCodif(String gdsPropId, String keyType) {
        Optional<Codif> agentInfo = Optional.ofNullable(morphia.getDatastore().createQuery(Codif.class)
                .field(GDS_PROP_ID_COLUMN).equalIgnoreCase(gdsPropId)
                .field(KEY_TYPE_COLUMN).equalIgnoreCase(keyType)
                .get());
        return agentInfo.orElse(new Codif());
    }

    public List<Codif> getCodifs(String gdsPropId, String keyType) {
        final Query<Codif> query = morphia.getDatastore().find(Codif.class);
        query.field(GDS_PROP_ID_COLUMN).equalIgnoreCase(gdsPropId);
        query.field(KEY_TYPE_COLUMN).equalIgnoreCase(keyType);

        Optional<List<Codif>> codifs = Optional.ofNullable(query.asList());

        return codifs.orElse(new ArrayList<>());
    }

    public String removeCodif(String gdsPropId, String keyType) {
        final Datastore datastore = morphia.getDatastore();

        final Query<Codif> query = datastore.createQuery(Codif.class);
        query.field(GDS_PROP_ID_COLUMN).equalIgnoreCase(gdsPropId);
        query.field(KEY_TYPE_COLUMN).equalIgnoreCase(keyType);

        datastore.delete(query);
        return gdsPropId;
    }

    public String updateCodif(Codif codif) {
        final Datastore datastore = morphia.getDatastore();

        final Query<Codif> query = datastore.createQuery(Codif.class);
        query.field(GDS_PROP_ID_COLUMN).equalIgnoreCase(codif.getGdsPropId());
        query.field(KEY_TYPE_COLUMN).equalIgnoreCase(codif.getKeyType());

        final UpdateOperations<Codif> updateOperations = datastore.createUpdateOperations(Codif.class);

        updateOperations.set(HARP_NO_COLUMN, codif.getHarpNo());
        updateOperations.set(CRS_CODE_COLUMN, codif.getCrsCode());
        updateOperations.set(STATUS_COLUMN, codif.getStatus());
        updateOperations.set(LAST_UPDATED_COLUMN, codif.getLastUpdated());

        UpdateResults updateResults = datastore.update(query, updateOperations);

        if (updateResults.getUpdatedCount() > 0) {
            LOGGER.info(String.format("Codif: %s|%s updated!", codif.getGdsPropId(), codif.getKeyType()));
        } else {
            LOGGER.error(String.format("Error updating codif: %s|%s", codif.getGdsPropId(), codif.getKeyType()));
        }

        return codif.getGdsPropId();
    }
}
