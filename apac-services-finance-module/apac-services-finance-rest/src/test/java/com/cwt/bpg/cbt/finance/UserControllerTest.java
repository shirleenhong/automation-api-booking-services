package com.cwt.bpg.cbt.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getUserShouldReturnUser() throws Exception {
        final String uid = "UID";
        User user = new User();
        when(service.getUser(uid)).thenReturn(user);

        mockMvc.perform(get("/user/" + uid)
                .contentType(MediaType.TEXT_PLAIN).content(uid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).getUser(uid);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void saveUserShouldReturnSavedUser() throws Exception {
        User user = new User();
        when(service.saveUser(user)).thenReturn(user);

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(user)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).saveUser(any(User.class));
        verifyNoMoreInteractions(service);
    }
    
    @Test
    public void saveUserShouldIdentifyUnsafeHtmlContent() throws Exception {
        User user = new User();
        user.setUid("<script>alert(1)</script>");

        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(user)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteUserShouldReturnUidOfDeletedUser() throws Exception {
        final String uid = "UID";
        when(service.deleteUser(uid)).thenReturn(uid);

        mockMvc.perform(delete("/user/" + uid)
                .contentType(MediaType.TEXT_PLAIN).content(uid))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(service).deleteUser(uid);
        verifyNoMoreInteractions(service);
    }

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}