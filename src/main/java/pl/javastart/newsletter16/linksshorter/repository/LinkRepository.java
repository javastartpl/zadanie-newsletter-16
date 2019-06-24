package pl.javastart.newsletter16.linksshorter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.javastart.newsletter16.linksshorter.link.Link;

import java.util.Optional;

public interface LinkRepository extends MongoRepository<Link, String> {
  Optional<Link> findByIdAndDeletingCode(String id, String deletingCode);
}
