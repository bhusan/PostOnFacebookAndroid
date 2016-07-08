package app.famousenuf.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bharatbhusan on 2/7/16.
 */
/*{
        "id": "1013046948790510",
        "name": "Bharat Bhusan"
        }*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookUserDetail {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public FacebookUserDetail() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
