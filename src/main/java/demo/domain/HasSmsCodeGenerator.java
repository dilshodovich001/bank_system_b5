package demo.domain;

import java.util.Random;

public interface HasSmsCodeGenerator {
    default Integer generatedCode(){
        return new Random().nextInt(1000) + 8999;
    }
}
