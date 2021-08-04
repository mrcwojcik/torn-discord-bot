package pl.mrwojcik.tornbot.commands.fun;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import pl.mrwojcik.tornbot.commands.Command;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public class BronsonCommand implements Command {

    @Override
    public String getName() {
        return "bronson";
    }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        Set<Snowflake> mentions = event.getMessage().getUserMentionIds();
        if (mentions.isEmpty()){
            return event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("https://www.youtube.com/watch?v=08rVNqKhjA0"))
                    .then();
        }
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("https://www.youtube.com/watch?v=08rVNqKhjA0" + " <@" + mentions.iterator().next().asLong() + ">"))
                .then();
    }
}
