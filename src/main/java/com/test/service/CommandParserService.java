package com.test.service;

import com.test.command.*;
import com.test.exception.IllegalCommandException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommandParserService {

    public static class CommandFormat {
        String command;
        int args;
        String usage;

        static CommandFormat create(String cm, String usage, int args) {
            return new CommandFormat(cm, usage, args);
        }

        CommandFormat(String cm, String usage, int args) {
            command = cm;
            this.args = args;
            this.usage = usage;
        }
    }

    private static final List<CommandFormat> COMMANDS = List.of(
            CommandFormat.create("REGISTER", """
                      REGISTER partnerId – registers partner identified by partnerId in the system. Example “REGISTER 1”
                    """, 1),
            CommandFormat.create("REGISTER", """
                     REGISTER partnerId parentPartnerId – registers partner identified by partnerId to parent partner identified by parentPartnerId. Example “REGISTER 2 1”
                    """, 2),
            CommandFormat.create("LOAD", """
                            LOAD filename – load sales report where filename represents a CSV document where each line is composed of 5 values in following order:
                            ◦ partnerId – type long, existing partner in the system
                            ◦ contractId – type long, unique contract id
                            ◦ contractType – type string, possible values: Tortoise or Rabbit ◦ date – type string representing a date in format YYYY-MM-DD ◦ action – type string, possible values: BEGIN or END""",
                    1),
            CommandFormat.create("LEVEL", """
                    LEVEL partnerId year quarter – list partner level for a given quarter. Example “LEVEL 1 2015 1”.
                    """, 3),
            CommandFormat.create("REWARDS", """
                    REWARDS partnerId year quarter – list partner level for a given quarter. Example “LEVEL 1 2015 1”.
                    """, 3),
            CommandFormat.create("ALL_REWARDS", """
                    ALL_REWARDS partnerId”.
                    """, 1));


    public Command parse(String commandString) {
        if (commandString == null || commandString.isBlank())
            throw new IllegalCommandException("Please type a command");
        String[] args = commandString.split(" ");
        String command = args[0];
        Optional<CommandFormat> commandFormat = COMMANDS.stream().filter(it -> it.command.equals(command) && (args.length - 1) == it.args).findFirst();
        if (!commandFormat.isPresent()) {
            throw new IllegalCommandException("Not a valid command or no proper arguments list passed for command " + commandString);
        }


        return switch (command) {
            case "REGISTER" -> RegisterCommand.create(args);
            case "LOAD" -> LoadCommand
                    .builder()
                    .filename(args[1])
                    .build();

            case "LEVEL" -> LevelCommand
                    .create(args);

            case "REWARDS" -> RewardsCommand.create(args);
            case "ALL_REWARDS" -> AllRewardsCommand.builder().build();

            default -> throw new RuntimeException();

        };
    }
}
