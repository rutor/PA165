package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.*;

@EnableAspectJAutoProxy
public class JavaConfig {
    @Bean
    public CurrencyConvertor CurrencyConvertorImpl() {
        ExchangeRateTable table = new ExchangeRateTableImpl();
        return new CurrencyConvertorImpl(table);
    }
}
