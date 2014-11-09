package cloudbank.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateChallengeEvent {
    public String user;
    public String usecase;
    public Long sleep;
}
