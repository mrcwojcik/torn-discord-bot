package pl.mrwojcik.tornbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import org.springframework.stereotype.Service;
import pl.mrwojcik.tornbot.entity.Faction;
import pl.mrwojcik.tornbot.utils.RequestToApi;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Service
public class FactionCommand implements Command{

    private final RequestToApi requestToApi;

    public FactionCommand(RequestToApi requestToApi) {
        this.requestToApi = requestToApi;
    }

    @Override
    public String getName() {
        return "faction";
    }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        Faction faction = requestToApi.getFactionInfo();
        Consumer<EmbedCreateSpec> embed = embedCreateSpec -> {
            embedCreateSpec.setTitle(faction.getName());
            embedCreateSpec.setColor(Color.BLUE);
            embedCreateSpec.setDescription("Faction rp: " + faction.getRespect() + "\n" + "Age: " + faction.getAge());
        };
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(messageSpec ->
                        messageSpec.setEmbed(embed.andThen(embedCreateSpec -> {}))

                ))
                .then();
    }
}
