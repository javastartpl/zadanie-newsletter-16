package pl.javastart.newsletter16.linksshorter.validator;

import pl.javastart.newsletter16.linksshorter.link.Link;

public interface LinkValidator {
    boolean validateDestination(Link link);
    boolean validateId(String id);
    boolean validateDeletingCode(Link link);
}
