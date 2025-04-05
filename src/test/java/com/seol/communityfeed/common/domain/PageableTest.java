package com.seol.communityfeed.common.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageableTest {

    @Test
    void givenPageableIndexIsZero_whenGetOffset_thenShouldBeReturn0(){
        //given
        Pageable pageable = new Pageable();

        //when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        //then
        assertEquals(0, offset);
        assertEquals(10, limit);

    }

    @Test
    void givenPageableIndexIs25Size10_whenGetOffset_thenShouldBeReturn10(){
        //given
        Pageable pageable = new Pageable(2, 10);

        //when
        int offset = pageable.getOffset();
        int limit = pageable.getLimit();

        //then
        assertEquals(10, offset);
        assertEquals(10, limit);
    }

}
