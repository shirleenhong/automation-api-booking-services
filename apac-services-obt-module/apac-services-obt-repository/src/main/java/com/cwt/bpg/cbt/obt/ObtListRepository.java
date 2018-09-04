package com.cwt.bpg.cbt.obt;

import com.cwt.bpg.cbt.obt.model.ObtList;
import org.springframework.stereotype.Repository;
import com.cwt.bpg.cbt.repository.*;

@Repository
public class ObtListRepository extends CommonRepository<ObtList, String> {

    public static final String KEY_COLUMN = "countryCode";

    public ObtListRepository() {
        super(ObtList.class, KEY_COLUMN);
    }
}