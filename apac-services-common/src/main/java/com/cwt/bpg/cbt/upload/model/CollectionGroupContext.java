package com.cwt.bpg.cbt.upload.model;

import java.util.List;

public class CollectionGroupContext<T>
{
    private CollectionGroup collectionGroup;
    private List<T> data;

    public CollectionGroupContext(CollectionGroup collectionGroup, List<T> data)
    {
        this.collectionGroup = collectionGroup;
        this.data = data;
    }

    public CollectionGroup getCollectionGroup()
    {
        return this.collectionGroup;
    }

    public List<T> getData()
    {
        return data;
    }
}
