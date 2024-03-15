    package com.Intent.shop.dto;

    import com.fasterxml.jackson.annotation.JsonInclude;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import jakarta.validation.constraints.PositiveOrZero;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.experimental.Accessors;

    import java.math.BigDecimal;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class OrderDTO {
        private Long id;

        @NotNull(message = "userId is required")
        @Positive(message = "userId must be positive or zero")
        private Long userId;

        @NotNull(message = "productId is required")
        @Positive(message = "productId must be positive or zero")
        private Long productId;

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be positive or zero")
        private int quantity;

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be positive or zero")
        private BigDecimal totalPrice;
    }
