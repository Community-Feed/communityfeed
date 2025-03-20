package com.seol.communityfeed.post.domain.content;

import com.seol.communityfeed.post.domain.common.DateTimeInfo;

public abstract class Content {
    protected String contentText;
    protected final DateTimeInfo dateTimeInfo;

    protected Content(String contentText){
        checkTest(contentText);
        this.contentText = contentText;
        this.dateTimeInfo = new DateTimeInfo();
    }

    public void updateContent(String updateContent){
        checkTest(updateContent);
        this.contentText = updateContent;
        this.dateTimeInfo.updateEditDateTime();
    }

    protected abstract void checkTest(String contentText);

    public String getContentText() {
        return contentText;
    }
}
