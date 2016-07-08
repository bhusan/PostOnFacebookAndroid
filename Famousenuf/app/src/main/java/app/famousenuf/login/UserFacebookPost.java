package app.famousenuf.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bharatbhusan on 2/7/16.
 */

/*{
        "id": "669466673148541_932909910137548",
        "name": "Ram",
        "link": "https://www.facebook.com/photo.php?fbid=533592483347948&set=p.533592483347948&type=3",
        "message": "kyo be kamino ye pic yad hai? :P
        Amit Singh Rathore Bharat Bhusan Anup Singh Subrat Prasad"
        }*/
    @JsonIgnoreProperties(ignoreUnknown = true)
public class UserFacebookPost {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;

    @JsonProperty("link")
    private String link;

    @JsonProperty("full_picture")
    private String fullPicture;

    @JsonProperty("likes")
    private FacebookLikeList facebookLikeList;

    @JsonProperty("comments")
    private FacebookCommentList facebookCommentList;

    public UserFacebookPost()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public FacebookLikeList getFacebookLikeList() {
        return facebookLikeList;
    }

    public void setFacebookLikeList(FacebookLikeList facebookLikeList) {
        this.facebookLikeList = facebookLikeList;
    }

    public FacebookCommentList getFacebookCommentList() {
        return facebookCommentList;
    }

    public void setFacebookCommentList(FacebookCommentList facebookCommentList) {
        this.facebookCommentList = facebookCommentList;
    }

    public String getFullPicture() {
        return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }
}

