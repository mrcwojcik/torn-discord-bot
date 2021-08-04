package pl.mrwojcik.tornbot.listeners;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import pl.mrwojcik.tornbot.commands.Command;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent>{

    private final List<Command> commands;

    public MessageCreateListener(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public <T extends Event> Mono<Void> execute(T event) {
        return Mono.just(((MessageCreateEvent) event).getMessage())
                .filter(message -> message.getContent().startsWith("!"))
                .switchIfEmpty(Mono.empty())
                .filter(message -> message.getAuthor().map(author -> !author.isBot()).orElse(false))
                .flatMap(message -> this.executeCommand(message, (MessageCreateEvent) event));
    }

    private Mono<Void> executeCommand(Message message, MessageCreateEvent event) {
        String[] messageSplit = message.getContent().substring(1).split(" ", 2);
        return commands.stream()
                .filter(command ->
                        command.getName().equals(messageSplit[0]))
                .findFirst()
                .map(command -> {
                    List<String> args = messageSplit.length == 2 ?
                            List.of(messageSplit[1].split(" ")) :
                            List.of();
                    return command.execute(args, event);
                })
                .orElse(Mono.empty());
    }


    @Override
    public Mono<Void> handleError(Throwable error) {
        System.out.println("Error handling " + getEventType() + ": " + error);
        return Mono.empty();
    }
}
