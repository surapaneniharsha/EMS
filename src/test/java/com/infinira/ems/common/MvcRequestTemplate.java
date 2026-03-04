package com.infinira.ems.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class MvcRequestTemplate {

    @Autowired
    protected MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    protected String postRequest(String url, Object body, String msgOnEx) {
        try {
            String json = mapper.writeValueAsString(body);

            MvcResult result = mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            return result.getResponse().getContentAsString();

        } catch (Exception e) {
            throw new RuntimeException(msgOnEx, e);
        }
    }

    protected String getRequest(String url, String msgOnEx) {
        try {
            MvcResult result = mvc.perform(get(url))
                    .andExpect(status().isOk())
                    .andReturn();

            return result.getResponse().getContentAsString();

        } catch (Exception e) {
            throw new RuntimeException(msgOnEx, e);
        }
    }

    protected String putRequest(String url, Object body, String msgOnEx) {
        try {
            String json = mapper.writeValueAsString(body);

            MvcResult result = mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andReturn();

            return result.getResponse().getContentAsString();

        } catch (Exception e) {
            throw new RuntimeException(msgOnEx, e);
        }
    }

    protected String deleteRequest(String url, String msgOnEx) {
        try {
            MvcResult result = mvc.perform(delete(url))
                    .andExpect(status().isOk())
                    .andReturn();

            return result.getResponse().getContentAsString();

        } catch (Exception e) {
            throw new RuntimeException(msgOnEx, e);
        }
    }

    protected <T> T convertJsonToObj(String json, Class<T> object) {
        try {
            return mapper.readValue(json, object);
        } catch (Exception e) {
            throw new RuntimeException("JSON conversion failed", e);
        }
    }
}