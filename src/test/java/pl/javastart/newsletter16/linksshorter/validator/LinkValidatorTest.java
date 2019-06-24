package pl.javastart.newsletter16.linksshorter.validator;

import org.junit.Test;
import pl.javastart.newsletter16.linksshorter.link.Link;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LinkValidatorTest {

  @Test
  public void shouldReturnTrueWhenLinksAreCorrect() {
    // given
    Link linkWithHttp = createLink("http://google.pl");
    Link linkWithHttpAndWWW = createLink("http://www.google.pl");
    Link linkWithHttpsAndWWW = createLink("https://www.google.pl");
    Link linkWithHttps = createLink("https://google.pl");
    Link simpleLink = createLink("google.pl");
    Link linkWithWWW = createLink("www.google.pl");

    DestinationValidator linkValidator = new DestinationValidator();

    // when then
    assertThat(linkValidator.validate(linkWithHttp)).isTrue();
    assertThat(linkValidator.validate(linkWithHttpAndWWW)).isTrue();
    assertThat(linkValidator.validate(linkWithHttpsAndWWW)).isTrue();
    assertThat(linkValidator.validate(linkWithHttps)).isTrue();
    assertThat(linkValidator.validate(simpleLink)).isTrue();
    assertThat(linkValidator.validate(linkWithWWW)).isTrue();
  }

  @Test
  public void shouldReturnFalseWhenLinkIsInvalid() {
    // given
    Link link = createLink("invalidLink");

    DestinationValidator linkValidator = new DestinationValidator();

    // when then
    assertThat(linkValidator.validate(link)).isFalse();
  }

  private Link createLink(String url) {
    Link link = new Link();
    link.setDestination(url);
    return link;
  }
}
