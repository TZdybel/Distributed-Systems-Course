package pl.edu.agh.sr.ExchangeRatesProvider.Implementation;

import io.grpc.stub.StreamObserver;
import pl.edu.agh.sr.CurrenciesInquiry;
import pl.edu.agh.sr.CurrenciesResponse;
import pl.edu.agh.sr.Currency;
import pl.edu.agh.sr.ExchangeRatesServiceGrpc;

import java.util.HashMap;
import java.util.Map;

public class ExchangeRatesServiceImpl extends ExchangeRatesServiceGrpc.ExchangeRatesServiceImplBase {
    private Map<Currency, Double> exchangeRates;

    public ExchangeRatesServiceImpl() {
        exchangeRates = new HashMap<>();
        exchangeRates.put(Currency.EUR, 4.20);
        exchangeRates.put(Currency.USD, 3.80);
        exchangeRates.put(Currency.GBP, 4.90);
        exchangeRates.put(Currency.CHF, 3.70);
        exchangeRates.put(Currency.AUD, 2.68);
    }

    private double simulateExchangeChanges(Currency currency) {
        double value = exchangeRates.get(currency);
        double min = value - (Math.round((0.1 * value) * 100.0) / 100.0);
        double max = value + (Math.round((0.1 * value) * 100.0) / 100.0);
        double diff = max - min;
        double randomValue = min + Math.random() * diff;
        double tempRes = Math.floor(randomValue * 100);
        double finalRes = tempRes/100;
        return finalRes;
    }

    @Override
    public void exchangeRates(CurrenciesInquiry request, StreamObserver<CurrenciesResponse> responseObserver) {
        while (true) {
            CurrenciesResponse.Builder builder = CurrenciesResponse.newBuilder();
            for (Currency currency : request.getCurrenciesList()) {
                CurrenciesResponse.CurrencyExchange currencyExchange = CurrenciesResponse.CurrencyExchange.newBuilder()
                        .setName(currency)
                        .setExchange(simulateExchangeChanges(currency))
                        .build();
                builder.addCurrencyExchanges(currencyExchange);
            }
            responseObserver.onNext(builder.build());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
