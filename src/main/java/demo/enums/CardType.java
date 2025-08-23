package demo.enums;

public enum CardType {
    HUMO("9860"),
    UZCARD("8600"),
    VISA("4916");

    private String code;


    CardType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
