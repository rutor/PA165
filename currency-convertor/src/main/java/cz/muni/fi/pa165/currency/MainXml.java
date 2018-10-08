package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.junit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;


public class MainXml {
    public static void main(String [] arguments) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CurrencyConvertor convertor = (CurrencyConvertor)ctx.getBean("CurrencyConvertor");
        Currency sourceCurrency = Currency.getInstance("EUR");
        Currency targetCurrency = Currency.getInstance("CZK");
        BigDecimal amount = new BigDecimal(1);
        BigDecimal result = convertor.convert(sourceCurrency, targetCurrency, amount);
        System.out.print(result.longValue());
    }
}
