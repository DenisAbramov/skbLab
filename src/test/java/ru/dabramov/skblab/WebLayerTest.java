package ru.dabramov.skblab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.dabramov.skblab.mail.listener.MessageListener;
import ru.dabramov.skblab.service.IUserService;
import ru.dabramov.skblab.service.MessagingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebLayerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService service;
    @MockBean
    private MessagingService messagingService;
    @MockBean
    private MessageListener messageListener;

    @Test
    public void getShouldReturnStatusOk() throws Exception
    {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
        this.mockMvc.perform(get("/index")).andExpect(status().isOk());
        this.mockMvc.perform(get("/registration")).andExpect(status().isOk());
    }
}