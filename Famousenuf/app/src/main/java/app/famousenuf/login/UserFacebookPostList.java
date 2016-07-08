package app.famousenuf.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bharatbhusan on 2/7/16.
 */

/*"data": [
        {
        "id": "669466673148541_932909910137548",
        "name": "Ram",
        "link": "https://www.facebook.com/photo.php?fbid=533592483347948&set=p.533592483347948&type=3",
        "message": "kyo be kamino ye pic yad hai? :P
        Amit Singh Rathore Bharat Bhusan Anup Singh Subrat Prasad"}
        ]*/
    @JsonIgnoreProperties(ignoreUnknown = true)
public class UserFacebookPostList {

    @JsonProperty("data")
    List<UserFacebookPost> facebookPosts;

    public UserFacebookPostList()
    {

    }

    public List<UserFacebookPost> getFacebookPosts() {
        return facebookPosts;
    }

    public void setFacebookPosts(List<UserFacebookPost> facebookPosts) {
        this.facebookPosts = facebookPosts;
    }
}
