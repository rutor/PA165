package cz.muni.fi.pa165.currency;

import org.junit.*;
import org.junit.runner.*;
import java.math.BigDecimal;
import java.util.Currency;

import org.mockito.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); 

    @Mock
    ExchangeRateTable exchangeRateTable;

    Currency czk = Currency.getInstance("CZK");
    Currency eur = Currency.getInstance("EUR");
    Currency usd = Currency.getInstance("USD");

    @Test
    public void testConvert() throws Exception {

        when(exchangeRateTable.getExchangeRate(eur, czk)).thenReturn(new BigDecimal("25.00"));

        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
        BigDecimal result;

        result = convertor.convert(eur, czk, new BigDecimal("1"));
        assertThat(result).isEqualTo("25.00");

        result = convertor.convert(eur, czk, new BigDecimal("1.1"));
        assertThat(result).isEqualTo("27.50");

        result = convertor.convert(eur, czk, new BigDecimal("1.01"));
        assertThat(result).isEqualTo("25.25");

        // Rouding down
        result = convertor.convert(eur, czk, new BigDecimal("1.001"));
        assertThat(result).isEqualTo("25.02");

        // Rounding up
        result = convertor.convert(eur, czk, new BigDecimal("1.003"));
        assertThat(result).isEqualTo("25.08");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
        convertor.convert(null, czk, new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
        convertor.convert(eur, null, new BigDecimal("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
        convertor.convert(eur, czk, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(usd, czk)).thenReturn(null);

        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
        convertor.convert(Currency.getInstance("USD"), czk, new BigDecimal("1"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws Exception {

        ExternalServiceFailureException externalServiceFailureException = new ExternalServiceFailureException("error");

        when(exchangeRateTable.getExchangeRate(eur, czk)).thenThrow(externalServiceFailureException);

        CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);

        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> convertor.convert(eur, czk, BigDecimal.ONE))
                .withCause(externalServiceFailureException)
                .withMessage("Error when looking up for exchange rate EUR -> CZK");
    }
}
