package pl.mrwojcik.tornbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ChainTime implements Command{

    @Override
    public String getName() {
        return "chain";
    }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Chain takes place every two weeks on Saturday. We start at 20:30 TCT. It's mandatory chain"))
                .then();
    }
}
