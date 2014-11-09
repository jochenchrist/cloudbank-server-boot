package cloudbank.controller;

import cloudbank.IncorrectChallengeResponseException;
import cloudbank.event.ChallengeCreatedEvent;
import cloudbank.event.CreateChallengeEvent;
import cloudbank.event.VerifyChallengeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ChallengeCreatedEvent> createChallenge(@RequestBody CreateChallengeEvent createChallengeEvent,
                                                                 UriComponentsBuilder builder) {
        logger.info("Create challenge for user {}", createChallengeEvent.user);

        if(createChallengeEvent.sleep != null) {
            try {
                Thread.sleep(createChallengeEvent.sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ChallengeCreatedEvent challengeCreatedEvent = new ChallengeCreatedEvent();

        challengeCreatedEvent.challengeId = UUID.randomUUID().toString();
        challengeCreatedEvent.add(new Link(builder.path("/api/challenges/verify").build().toUriString(), "verify"));

        return new ResponseEntity<>(challengeCreatedEvent, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyChallenge(VerifyChallengeEvent verifyChallengeEvent) {
        logger.info("Verify challenge id {}", verifyChallengeEvent.challengeId);

        if("123456".equals(verifyChallengeEvent.challengeId)) {
            logger.info("Challenge is correct");
        } else {
            throw new IncorrectChallengeResponseException();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void status() {
        logger.info("status");
    }

}
