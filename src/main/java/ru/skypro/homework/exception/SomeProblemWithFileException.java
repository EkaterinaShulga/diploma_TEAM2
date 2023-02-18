package ru.skypro.homework.exception;

public class SomeProblemWithFileException extends RuntimeException{

    public SomeProblemWithFileException(){
        super("Something wrong, file can not be add");
    }
}
