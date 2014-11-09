package cloudbank.event;

import org.springframework.hateoas.ResourceSupport;

public class ChallengeCreatedEvent extends ResourceSupport {
    public String challengeId;

}
