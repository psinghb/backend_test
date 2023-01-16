package com.test.command;

import com.test.exception.IllegalCommandException;
import com.test.util.Util;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterCommand implements Command {
    private RegisterCommand() {}
    private long partnerId;
    private Long parentPartnerId;

    public static void validate(String[] args) {
        if(args.length > 1 && !Util.isLong(args[1])) {
            throw new IllegalCommandException("Register command not in proper format");
        }

        if(args.length > 2 && !Util.isLong(args[2])) {
            throw new IllegalCommandException("Register command not in proper format");
        }
    }

    public static RegisterCommand create(String[] args) {
        validate(args);
        var com = new RegisterCommand();
        com.partnerId = Util.toLong(args[1]);
        if(args.length > 2) {
            com.parentPartnerId = Util.toLong(args[2]);
        }

        return com;
    }

    @Override
    public String getType() {
        return Constants.REGISTER;
    }
}
