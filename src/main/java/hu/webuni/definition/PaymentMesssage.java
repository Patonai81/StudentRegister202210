package hu.webuni.definition;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Data
public class PaymentMesssage {

    private final Long stundentId;
    private final Long amounts;
}
