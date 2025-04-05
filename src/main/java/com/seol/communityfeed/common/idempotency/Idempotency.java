package com.seol.communityfeed.common.idempotency;

import com.seol.communityfeed.common.ui.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Idempotency {

    private final String key;
    private final Response<?> response;

}
