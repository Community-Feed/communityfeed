package com.seol.communityfeed.post.repository.entity.post;

import com.seol.communityfeed.post.domain.content.PostPublicationState;
import jakarta.persistence.AttributeConverter;

public class PostPublicationStateConverter implements AttributeConverter<PostPublicationState, String> {

    @Override
    public String convertToDatabaseColumn(PostPublicationState postPublicationState){
        return postPublicationState.name();
    }

    @Override
    public PostPublicationState convertToEntityAttribute(String s){
        return PostPublicationState.valueOf(s);
    }
}
