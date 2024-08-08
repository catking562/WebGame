package taewookim.WebGame.exception;

public enum ExceptionType {

    Server_Error(505),
    User_CantCollisionUserName(400),
    User_NotFound(404),
    ;

    private final int httpstate;

    ExceptionType(int httpstate) {
        this.httpstate = httpstate;
    }

    public int getHttpstate() {
        return httpstate;
    }

}
