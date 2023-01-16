package com.test.command;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoadCommand implements Command {
    private String filename;
    @Override
    public String getType() {
        return Constants.LOAD;
    }
}
