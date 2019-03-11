package com.cashback.strategy;

import com.cashback.document.OrderEntry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ClassicCashbackStrategy implements CashbackStrategy {

    private Map<DayOfWeek, Double> percentuals;

    @PostConstruct
    public void init() {
        this.percentuals = this.createPercentuals();
    }

    @Override
    public Double calculate(OrderEntry orderEntry) {

        Map<DayOfWeek, Double> percentuals = this.percentuals;

        Double total = orderEntry.getProduct().getPrice() * orderEntry.getQuantity();

        Double percentualToday = percentuals.get(LocalDate.now().getDayOfWeek());

        Double cashback = BigDecimal.valueOf(percentualToday).multiply(BigDecimal.valueOf(total)).divide(BigDecimal.valueOf(100)).doubleValue();

        return cashback;
    }

    public Map<DayOfWeek, Double> createPercentuals() {

        Map<DayOfWeek, Double> map = new HashMap<>();

        map.put(DayOfWeek.SUNDAY, Double.valueOf(35));
        map.put(DayOfWeek.MONDAY, Double.valueOf(3));
        map.put(DayOfWeek.TUESDAY, Double.valueOf(5));
        map.put(DayOfWeek.WEDNESDAY, Double.valueOf(8));
        map.put(DayOfWeek.THURSDAY, Double.valueOf(13));
        map.put(DayOfWeek.FRIDAY, Double.valueOf(18));
        map.put(DayOfWeek.SATURDAY, Double.valueOf(25));

        return map;
    }
}
