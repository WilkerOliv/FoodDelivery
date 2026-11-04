package br.com.fooddelivery.reservations;
public enum ReservationStatus {
    PENDING_PAYMENT, CONFIRMED, IN_SERVICE, COMPLETED, CANCELLED, NO_SHOW;
    public String descriptionPtBr() {
        return switch(this) {
            case PENDING_PAYMENT -> "aguardando pagamento";
            case CONFIRMED -> "confirmada";
            case IN_SERVICE -> "em atendimento";
            case COMPLETED -> "concluída";
            case CANCELLED -> "cancelada";
            case NO_SHOW -> "não compareceu";
        };
    }
}
