package pl.mrwojcik.tornbot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface Command {

    String getName();
    Mono<Void> execute(List<String> args, MessageCreateEvent event);

}
