package com.test.service;

import com.test.command.*;
import com.test.exception.IllegalCommandException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.PrintStream;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class CommandLineService {


    private final PrintStream out = System.out;
    private final CommandParserService commandParserService;
    private final PartnerService partnerService;
    private final ContractService contractService;

    private final Scanner scanner = new Scanner(System.in);
    private final Thread commandReaderThread = new Thread(() -> {
        while (true) {
            try {
                processCommand();
            } catch (IllegalCommandException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    @PostConstruct
    public void init() {
        commandReaderThread.start();
    }

    protected void processCommand() throws Exception {
        String input = scanner.nextLine();
        Command command = parse(input);
        processCommand(command);
    }

    protected void processCommand(Command command) throws Exception {
        if(command instanceof RegisterCommand registerCommand) {
            partnerService.register(registerCommand);
        }

        if(command instanceof LoadCommand loadCommand) {
            contractService.load(loadCommand);
        }

        if(command instanceof RewardsCommand rewardsCommand) {
            out.println(partnerService.rewards(rewardsCommand));
            return;
        }

        if(command instanceof AllRewardsCommand rewardsCommand) {
            out.println("TODO");
        }

        if(command instanceof LevelCommand levelCommand) {
            out.println(partnerService.getLevel(levelCommand));
        }
    }

    protected Command parse(String input) {
        return commandParserService.parse(input);
    }


}
