package pl.mrwojcik.tornbot.commands.fun;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;
import pl.mrwojcik.tornbot.commands.Command;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BrilliantCommand implements Command {

    @Override
    public String getName() { return "brilliant"; }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("https://tenor.com/1RDm.gif"))
                .then();
    }

}
