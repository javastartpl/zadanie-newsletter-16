package pl.javastart.newsletter16.linksshorter.validator;

import org.springframework.stereotype.Component;
import pl.javastart.newsletter16.linksshorter.link.Link;

import java.util.regex.Pattern;

@Component
public class LinkIdValidator {
  private static final String ID_PATTERN = "^\\w+$";

  private final Pattern pattern;

  public LinkIdValidator() {
    this.pattern = Pattern.compile(ID_PATTERN);
  }

  public boolean validate(String id) {
    return pattern.matcher(id).matches();
  }
}
