package tcc2.desafio.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tcc2.desafio.Dificuldade;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChallengeClass {

    Dificuldade value();

}
