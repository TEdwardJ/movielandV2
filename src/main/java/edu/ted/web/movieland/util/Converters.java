package edu.ted.web.movieland.util;

import edu.ted.web.movieland.entity.ExchangeRate;

import java.util.function.BiFunction;

public class Converters {

    private final static BiFunction<Double, Double, Double> CONVERT_LOGIC = (input, rate) -> input / rate;
    private final static BiFunction<Double, Double, Double> REVERSED_CONVERT_LOGIC = (input, rate) -> input * rate;

    @FunctionalInterface
    public interface Converter {
        double convert(double amount);

        default Converter thenConvert(Converter converter) {
            return
                    (double in) -> {
                        var res = this.convert(in);
                        return converter.convert(res);
                    };
        }
    }

    public static class CurrencyRoundingConverter implements Converter {

        private CurrencyRoundingConverter() {
        }

        @Override
        public double convert(double amount) {
            return ((double)Math.round(amount * 100)) / 100;
        }
    }

    public static class CurrencyConverter implements Converter {

        private final BiFunction<Double, Double, Double> convertLogic;

        private final ExchangeRate rate;

        private CurrencyConverter(ExchangeRate rate) {
            this(CONVERT_LOGIC, rate);
        }

        private CurrencyConverter(BiFunction<Double, Double, Double> convertLogic, ExchangeRate rate) {
            this.rate = rate;
            this.convertLogic = convertLogic;
        }

        @Override
        public double convert(double amount) {
            return convertLogic.apply(amount, rate.getRate());
        }
    }

    public static Converter getConverter(ExchangeRate rate) {
        return new CurrencyConverter(rate).thenConvert(getRoundingConverter());
    }

    public static Converter getReversedConverter(ExchangeRate rate) {
        return new CurrencyConverter(REVERSED_CONVERT_LOGIC, rate).thenConvert(getRoundingConverter());
    }

    public static Converter getRoundingConverter() {
        return new CurrencyRoundingConverter();
    }
}
