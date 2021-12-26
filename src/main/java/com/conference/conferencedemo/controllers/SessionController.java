package com.conference.conferencedemo.controllers;

import com.conference.conferencedemo.models.Session;
import com.conference.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionController {
    private final SessionRepository sessionRepository;

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getById(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //Also need to check for children records before deleting
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //because this is a PUT, we expect all attributes to be passed in. A PATCH would only need what
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
