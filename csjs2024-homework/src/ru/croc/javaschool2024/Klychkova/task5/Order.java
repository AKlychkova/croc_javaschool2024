package ru.croc.javaschool2024.Klychkova.task5;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Order implements StatusChangeable {
    private final String code;
    private final LocalDateTime createdDate;
    private LocalDateTime collectedDate;
    private LocalDateTime closedDate;
    private final Product[] productsList;
    private String clientName;
    private String clientPhone;
    private OrderStatus status;

    public String getCode() {
        return code;
    }

    public Order(String clientName, String clientPhone, Product... productsList) {
        if (clientName == null || clientPhone == null || productsList == null) {
            throw new InvalidParameterException("Parameters cannot be null.");
        }
        if (productsList.length > 10) {
            throw new InvalidParameterException("Order cannot contain more than 10 products.");
        }

        this.createdDate = LocalDateTime.now();
        this.productsList = productsList;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.status = OrderStatus.CREATED;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        this.code = createdDate.format(formatter) + clientPhone.substring(clientPhone.length() - 4);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getCollectedDate() {
        return collectedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public Product[] getProductsList() {
        return Arrays.copyOf(productsList, productsList.length);
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void collect() {
        if (status == OrderStatus.CREATED) {
            status = OrderStatus.COLLECTED;
            collectedDate = LocalDateTime.now();
        } else {
            throw new IllegalStateException(String.format("Cannot collect order from status %s", status.toString()));
        }
    }

    public void close() {
        if (status == OrderStatus.COLLECTED) {
            status = OrderStatus.CLOSED;
            closedDate = LocalDateTime.now();
        } else {
            throw new IllegalStateException(String.format("Cannot close order from status %s", status.toString()));
        }
    }

    public String getNotificationText() {
        if (status != OrderStatus.COLLECTED) {
            throw new IllegalStateException("Order is not collected");
        }
        StringBuilder productsDescription = new StringBuilder();
        BigDecimal sum = BigDecimal.ZERO;
        for (var product : productsList) {
            sum = sum.add(BigDecimal.valueOf(product.getPrice()));
            productsDescription.append(String.format("%s, %.2f₽\n", product.getName(), product.getPrice()));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return String.format("""
                Добрый день, %s!
                
                Рады сообщить, что Ваш заказ %s собран и готов к выдаче.
                
                Состав заказа:
                %s
                
                Сумма к оплате: %.2f₽
                
                
                С наилучшими пожеланиями, магазин “Кошки и картошки”,
                %s г.
                """, clientName, code, productsDescription,sum,LocalDateTime.now().format(formatter));
    }
}
