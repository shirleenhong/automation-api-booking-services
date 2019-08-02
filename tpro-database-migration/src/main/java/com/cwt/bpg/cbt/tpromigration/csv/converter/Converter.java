package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.List;

public interface Converter<S, T>
{
    void convert(S source, List<T> results);
}
