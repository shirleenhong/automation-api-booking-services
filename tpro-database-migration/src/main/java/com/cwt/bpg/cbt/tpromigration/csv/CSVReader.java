package com.cwt.bpg.cbt.tpromigration.csv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.cwt.bpg.cbt.tpromigration.csv.converter.ListConverter;

public class CSVReader
{

    private static final Logger log = LoggerFactory.getLogger(CSVReader.class);

    public <T> List<T> parse(final String path, final ListConverter<Map<String, String>, T> converter) throws IOException
    {
        final List<T> results = new ArrayList<T>();
        final Resource resource = new ClassPathResource(path);
        try (final LineNumberReader io = new LineNumberReader(new InputStreamReader(resource.getInputStream())))
        {
            String content = null;
            String[] header = null;
            while ((content = io.readLine()) != null)
            {
                if (io.getLineNumber() == 1)
                {
                    header = content.split("\\|");
                }
                else
                {
                    final Map<String, String> map = new HashMap<>();
                    final String[] data = content.split("\\|");
                    for (int i = 0; i < header.length; i++)
                    {
                        map.put(header[i], data[i]);
                    }
                    results.addAll(converter.convert(map));
                }
            }
        }
        log.info("results size {}", results.size());
        return results;
    }

}
