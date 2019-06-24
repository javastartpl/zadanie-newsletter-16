package pl.javastart.newsletter16.linksshorter.link;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = Collection.LINKS)
public class Link {
  @Id private String id;

  private String destination;
  private LocalDateTime created;
  private long visits;
  private String deletingCode;
}
