package com.cwt.bpg.cbt.client.gst.model;

import java.util.List;

public class WriteClientGstInfoFileResponse {

    private byte[] data;
    private String filename;
    private List<String> lines;

    public WriteClientGstInfoFileResponse(){
        //default no-args constructor
    }

    public WriteClientGstInfoFileResponse(String filename, byte[] data, List<String> lines) {
        this.filename = filename;
        this.data = data;
        this.lines = lines;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
