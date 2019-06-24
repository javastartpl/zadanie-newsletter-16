package pl.javastart.newsletter16.linksshorter.validator;

import org.springframework.stereotype.Component;
import pl.javastart.newsletter16.linksshorter.link.Link;

@Component
public class LinkValidatorImpl implements LinkValidator {

  private DeletingCodeValidator deletingCodeValidator;
  private DestinationValidator destinationValidator;
  private LinkIdValidator linkIdValidator;

  public LinkValidatorImpl(
      DeletingCodeValidator deletingCodeValidator,
      DestinationValidator destinationValidator,
      LinkIdValidator linkIdValidator) {
    this.deletingCodeValidator = deletingCodeValidator;
    this.destinationValidator = destinationValidator;
    this.linkIdValidator = linkIdValidator;
  }

  @Override
  public boolean validateDestination(Link link) {
    return this.destinationValidator.validate(link);
  }

  @Override
  public boolean validateId(String id) {
    return this.linkIdValidator.validate(id);
  }

  @Override
  public boolean validateDeletingCode(Link link) {
    return this.deletingCodeValidator.validate(link);
  }
}
