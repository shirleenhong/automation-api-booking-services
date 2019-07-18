package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.List;

public interface ListConverter<S, T>
{
    List<T> convert(S source);
}
