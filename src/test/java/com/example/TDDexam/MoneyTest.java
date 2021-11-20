package com.example.TDDexam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyTest {
    /**
     * 다중 통화를 지원하는 Money 객체.
     * <p>
     * 종목	        주	       가격	         합계
     * Norvartis     400	 150 CHF	   60000 CHF
     * IBM	        1000	  25 USD	   25000 USD
     * 합계        65000 USD
     * -------------------------------------------------
     * 기준	    변환	    환율
     * CHF	    USD	    1.5
     * -------------------------------------------------
     * <p>
     * $5 + 10CHF = $10(환율이 2:1일 경우)
     * $5 * 2 = $10
     * <p>
     * 이런 보고서를 작성하기 위해 어떤 기능들이 필요한지 기능들의 확신을 위해 테스트코드를 먼저 작성해보면서 진행.
     * <p>
     * * 통화가 다른 두 금액을 더해서 주어진 환율에 맞게 변한 금액을 결과로 얻을 수 있어야 함.
     * * 어떤 금액(주가)을 어떤 수(주식의 수)에 곱한 금액을 결과로 얻을 수 있어야 함.
     **/

    /**
     * Money Class
     */
    abstract static class Money {

        protected int amount;

        public boolean equals(Object o) {
            Money money = (Money) o;
            return amount == money.amount && getClass().equals(money.getClass());
        }

        static Dollar dollar(int amount) {
            return new Dollar(amount);
        }

        abstract Money times(int multiplier);
    }

    /**
     * Dollar Class
     */
    static class Dollar extends Money {

        public Dollar(int amount) {
            this.amount = amount;
        }

        public Money times(int multiplier) {
            return new Dollar(amount * multiplier);
        }
    }

    /**
     * Franc Class
     */
    static class Franc extends Money {

        public Franc(int amount) {
            this.amount = amount;
        }

        public Money times(int multiplier) {
            return new Franc(amount * multiplier);
        }
    }

    @Test
    public void testMultiplication() throws Exception {
        Dollar five = Money.dollar(5);
        assertEquals(new Dollar(10), five.times(2));
        assertEquals(new Dollar(15), five.times(3));
    }

    @Test
    public void testEquality() throws Exception {
        assertEquals(new Dollar(5), new Dollar(5));
        assertNotEquals(new Dollar(5), new Dollar(6));
        assertNotEquals(new Dollar(10), new Franc(10));
    }
}