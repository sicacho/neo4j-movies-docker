package movies.spring.data.neo4j.services;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MovieService {

    @Autowired MovieRepository movieRepository;

    private Map<String, Object> toD3Format(Iterator<Map<String, Object>> result) {
        List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> rels= new ArrayList<Map<String,Object>>();
        int i=0;
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            nodes.add(map("title",row.get("movie"),"label","movie"));
            int target=i;
            i++;
            for (Object name : (Collection) row.get("cast")) {
                Map<String, Object> actor = map("title", name,"label","actor");
                int source = nodes.indexOf(actor);
                if (source == -1) {
                    nodes.add(actor);
                    source = i++;
                }
                rels.add(map("source",source,"target",target));
            }
        }
        return map("nodes", nodes, "links", rels);
    }

    private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> result = new HashMap<String,Object>(2);
        result.put(key1,value1);
        result.put(key2,value2);
        return result;
    }

    public Map<String, Object> graph(int limit) {
        Iterator<Map<String, Object>> result = movieRepository.graph(limit).iterator();
        return toD3Format(result);
    }

    public void insertMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public Iterable<Movie> getAll() {
        return movieRepository.findAll();
    }
}
