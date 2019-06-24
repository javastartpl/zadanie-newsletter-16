package pl.javastart.newsletter16.linksshorter.validator;

import org.springframework.stereotype.Component;
import pl.javastart.newsletter16.linksshorter.link.Link;

import java.util.regex.Pattern;

@Component
public class DeletingCodeValidator {

  private static final String DELETING_CODE_PATTERN = "\\d{4}";
  private final Pattern pattern;

  public DeletingCodeValidator() {
    this.pattern = Pattern.compile(DELETING_CODE_PATTERN);
  }

  public boolean validate(Link link) {
    return pattern.matcher(link.getDeletingCode()).matches();
  }
}
