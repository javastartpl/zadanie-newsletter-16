package pl.javastart.newsletter16.linksshorter.controller;

class Redirect {

  private static final String ADDRESS_TEMPLATE = "redirect:%s";

  static String to(String address) {

    return String.format(ADDRESS_TEMPLATE, address);
  }
}
