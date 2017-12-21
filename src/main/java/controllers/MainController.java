package controllers;

import model.Greeting;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repositories.UserRepository;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(path = "/backend")
public class MainController {

  @Autowired
  private UserRepository userRepository;

  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  private static final String template = "Hello %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
  }

  @GetMapping("/sleep")
  public String sleep(@RequestParam(value = "timeout", defaultValue = "1") Integer timeout) throws Exception {
    logger.info("I'm going to sleep for " + timeout + " seconds");
    Thread.sleep((long) (timeout*1000));
    logger.info("I'm awake now and I feel much better after some sleep");
    return "sleep";
  }

  @PostMapping("/add")
  public @ResponseBody String addNewUser (@Valid @RequestBody User user) {
    logger.info("Saving " + user );
    userRepository.save(user);
    logger.info(user + " saved");
    return "Saved";
  }

  @GetMapping("/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    logger.info("Retrieving all users");
    return userRepository.findAll();
  }
}
