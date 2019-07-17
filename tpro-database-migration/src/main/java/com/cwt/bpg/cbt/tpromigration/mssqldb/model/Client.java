package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import static java.util.Objects.hash;
import java.util.Objects;

public class Client extends com.cwt.bpg.cbt.exchange.order.model.Client {

    private static final long serialVersionUID = 5685472794816584778L;
    private int cmpid;

	public int getCmpid() {
		return cmpid;
	}

	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}
	
	@Override
    public int hashCode()
    {
        return hash(super.getClientId(), super.getClientAccountNumber());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Client client = (Client) obj;
        
        return Objects.equals(super.getClientId(), client.getClientId()) 
            && Objects.equals(super.getClientAccountNumber(), client.getClientAccountNumber());
    }

}
