package com.cwt.bpg.cbt.exchange.order.validator;

public interface Validator<T>
{
    public void validate(T input);
}
