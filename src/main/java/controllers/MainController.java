package controllers;

import java.util.concurrent.atomic.AtomicLong;

import model.Greeting;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepository;

@RestController
@RequestMapping(path = "/backend")
public class MainController {

  @Autowired
  private UserRepository userRepository;

  private static final String template = "Hello %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
  }

  @RequestMapping(method = RequestMethod.POST, path = "/sleep")
  public void sleep(@RequestParam(value = "timeout", defaultValue = "1") Integer timeout) throws Exception {
    Thread.sleep((long) (timeout*1000));
  }

  @GetMapping(path="/add") // Map ONLY GET Requests
  public @ResponseBody String addNewUser (@RequestParam String name
          , @RequestParam String email) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }
}
