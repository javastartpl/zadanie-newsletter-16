package pl.javastart.newsletter16.linksshorter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.javastart.newsletter16.linksshorter.link.Link;
import pl.javastart.newsletter16.linksshorter.service.LinkService;
import pl.javastart.newsletter16.linksshorter.validator.LinkValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class LinksController {
  private static final String LINK = "link";
  private static final String NOT_VALID = "notValid";
  private static final String NOT_DELETED = "notDeleted";

  private LinkService linkService;
  private LinkValidator linkValidator;

  public LinksController(LinkService linkService, LinkValidator linkValidator) {
    this.linkService = linkService;
    this.linkValidator = linkValidator;
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute(LINK, new Link());
    return Page.HOME_PAGE.value();
  }

  @PostMapping("/")
  public String makeLinkShort(
      @ModelAttribute Link link, Model model, HttpServletResponse httpServletResponse) {

    if (isDestinationNotValid(link)) {
      model.addAttribute(NOT_VALID, true);
      httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return Page.HOME_PAGE.value();
    }

    link = linkService.decapitateLink(link);
    model.addAttribute(LINK, link);
    return Page.CREATED.value();
  }

  private boolean isDestinationNotValid(@ModelAttribute Link link) {
    return !linkValidator.validateDestination(link);
  }

  @GetMapping("/link/{id}")
  public String redirectTo(@PathVariable String id, HttpServletResponse httpServletResponse) {

    if (isIdNotValid(id)) {
      return notFound(httpServletResponse);
    }
    return linkService.findDestination(id)
            .map(Redirect::to)
            .orElse(notFound(httpServletResponse));
  }

  @GetMapping("/link/{id}/details")
  public String getLinkDetails(
      @PathVariable String id, Model model, HttpServletResponse httpServletResponse) {

    if (isIdNotValid(id)) {
      return notFound(httpServletResponse);
    }

    Optional<Link> linkOptional = linkService.findById(id);
    if (linkOptional.isEmpty()) {
      return notFound(httpServletResponse);
    }
    model.addAttribute(LINK, linkOptional.get());
    return Page.LINK_DETAILS.value();
  }

  @GetMapping("/link/{id}/removing")
  public String toRemoveLinkPage(
      @PathVariable String id, Model model, HttpServletResponse httpServletResponse) {
    if (isIdNotValid(id)) {
      return notFound(httpServletResponse);
    }
    Link link = new Link();
    link.setId(id);
    model.addAttribute(LINK, link);

    return Page.REMOVE_LINK.value();
  }

  private boolean isIdNotValid(String id) {
    return !linkValidator.validateId(id);
  }

  private String notFound(HttpServletResponse httpServletResponse) {
    httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
    return Page.LINK_NOT_EXIST.value();
  }

  @PostMapping("/link/{id}/removing")
  public String removeLink(
      @ModelAttribute Link link, Model model, HttpServletResponse httpServletResponse) {
    if (linkValidator.validateDeletingCode(link)) {
      if (linkService.deleteLinkById(link)) {
        return Page.REMOVE_SUCCESS.value();
      }
    }
    model.addAttribute(NOT_DELETED, true);
    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return Page.REMOVE_LINK.value();
  }

  @GetMapping("/error")
  public String error(HttpServletResponse httpServletResponse) {
    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    return Page.ERROR.value();
  }
}
