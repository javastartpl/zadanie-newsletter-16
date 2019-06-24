package pl.javastart.newsletter16.linksshorter.validator;

import org.springframework.stereotype.Component;
import pl.javastart.newsletter16.linksshorter.link.Link;

import java.util.regex.Pattern;

@Component
public class DestinationValidator {

  private static final String HTTP_WITH_WWW_PATTERN = "^http:\\/\\/www\\.\\w+\\..*$";
  private static final String HTTP_SIMPLE_PATTERN = "^http:\\/\\/\\w+\\..*$";
  private static final String ONLY_WWW_PATTERN = "^www\\.\\w+\\..*$";
  private static final String SIMPLE_URL_PATTERN = "^\\w+\\..*$";
  private static final String HTTPS_WITH_WWW_PATTERN = "^https:\\/\\/www\\.\\w+\\..*$";
  private static final String HTTPS_SIMPLE_PATTERN = "^https:\\/\\/\\w+\\..*$";

  private static final String LINK_PATTERN =
      HTTP_SIMPLE_PATTERN
          + '|'
          + HTTP_WITH_WWW_PATTERN
          + '|'
          + ONLY_WWW_PATTERN
          + '|'
          + SIMPLE_URL_PATTERN
          + '|'
          + HTTPS_WITH_WWW_PATTERN
          + '|'
          + HTTPS_SIMPLE_PATTERN;

  private final Pattern pattern;

  public DestinationValidator() {
    this.pattern = Pattern.compile(LINK_PATTERN);
  }

  public boolean validate(Link link) {
    return pattern.matcher(link.getDestination()).matches();
  }
}
