package org.sse.communityservice.dto;

/**
 * @author HPY
 */
public class LikeDTO {

    private String username;
    private long type;
    private long typeId;
    private String likedUsername;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setType_id(long typeId) {
        this.typeId = typeId;
    }

    public String getLikedUsername() {
        return likedUsername;
    }

    public void setLikedUsername(String likedUsername) {
        this.likedUsername = likedUsername;
    }



}
