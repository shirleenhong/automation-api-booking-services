package com.cwt.bpg.cbt.services.rest.filter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TransactionLog
{
    private String id;
    private String method;
    private String endpoint;
    private int status;
    private long duration;
    private String timestamp;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();
    private final Map<String, String> userIdentifiers = new HashMap<>();

    public TransactionLog(String id, String method, String endpoint,
            int status, long duration, String timestamp)
    {
        this.id = id;
        this.method = method;
        this.endpoint = endpoint;
        this.status = status;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public String getId()
    {
        return id;
    }

    public String getMethod()
    {
        return method;
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public int getStatus()
    {
        return status;
    }

    public long getDuration()
    {
        return duration;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public boolean isSuccess()
    {
        return 200 == status || 201 == status || 204 == status;
    }

    public String addParameter(String key, String value)
    {
        return parameters.put(key, value);
    }

    public String addHeader(String key, String value)
    {
        return headers.put(key, value);
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public Map<String, String> getParameters()
    {
        return parameters;
    }

    public void parseUserIdentifiers(final String param)
    {
        if (StringUtils.isNotBlank(param))
        {
            final String[] params = param.split(",");
            for (int i = 0; i < params.length; i++)
            {
                final String content = params[i];
                if (StringUtils.isNotBlank(content))
                {
                    final int index = content.indexOf(":");
                    final int length = content.length();
                    if (index != -1 && index + 1 <= length)
                    {
                        userIdentifiers.put(StringUtils.trim(content.substring(0, index)), StringUtils.trim(content.substring(index + 1, content.length())));
                    }
                }
            }
        }
    }

    public Map<String, String> getUserIdentifiers()
    {
        return Collections.unmodifiableMap(userIdentifiers);
    }

}
