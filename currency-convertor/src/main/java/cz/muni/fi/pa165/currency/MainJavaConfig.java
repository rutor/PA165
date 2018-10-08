package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {
    public static void main(String [] arguments) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
        CurrencyConvertor convertor = applicationContext.getBean(CurrencyConvertor.class);
        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("CZK");
        BigDecimal amount = new BigDecimal(1);
        BigDecimal result = convertor.convert(sourceCurrency, targetCurrency, amount);
        System.out.print(result.longValue());
    }


}
