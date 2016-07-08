package app.famousenuf.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bharatbhusan on 2/7/16.
 */

/*{
        "data": [
        {
        "created_time": "2016-07-02T00:01:08+0000",
        "from": {
        "name": "Bharat Bhusan",
        "id": "1013046948790510"
        },
        "message": "good",
        "id": "1013231108772094_1013240602104478"
        }
        ],
        "paging": {
        "cursors": {
        "before": "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEF4TXpJME1EWXdNakV3TkRRM09Eb3hORFkzTkRFM05qWTQZD",
        "after": "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEF4TXpJME1EWXdNakV3TkRRM09Eb3hORFkzTkRFM05qWTQZD"
        }
        }
        }*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookCommentList {
    @JsonProperty("data")
   private List<FacebookComment> facebookCommentList;

    public FacebookCommentList() {

    }

    public List<FacebookComment> getFacebookCommentList() {
        return facebookCommentList;
    }

    public void setFacebookCommentList(List<FacebookComment> facebookCommentList) {
        this.facebookCommentList = facebookCommentList;
    }
}
