package com.seol.communityfeed.post.domain.content;

public class CommentContent extends Content {

    private static final int MAX_CONTENT_LENGTH = 100;

    public CommentContent(String content){
        super(content);
    }

    @Override
    protected void checkTest(String contentText) {
        if(contentText == null || contentText.isEmpty()){
            throw new IllegalArgumentException();
        }

        if(MAX_CONTENT_LENGTH < contentText.length()){
            throw new IllegalArgumentException();
        }
    }


}
