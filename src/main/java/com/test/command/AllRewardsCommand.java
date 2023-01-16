package com.test.command;

import lombok.Builder;

@Builder
public class AllRewardsCommand implements Command {

    private String partnerId;
    @Override
    public String getType() {
        return Constants.ALL_REWARDS;
    }

}
