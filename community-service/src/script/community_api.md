xxx/publish  发布

xxx/delete/{id} 删除

xxx/{id}      得到详细信息

xxx=post 、comment、 reply

#POST:post/publish 发帖
-----------------------
BODY:  
{  
   	"title":"第二条帖子",  
	"username":"jk",  
	"content":"这是第二条帖子哦！！！"  
}   
return：  
true/false


#GET:post/{id} 查询帖子
-----
return：    
{  
    "postId": 0,  
    "username": "cbc",  
    "title": "第一条帖子",  
    "content": "这是第一条帖子哦！！！",  
    "createTime": null,  
    "likeNum": 0,  
    "commentNum": 0,  
    "status": 0  
}  
or  
null

#GET:post/delete/{id} 删除帖子
---
return：
true/false    

#POST：comment/publish 发表评论
---
BODY：  
{  
	"postId":1,  
	"username":"jhj",  
	"content":"宁说的对!"  
}  
return true/false
  
#GET：comment/{id} 查询评论
---
return：  
{  
    "commentId": 19,   
    "postId": 1,  
    "username": "jj",  
    "content": "宁说的对!",  
    "createTime": "2019-12-06T12:32:03.000+0000",  
    "status": 0,  
    "replyNum": 0,  
    "likeNum": 0  
}

#GET：comment/delete/{id} 删除评论
---
return true/false

#POST：reply/publish 发表评论
---
{  
	"username":"jdj",  
	"commentId":"19",  
	"cotent":"对对对！"  
}  
#GET：reply/{id} 查询评论
---
return  
{  
    "replyId": 8,  
    "username": "jj",  
    "commentId": 21,  
    "content": null,  
    "createTime": "2019-12-06T13:01:06.000+0000",  
    "status": 0  
}
#GET：(un)like/post?name=  &&  post-id=  
---
name为username
#GET:(un)like/comment?name= && comment-id=
---
name为username