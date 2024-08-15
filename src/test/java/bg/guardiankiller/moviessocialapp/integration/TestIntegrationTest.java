package bg.guardiankiller.moviessocialapp.integration;

import bg.guardiankiller.moviessocialapp.model.dto.AuthRequestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.AuthResponseDTO;
import bg.guardiankiller.moviessocialapp.model.dto.Movie;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLanguage() throws Exception {
        mockMvc
                .perform(get("/api/languages"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                ["EN","BG"]""")
                );
    }

    @Test
    public void test() throws Exception {
        var reg = new UserRegisterDTO();
        reg.setUsername("admin123");
        reg.setPassword("admin123");
        reg.setEmail("admin123@abv.bg");
        reg.setConfirmPassword("admin123");
        reg.setFullName("Ivan Shopov");
        mockMvc
                .perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andDo(print())
                .andExpect(status().isCreated());

        var login = new AuthRequestDTO("admin123", "admin123");
        var result = mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(login)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        var response = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), AuthResponseDTO.class);
        System.out.println(response);

        var result2 = mockMvc.perform(get("/api/movies")
                .header("Authorization", "Bearer "+response.accessToken()))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                ).andReturn();
//        var response2 = objectMapper
//                .readValue(result2.getResponse().getContentAsByteArray(),
//                        new TypeReference<PageImpl<Movie>>() {});
//        System.out.println(response2.getSize());
    }
}
