package controllers;

import model.Greeting;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(path = "/backend")
public class MainController {

  @Autowired
  private UserRepository userRepository;

  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  private static final String template = "Hello %s!";
  private final AtomicLong counter = new AtomicLong();

  // inject via application.properties
  @Value("${welcome.message:test}")
  private String message = "Hello World";

  @RequestMapping("/")
  public String welcome(Map<String, Object> model) {
    model.put("message", this.message);
    return "welcome";
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
  }

  @GetMapping("/sleep")
  public String sleep(@RequestParam(value = "timeout", defaultValue = "1") Integer timeout) throws Exception {
    logger.info("I'm going to sleep for " + timeout + " seconds");
    Thread.sleep((long) (timeout*1000));
    return "sleep";
  }

  @GetMapping("/add")
  public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
    // @ResponseBody means the returned String is the response, not a view name
    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping("/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }
}
