package softuni.futsalleague.exeption;

public class NoEnoughResourceException extends RuntimeException{

    public NoEnoughResourceException(String messages) {
        super(messages);
    }
}
