package com.seol.communityfeed.user.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserInfoTest {

    @Test
    void givenNameAndProfileImage_whenCreated_thenThrowNothing(){
        //given
        String name = "abcd";
        String profileImageUrl = "";

        //when
        //then
        assertDoesNotThrow(()-> new UserInfo(name, profileImageUrl));
    }

    @Test
    void givenBlankNameAndProfileImage_whenCreated_thenThrowError(){
        //given
        String name = "";
        String profileImageUrl = "";

        //when
        //then
        assertThrows(IllegalStateException.class,()-> new UserInfo(name, profileImageUrl));
    }

}