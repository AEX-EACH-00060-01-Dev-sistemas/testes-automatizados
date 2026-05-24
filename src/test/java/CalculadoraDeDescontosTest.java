import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraDeDescontosTest {

    private CalculadoraDeDescontos calculadora = new CalculadoraDeDescontos();

    @Test
    void naoDeveAplicarDescontoParaCompraPequenaDeClienteNormal() {
        double resultado = calculadora.calcularPrecoFinal(500.0, false);

        assertEquals(500.0, resultado, "O preço deve permanecer o mesmo sem descontos");
    }

    @Test
    void deveAplicarDezPorcentoParaCompraGrandeDeClienteNormal() {
        double resultado = calculadora.calcularPrecoFinal(1000.0, false);

        assertEquals(900.0, resultado, "Deve aplicar 10% de desconto pelo valor da compra");
    }

    @Test
    void deveAplicarCincoPorcentoParaCompraPequenaDeClienteVIP() {
        double resultado = calculadora.calcularPrecoFinal(500.0, true);

        assertEquals(475.0, resultado, "Deve aplicar 5% de desconto por ser VIP");
    }

    @Test
    void deveSomarDescontosParaCompraGrandeDeClienteVIP() {
        double resultado = calculadora.calcularPrecoFinal(1000.0, true);

        assertEquals(850.0, resultado, "Deve aplicar 15% de desconto total (Volume + VIP)");
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNegativo() {
        // assertThrows == bloqueio de valores inválidos (Exception)
        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> calculadora.calcularPrecoFinal(-100.0, false)
        );

        // Checa se a mensagem é igual
        assertEquals("O preço não pode ser negativo", excecao.getMessage());
    }
}