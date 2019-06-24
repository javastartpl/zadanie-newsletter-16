package pl.javastart.newsletter16.linksshorter.controller;

public enum Page {
  HOME_PAGE("index"),
  LINK_DETAILS("linkDetails"),
  LINK_NOT_EXIST("linkNotExist"),
  REMOVE_LINK("removeLink"),
  REMOVE_SUCCESS("removeSuccess"),
  ERROR("error"),
  CREATED("created");

  private String page;

  Page(String page) {
    this.page = page;
  }

  public String value() {
    return page;
  }
}
