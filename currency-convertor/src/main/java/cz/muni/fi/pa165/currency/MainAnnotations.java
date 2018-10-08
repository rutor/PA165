package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String [] arguments) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");
        CurrencyConvertor convertor = applicationContext.getBean(CurrencyConvertor.class);
        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("CZK");
        BigDecimal amount = new BigDecimal(1);
        BigDecimal result = convertor.convert(sourceCurrency, targetCurrency, amount);
        System.out.print(result.longValue());
    }
}
