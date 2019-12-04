package Bean;

import java.util.Date;

public class Post {
    private int post_id;
    private String username;
    private String title;
    private String content;
    private Date create_time;
    private int like_num;
    private int reply_num;
    private int comment_num;
    private int status;

    public Post(int post_id, String username, String title, String content, Date create_time, int like_num, int reply_num,
                int comment_num, int status){
        this.post_id = post_id;
        this.username = username;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.like_num = like_num;
        this.reply_num = reply_num;
        this.comment_num = comment_num;
        this.status = status;
    }
}
