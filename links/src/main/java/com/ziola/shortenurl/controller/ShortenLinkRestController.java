package com.ziola.shortenurl.controller;

import com.ziola.shortenurl.domain.LinkRequest;
import com.ziola.shortenurl.domain.LinkResponse;
import com.ziola.shortenurl.dto.Link;
import com.ziola.shortenurl.dto.LinkWithPassword;
import com.ziola.shortenurl.exception.LinkException;
import com.ziola.shortenurl.repository.LinkRepository;
import com.ziola.shortenurl.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequiredArgsConstructor
public class ShortenLinkRestController {

    private final LinkService shortenLinkService;
    private final LinkRepository repository;

    @RequestMapping(value = "/shorten" ,method = RequestMethod.POST)
    public LinkWithPassword shortLink(@RequestBody Link link) {
        checkIfCorrect(link);
        LinkRequest request = shortenLinkService.createShortenLink(link.getLink());
        LinkWithPassword shortenLink = new LinkWithPassword();
        shortenLink.setLink(request.getShortenLink());
        shortenLink.setPassword(request.getPassword());
        return shortenLink;
    }

    private void checkIfCorrect(Link link) {
        String linkToSite = link.getLink();
        if (isBlank(linkToSite)){
            throw new LinkException("Link nie może być pusty!");
        }
    }

    @RequestMapping(value = "/{link}", method = RequestMethod.GET)
    public LinkResponse fullLink(@PathVariable String link) {
        LinkRequest request = shortenLinkService.findAndUpdate(link);
        LinkResponse response = new LinkResponse();
        response.setFullLengthLink(request.getFullLengthLink());
        response.setVisits(request.getVisits());
        return response;
    }

    @DeleteMapping("/delete/{password}")
    public void deleteShortenLink(@PathVariable String password) {
        LinkRequest request = repository.findByPassword(password);
        repository.delete(request);
    }
}
