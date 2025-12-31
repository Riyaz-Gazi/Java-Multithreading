package tuf;

import java.util.*;

class CartItem {
    private int quantity;
    private double unitPrice;

    public CartItem(int quantity, double unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

public class CartItemsDemo {
    public static void main(String[] args) {
        List<CartItem> cartItems = Arrays.asList(
                new CartItem(2, 50.0),
                new CartItem(1, 100.0),
                new CartItem(3, 34.0)
        );

        final double sum = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();
        System.out.println(sum);

        final Optional<CartItem> max = cartItems.stream().max(Comparator.comparingDouble(item -> item.getQuantity() * item.getUnitPrice()));
        System.out.println(max.get());
    }
}
