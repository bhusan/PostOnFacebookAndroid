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
        "id": "1013046948790510",
        "name": "Bharat Bhusan"
        }
        ],
        "paging": {
        "cursors": {
        "before": "MTAxMzA0Njk0ODc5MDUxMAZDZD",
        "after": "MTAxMzA0Njk0ODc5MDUxMAZDZD"
        }
        }
        }*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookLikeList {

    @JsonProperty("data")
    private List<FacebookUserDetail> facebookLikeList;

    public FacebookLikeList() {

    }

    public List<FacebookUserDetail> getFacebookLikeList() {
        return facebookLikeList;
    }

    public void setFacebookLikeList(List<FacebookUserDetail> facebookLikeList) {
        this.facebookLikeList = facebookLikeList;
    }
}
