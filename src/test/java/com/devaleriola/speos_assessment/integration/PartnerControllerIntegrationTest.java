package com.devaleriola.speos_assessment.integration;

import com.devaleriola.speos_assessment.entities.partner.Partner;
import com.devaleriola.speos_assessment.entities.partner.PartnerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class PartnerControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:static/data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void getPartners() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/api/partners")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();

        List<Partner> partners = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        PartnerDto firstPartner = partners.get(0);
        assertEquals("Bells & Whistles", firstPartner.getName());
        assertEquals("xxxxxx", firstPartner.getReference());
        assertEquals(Locale.forLanguageTag("en-ES"), firstPartner.getLocale());
        assertEquals(OffsetDateTime.parse("2017-10-03T12:18:46+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), firstPartner.getExpirationTime());

        PartnerDto secondPartner = partners.get(1);
        assertEquals("Emanuel Greer", secondPartner.getName());
        assertEquals("xxxxx2", secondPartner.getReference());
        assertEquals(Locale.forLanguageTag("en-GB"), secondPartner.getLocale());
        assertEquals(OffsetDateTime.parse("2017-10-03T12:18:46+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), secondPartner.getExpirationTime());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void getPartnersWithWrongPage() throws Exception {
        mvc.perform(
                        get("/api/partners?from=-1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mvc.perform(
                        get("/api/partners?size=0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:static/data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void getPartner() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/api/partners/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();

        Partner partner = objectMapper.readValue(result.getResponse().getContentAsString(), Partner.class);
        assertEquals("Bells & Whistles", partner.getName());
        assertEquals("xxxxxx", partner.getReference());
        assertEquals(Locale.forLanguageTag("en-ES"), partner.getLocale());
        assertEquals(OffsetDateTime.parse("2017-10-03T12:18:46+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), partner.getExpirationTime());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void createPartner() throws Exception {
        ResultActions resultActions = mvc.perform(
                        post("/api/partners")
                                .content("""
                                                {
                                                    "name"              : "New Partner",
                                                    "reference"         : "xxxxxx",
                                                    "locale"            : "fr_BE",
                                                    "expirationTime"    : "2017-10-03T12:18:46+00:00"
                                                }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();

        Partner partner = objectMapper.readValue(result.getResponse().getContentAsString(), Partner.class);
        assertEquals("New Partner", partner.getName());
        assertEquals("xxxxxx", partner.getReference());
        assertEquals(Locale.forLanguageTag("fr-BE"), partner.getLocale());
        assertEquals(OffsetDateTime.parse("2017-10-03T12:18:46+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), partner.getExpirationTime());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:static/data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void createDuplicatePartner() throws Exception {
        mvc.perform(
                        post("/api/partners")
                                .content("""
                                                {
                                                    "name"              : "New Partner",
                                                    "reference"         : "xxxxxx",
                                                    "locale"            : "fr_BE",
                                                    "expirationTime"    : "2017-10-03T12:18:46+00:00"
                                                }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void createPartnerWithWrongLocale() throws Exception {
        mvc.perform(
                        post("/api/partners")
                                .content("""
                                                {
                                                    "name"              : "New Partner",
                                                    "reference"         : "xxxxxx",
                                                    "locale"            : "ABCDEF",
                                                    "expirationTime"    : "2017-10-03T12:18:46+00:00"
                                                }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:static/data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void updatePartner() throws Exception {
        ResultActions resultActions = mvc.perform(
                        put("/api/partners/1")
                                .content("""
                                                {
                                                    "name"              : "New Partner",
                                                    "reference"         : "xxxxx6",
                                                    "locale"            : "fr_BE",
                                                    "expirationTime"    : "2017-10-03T13:18:46+00:00"
                                                }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();

        Partner partner = objectMapper.readValue(result.getResponse().getContentAsString(), Partner.class);
        assertEquals("New Partner", partner.getName());
        assertEquals("xxxxx6", partner.getReference());
        assertEquals(Locale.forLanguageTag("fr-BE"), partner.getLocale());
        assertEquals(OffsetDateTime.parse("2017-10-03T13:18:46+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), partner.getExpirationTime());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void updateNonExistingPartner() throws Exception {
        mvc.perform(
                        put("/api/partners/0")
                                .content("""
                                                {
                                                    "name"              : "New Partner",
                                                    "reference"         : "xxxxx6",
                                                    "locale"            : "fr_BE",
                                                    "expirationTime"    : "2017-10-03T12:18:46+00:00"
                                                }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:static/data.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void deletePartner() throws Exception {
        mvc.perform(
                        delete("/api/partners/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:static/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    })
    public void deleteNonExistingPartner() throws Exception {
        mvc.perform(
                        delete("/api/partners/0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
