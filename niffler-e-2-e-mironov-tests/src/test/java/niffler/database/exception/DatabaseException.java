package niffler.database.exception;

public class DatabaseException extends RuntimeException{

    public DatabaseException(Throwable throwable){
        super(throwable);
    }

}
