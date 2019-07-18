package com.cwt.bpg.cbt.tpromigration.csv.model;

import java.util.ArrayList;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

public class AirMiscInfoGroup
{
    private String clientNumberAccountNumber;
    private final List<AirMiscInfo> airMiscInfo = new ArrayList<>();

    public boolean addAirMiscInfo(AirMiscInfo e)
    {
        return airMiscInfo.add(e);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clientNumberAccountNumber == null) ? 0 : clientNumberAccountNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AirMiscInfoGroup other = (AirMiscInfoGroup) obj;
        if (clientNumberAccountNumber == null)
        {
            if (other.clientNumberAccountNumber != null)
                return false;
        }
        else if (!clientNumberAccountNumber.equals(other.clientNumberAccountNumber))
            return false;
        return true;
    }

}
