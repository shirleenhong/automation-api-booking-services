package com.cwt.bpg.cbt.finance;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetUser() {
        String uid = "UID";
        User user = new User();
        when(repository.get(uid)).thenReturn(user);

        User result = service.getUser(uid);

        assertThat(result, is(equalTo(user)));

        verify(repository).get(uid);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveUser() {
        User user = new User();
        when(repository.put(user)).thenReturn(user);

        User result = service.saveUser(user);

        assertThat(result, is(equalTo(user)));

        verify(repository).put(user);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldDeleteUser() {
        String uid = "UID";
        when(repository.remove(uid)).thenReturn(uid);

        String result = service.deleteUser(uid);

        assertThat(result, is(equalTo(uid)));

        verify(repository).remove(uid);
        verifyNoMoreInteractions(repository);
    }
}