package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.Remark;

public class RemarkServiceTest {

    @Mock
    private RemarkRepository repository;

    @InjectMocks
    private RemarkService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllShouldGetAllRemarks() {
        @SuppressWarnings("unchecked")
		List<Remark> remarks = mock(List.class);
        when(service.getAll()).thenReturn(remarks);

        List<Remark> result = service.getAll();

        assertNotNull(result);
        verify(repository, times(1)).getAll();
    }

    @Test
    public void getRemarkShouldReturnRemark() {
        Remark remark = mock(Remark.class);
        when(service.getRemark("5b2870d6284b8d1ac84300ac")).thenReturn(remark);

        Remark result = service.getRemark("5b2870d6284b8d1ac84300ac");

        assertNotNull(result);
        verify(repository, times(1)).get(new ObjectId("5b2870d6284b8d1ac84300ac"));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void getRemarksShouldReturnRemarks() {
    	List<Remark> remarks = mock(List.class);
        when(service.getRemarks("HK", "HL", "E")).thenReturn(remarks);

        List<Remark> result = service.getRemarks("HK", "HL", "E");

        assertNotNull(result);
        verify(repository, times(1)).getRemarks("HK", "HL", "E");
    }

    @Test
    public void saveShouldReturnSavedRemark() {
    	Remark remark = mock(Remark.class);
        when(service.save(remark)).thenReturn(remark);

        Remark result = service.save(remark);

        assertNotNull(result);
        verify(repository, times(1)).put(any(Remark.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
    	String remarkId = "5b2870d6284b8d1ac84300ad";
        when(service.delete(remarkId)).thenReturn("result");

        String result = service.delete(remarkId);

        assertNotNull(result);
        verify(repository, times(1)).remove(any(ObjectId.class));
    }
}
