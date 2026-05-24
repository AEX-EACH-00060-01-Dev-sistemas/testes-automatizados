import java.util.List;

public class CheckoutService {

    /**
     * Complexidade maior de negócio para testes cobrir cenários sobrepostos
     * - Descontos por Volume em Camadas: Compras acima de R$ 500 ganham 5%. Acima de R$ 1000 ganham 20%.
     * - Desconto VIP: Membros VIP ganham 10% extras.
     * - Limite (Cap) de Desconto: Para evitar prejuízos, a soma dos descontos percentuais nunca pode ultrapassar 25%.
     * - Cupom Fixo: O cupom "BEMVINDO50" retira R$ 50,00 do valor.
     * - Proteção de Preço: O valor final da compra nunca pode ser menor que zero, mesmo se o cupom for maior que o valor da compra.
     */
    private static final double MAX_DISCOUNT_PERCENTAGE = 0.25; // Cap de 25%

    public double calculateTotal(List<Item> cart, boolean isVip, String couponCode) {
        if (cart == null || cart.isEmpty()) {
            return 0.0;
        }

        double subtotal = cart.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        double discountPercentage = 0.0;

        if (isVip) discountPercentage += 0.10;

        if (subtotal >= 1000.0) {
            discountPercentage += 0.20;
        } else if (subtotal >= 500.0) {
            discountPercentage += 0.05;
        }

        discountPercentage = Math.min(discountPercentage, MAX_DISCOUNT_PERCENTAGE);

        double fixedDiscount = 0.0;
        if ("BEMVINDO50".equals(couponCode)) {
            fixedDiscount = 50.0;
        } else if (couponCode != null && !couponCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Cupom inválido");
        }

        double totalAfterPercentage = subtotal * (1.0 - discountPercentage);
        double finalTotal = totalAfterPercentage - fixedDiscount;

        return Math.max(finalTotal, 0.0);
    }
}