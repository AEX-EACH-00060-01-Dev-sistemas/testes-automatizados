public class CalculadoraDeDescontos {

    /**
     * Calcula o preço final com base nas seguintes regras:
     * - Compras a partir de R$ 1000,00 ganham 10% de desconto.
     * - Clientes VIP ganham 5% de desconto adicional.
     * - O preço original não pode ser negativo.
     */
    public double calcularPrecoFinal(double precoOriginal, boolean isClienteVip) {
        if (precoOriginal < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }

        double descontoPercentual = 0.0;

        if (precoOriginal >= 1000.0) {
            descontoPercentual += 0.10;
        }

        if (isClienteVip) {
            descontoPercentual += 0.05;
        }

        return precoOriginal * (1.0 - descontoPercentual);
    }
}