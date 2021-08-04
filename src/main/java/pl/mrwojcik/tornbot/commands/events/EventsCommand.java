package pl.mrwojcik.tornbot.commands.events;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import org.springframework.stereotype.Component;
import pl.mrwojcik.tornbot.commands.Command;
import pl.mrwojcik.tornbot.entity.Event;
import pl.mrwojcik.tornbot.utils.RequestToApi;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EventsCommand implements Command {

    private final RequestToApi requestToApi;
    private final DateTimeFormatter formatter;

    public EventsCommand(RequestToApi requestToApi, DateTimeFormatter formatter) {
        this.requestToApi = requestToApi;
        this.formatter = formatter;
    }

    @Override
    public String getName() {
        return "events";
    }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        List<Event> allEvents = requestToApi.getEventsList();
        StringBuilder stringBuilder = new StringBuilder();
        for(Event e : allEvents){
            stringBuilder.append(e.getTitle() + ": " + formatter.format(e.getEventDate()) + " TCT")
                    .append(System.getProperty("line.separator"));
        }
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createEmbed(spec ->
                                spec.setColor(Color.BLUE)
                                        .setTitle("Events - Clockwork Orange")
                                        .setDescription(stringBuilder.toString())
                        )
                )
                        .then();
    }
}
