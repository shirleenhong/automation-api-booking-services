package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
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
        when(repository.getAll()).thenReturn(remarks);

        List<Remark> result = service.getAll();

        assertNotNull(result);
        verify(repository, times(1)).getAll();
    }

    @Test
    public void getRemarkShouldReturnRemark() {
        Remark remark = mock(Remark.class);
        when(repository.get(new ObjectId("5b2870d6284b8d1ac84300ac"))).thenReturn(remark);

        Remark result = service.getRemark("5b2870d6284b8d1ac84300ac");

        assertNotNull(result);
        verify(repository, times(1)).get(new ObjectId("5b2870d6284b8d1ac84300ac"));
    }

    @SuppressWarnings({"unchecked"})
	@Test
    public void getRemarksShouldReturnRemarks() {
        Remark remark = mock(Remark.class);
        when(remark.getText()).thenReturn("ITIN REMARK");
    	List<Remark> remarks = Collections.singletonList(remark);
        when(repository.getRemarks("HK", "HL", "E")).thenReturn(remarks);

        List<String> result = service.getRemarks("HK", "HL", "E");

        assertNotNull(result);
        verify(repository, times(1)).getRemarks("HK", "HL", "E");
        verify(remark, times(1)).getText();
    }

    @Test
    public void saveShouldReturnSavedRemark() {
    	Remark remark = mock(Remark.class);
        when(repository.put(remark)).thenReturn(remark);

        Remark result = service.save(remark);

        assertNotNull(result);
        verify(repository, times(1)).put(any(Remark.class));
    }

    @Test
    public void deleteShouldReturnWriteResult() {
    	String remarkId = "5b2870d6284b8d1ac84300ad";
        when(repository.remove(new ObjectId(remarkId))).thenReturn("result");

        String result = service.delete(remarkId);

        assertNotNull(result);
        verify(repository, times(1)).remove(any(ObjectId.class));
    }
}
