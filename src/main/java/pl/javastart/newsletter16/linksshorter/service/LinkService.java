package pl.javastart.newsletter16.linksshorter.service;

import org.springframework.stereotype.Service;
import pl.javastart.newsletter16.linksshorter.link.Link;
import pl.javastart.newsletter16.linksshorter.repository.LinkRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
  private static final String HTTP = "http";
  private static final String FULL_HTTP_STATEMENT = "http://";
  private static final int ONLY_DIGITS = 9;
  private final Random random = new Random();

  private LinkRepository linkRepository;

  public LinkService(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  public Link decapitateLink(Link link) {

    addHttpStatement(link);
    link.setDeletingCode(generateDeletingCode());
    link.setCreated(LocalDateTime.now());
    linkRepository.insert(link);

    return link;
  }

  private void addHttpStatement(Link link) {
    String destination = link.getDestination();
    if (notStartWithHttp(destination)) {
      link.setDestination(FULL_HTTP_STATEMENT + destination);
    }
  }

  private boolean notStartWithHttp(String destination) {
    return !destination.startsWith(HTTP);
  }

  private String generateDeletingCode() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      stringBuilder.append(random.nextInt(ONLY_DIGITS));
    }

    return stringBuilder.toString();
  }

  public Optional<Link> findById(String id) {
    return linkRepository.findById(id);
  }

  public boolean deleteLinkById(Link link) {
    if (checkDeletingCode(link)) {
      linkRepository.deleteById(link.getId());
      return true;
    }
    return false;
  }

  private boolean checkDeletingCode(Link link) {
    return findByIdAndDeletingCode(link).isPresent();
  }

  private Optional<Link> findByIdAndDeletingCode(Link link) {
    return linkRepository.findByIdAndDeletingCode(link.getId(), link.getDeletingCode());
  }

  public Optional<String> findDestination(String id) {
    return findById(id).stream()
            .peek(this::increaseVisit)
            .map(Link::getDestination)
            .findFirst();
  }

  private void increaseVisit(Link link) {
    link.setVisits(link.getVisits() + 1);
    linkRepository.save(link);
  }
}
