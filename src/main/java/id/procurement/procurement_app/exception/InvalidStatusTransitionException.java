package id.procurement.procurement_app.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String current, String target) {
        super("Tidak bisa ubah status dari " + current + " menjadi " + target);
    }
}
