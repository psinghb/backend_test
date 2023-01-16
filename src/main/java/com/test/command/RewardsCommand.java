package com.test.command;

import com.test.exception.IllegalCommandException;
import com.test.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RewardsCommand extends LevelCommand {
    private Long partnerId;
    private int year;
    private int quarter;

    @Override
    public String getType() {
        return Command.Constants.REWARDS;
    }

    static void validate(String[] args) {
        if(args.length != 4) throw new IllegalCommandException("Level Command must have 3 arguments");
        if(!Util.isLong(args[1])
                || !Util.isLong(args[2])
                || !Util.isLong(args[3])
        ) {
            throw new IllegalCommandException("Level command 3 arguments must be of integer type");
        }

        var quarter = Util.toInt(args[3]);
        if(quarter < 1 && quarter > 4) throw new IllegalCommandException("Quarter in command must be [1-4]. Given is: "+ quarter);
    }

    public static RewardsCommand create(String[] args) {
        validate(args);
        var com = new RewardsCommand();
        com.partnerId = Util.toLong(args[1]);
        com.year = Util.toInt(args[2]);
        com.quarter = Util.toInt(args[3]);
        return com;
    }
}
