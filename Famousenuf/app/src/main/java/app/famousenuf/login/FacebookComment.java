package app.famousenuf.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bharatbhusan on 2/7/16.
 */
/*{
        "created_time": "2016-07-02T00:01:08+0000",
        "from": {
        "name": "Bharat Bhusan",
        "id": "1013046948790510"
        },
        "message": "good",
        "id": "1013231108772094_1013240602104478"
        }*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookComment {
    @JsonProperty("message")
    private String message;

    @JsonProperty("id")
    private String id;

    @JsonProperty("from")
    private FacebookUserDetail userDetail;

    public FacebookComment() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FacebookUserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(FacebookUserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
