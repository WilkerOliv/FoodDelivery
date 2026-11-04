package br.com.fooddelivery.orders;
public enum OrderStatus {
    AWAITING_PAYMENT, AWAITING_ACCEPTANCE, ACCEPTED, PREPARING, READY, COMPLETED, REJECTED, CANCELLED;
    public String descriptionPtBr() {
        return switch(this) {
            case AWAITING_PAYMENT -> "aguardando pagamento";
            case AWAITING_ACCEPTANCE -> "aguardando a loja";
            case ACCEPTED -> "aceito pela loja";
            case PREPARING -> "em preparação";
            case READY -> "pronto";
            case COMPLETED -> "concluído";
            case REJECTED -> "recusado";
            case CANCELLED -> "cancelado";
        };
    }
}
