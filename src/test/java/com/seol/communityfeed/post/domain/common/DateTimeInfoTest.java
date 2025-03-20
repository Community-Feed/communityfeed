package com.seol.communityfeed.post.domain.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeInfoTest {

    @Test
    void givenCreated_whenUpdated_thenTimeAndStateArsUpdated(){
        //given
        DateTimeInfo dateTimeInfo = new DateTimeInfo();
        LocalDateTime localDateTime = dateTimeInfo.getDateTime();

        //when
        dateTimeInfo.updateEditDateTime();

        //then
        assertTrue(dateTimeInfo.isEdited());
        assertNotEquals(localDateTime, dateTimeInfo.getDateTime());
    }

}