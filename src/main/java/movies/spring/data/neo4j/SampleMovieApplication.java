package movies.spring.data.neo4j;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mh
 * @since 06.10.14
 */
@SpringBootApplication(exclude={Neo4jDataAutoConfiguration.class})
@Configuration
@Import(MyNeo4jConfiguration.class)
@Controller
public class SampleMovieApplication  {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(SampleMovieApplication.class, args);
  }

  @Autowired
  MovieService movieService;


  @RequestMapping(value = "/movie",method = RequestMethod.POST)
  public @ResponseBody String insertMovie(@RequestParam String title,@RequestParam int released,@RequestParam String tagline) {
    Movie movie = new Movie();
    movie.setTitle(title);
    movie.setReleased(released);
    movie.setTagline(tagline);
    movieService.insertMovie(movie);
    return "good";
  }

  @RequestMapping(value = "/movies",method = RequestMethod.GET)
  public @ResponseBody
  List<Movie> getAll() {
    List<Movie> movies = new ArrayList<>();
    movieService.getAll().forEach(movie -> movies.add(movie));
    return movies;
  }

}
