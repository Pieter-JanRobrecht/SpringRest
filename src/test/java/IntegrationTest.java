import application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import repositories.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, IntegrationTest.Config.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class IntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @Test
  public void testIsGreetingOK() throws Exception {
    mockMvc.perform(get("/backend/greeting"))
            .andExpect(status().isOk());
  }

  @Configuration
  static class Config{
    @Bean
    public UserRepository userRepository(){
      return Mockito.mock(UserRepository.class);
    }
  }
}
