package pl.mrwojcik.tornbot.commands.events;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;
import pl.mrwojcik.tornbot.commands.Command;
import pl.mrwojcik.tornbot.entity.Event;
import pl.mrwojcik.tornbot.utils.RequestToApi;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EventCommand implements Command {

    private final RequestToApi requestToApi;
    private final DateTimeFormatter formatter;

    public EventCommand(RequestToApi requestToApi, DateTimeFormatter dateTimeFormatter) {
        this.requestToApi = requestToApi;
        this.formatter = dateTimeFormatter;
    }

    @Override
    public String getName() { return "event"; }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        Event eventFromDb = requestToApi.getClosestEvent();
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Closest event is: " + eventFromDb.getTitle() + " , date: " + formatter.format(eventFromDb.getEventDate()) + " TCT"))
                .then();
    }

}