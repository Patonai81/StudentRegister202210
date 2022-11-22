package hu.webuni.definition;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Data
public class PaymentMesssage {

    @NonNull
    private final Long stundentId;
    private  Long amounts;
}
