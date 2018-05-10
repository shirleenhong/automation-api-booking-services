package com.cwt.bpg.cbt.services.rest.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;

public class ResponseWrapper extends HttpServletResponseWrapper {

	private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(bos);
	private long id;

	public ResponseWrapper(Long requestId, HttpServletResponse response) {
		super(response);
		this.id = requestId;
	}

	@Override
	public ServletResponse getResponse() {
		return this;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {

			private TeeOutputStream tee = new TeeOutputStream(
					ResponseWrapper.super.getOutputStream(), bos);
			
			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				//Do nothing
			}

			@Override
			public void write(int b) throws IOException {
				tee.write(b);
			}
		};
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new TeePrintWriter(super.getWriter(), writer);
	}

	public byte[] toByteArray() {
		return bos.toByteArray();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}