import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private CheckoutService checkoutService;

    // Isolamento de testes - "limpeza" de variáveis
    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();
    }

    // @Nested == logs visuais.
    @Nested
    @DisplayName("Testes de Desconto Percentual e Limites (Cap)")
    class DiscountRulesTests {

         // @ParameterizedTest: executa o mesmo teste várias vezes, de acordo com um formato com CsvSource
        @ParameterizedTest(name = "Subtotal: {0}, VIP: {1} => Total Esperado: {2}")
        // "Fontes" de casos para testes parametrizados
        @CsvSource({
                "100.0,  false, 100.0",  // Sem desconto
                "100.0,  true,  90.0",   // Desconto VIP (10%)
                "500.0,  false, 475.0",  // Limite exato de 500 = 5% Volume
                "1000.0, false, 800.0",  // Limite exato de 1000 = 20% Volume
                "1000.0, true,  750.0"   // VIP (10%) + Volume (20%) = 30%. O Cap reduz para 25%. (1000 - 25% = 750)
        })
        @DisplayName("Deve calcular descontos garantindo que o limite máximo de 25% não seja ultrapassado")
        void shouldCalculateDiscountsWithCap(double price, boolean isVip, double expectedTotal) {
            Item item = new Item(price, 1);

            double result = checkoutService.calculateTotal(Arrays.asList(item), isVip, null);

            // delta == erro máximo permitido
            assertEquals(expectedTotal, result, 0.01, "O cálculo final do desconto falhou");
        }
    }

    @Nested
    @DisplayName("Testes de Cupons e Casos de Borda")
    class CouponAndEdgeCasesTests {

        @Test
        @DisplayName("Deve abater exatos R$ 50 com o cupom de boas-vindas")
        void shouldApplyFixedDiscountCoupon() {
            Item item = new Item(200.0, 1);
            double result = checkoutService.calculateTotal(Arrays.asList(item), false, "BEMVINDO50");

            assertEquals(150.0, result, 0.01);
        }

        @Test
        @DisplayName("Não deve permitir que o total fique negativo se o cupom for maior que a compra")
        void shouldNotAllowNegativeTotalWithCoupon() {
            Item item = new Item(30.0, 1);
            double result = checkoutService.calculateTotal(Arrays.asList(item), false, "BEMVINDO50");

            assertEquals(0.0, result, 0.01, "O sistema não deve devolver dinheiro (ficar negativo)");
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar usar um cupom inválido")
        void shouldThrowExceptionForInvalidCoupon() {
            Item item = new Item(500.0, 1);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> checkoutService.calculateTotal(Arrays.asList(item), false, "CUPOM_FALSO")
            );

            assertEquals("Cupom inválido", exception.getMessage());
        }

        @Test
        @DisplayName("Deve retornar 0.0 se o carrinho estiver vazio")
        void shouldReturnZeroForEmptyCart() {
            double result = checkoutService.calculateTotal(Collections.emptyList(), false, null);
            assertEquals(0.0, result, 0.01);
        }
    }
}