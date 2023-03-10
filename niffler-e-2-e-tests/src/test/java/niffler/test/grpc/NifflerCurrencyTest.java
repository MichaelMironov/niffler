package niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.grpc.niffler.grpc.CalculateRequest;
import guru.qa.grpc.niffler.grpc.CalculateResponse;
import guru.qa.grpc.niffler.grpc.CurrencyResponse;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NifflerCurrencyTest extends BaseGRPCTest {

    @Test
    void getAllCurrenciesTest() {
        CurrencyResponse allCurrencies =
                step("Get all currencies", () -> nifflerCurrencyBlockingStub.getAllCurrencies(Empty.getDefaultInstance()));
        assertEquals(4, allCurrencies.getAllCurrenciesList().size());
    }

    @Test
    void currencyConversionTest() {

        final CalculateRequest build = CalculateRequest.newBuilder()
                .setSpendCurrency(CurrencyValues.USD)
                .setAmount(100)
                .setDesiredCurrency(CurrencyValues.RUB)
                .build();

        final CalculateResponse calculateResponse =
                step("Currency conversion check.\n" +
                                "Currency - " + build.getSpendCurrency() + "\n" +
                                "DesiredCurrency -" + build.getDesiredCurrency() + "\n" +
                                "Amount: " + build.getAmount(),
                        () -> nifflerCurrencyBlockingStub.calculateRate(build));

        Assertions.assertEquals(6666.67, calculateResponse.getCalculatedAmount());
    }

    private Queue<CalculateResponse> sendStreamRequest(Queue<CalculateRequest> calculateRequests) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<Throwable> throwableCollector = new AtomicReference<>();
        return null;
    }
}
