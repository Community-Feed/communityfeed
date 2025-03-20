package com.seol.communityfeed.post.domain.content;

public class PostContent extends Content {

    private static final int MIN_POST_LENGTH = 5;
    private static final int MAX_POST_LENGTH = 500;

    public PostContent(String content) {
        super(content);
    }

    @Override
    protected void checkTest(String contentText) {
        if (contentText == null || contentText.isEmpty()) {
            throw new IllegalStateException("게시글 내용은 비어있을 수 없습니다.");
        }

        if (contentText.length() > MAX_POST_LENGTH) {
            throw new IllegalStateException("게시글 내용이 너무 깁니다. (최대 " + MAX_POST_LENGTH + "자)");
        }

        if (contentText.length() < MIN_POST_LENGTH) {
            throw new IllegalStateException("게시글 내용이 너무 짧습니다. (최소 " + MIN_POST_LENGTH + "자)");
        }
    }
}
