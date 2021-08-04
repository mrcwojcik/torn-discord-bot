package pl.mrwojcik.tornbot.config;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.TextChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.mrwojcik.tornbot.listeners.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BotConfiguration {

    private final String botKey;

    public BotConfiguration(@Value("${botKey}") String discordKey) {
        this.botKey = discordKey;
    }

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        GatewayDiscordClient client = DiscordClientBuilder.create(botKey)
                .build()
                .login()
                .block();

        if(client == null) {
            System.out.println("Discord Client fails to start.");
            return null;
        }

        return client;
    }

    @Bean
    public ApplicationRunner startup(GatewayDiscordClient client, List<EventListener<? extends Event>> eventListeners) {
        return args -> {
            Flux<ReadyEvent> readyEvent = client.getEventDispatcher().on(ReadyEvent.class).doOnNext(
                    event -> {
                        User self = event.getSelf();
                        System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                    }
            );
            Flux<Void> listeners = Flux.fromIterable(eventListeners)
                    .flatMap(listener -> client.getEventDispatcher().on(listener.getEventType())
                            .flatMap(listener::execute)
                            .onErrorResume(listener::handleError));
            Mono.when(readyEvent, listeners, runCron(client)).block();
        };
    }

    private Flux<?> runCron(GatewayDiscordClient client) {
        return Flux.interval(Duration.ofDays(1))
                .flatMap(tick -> {
                    ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
                    if (zonedDateTime.getDayOfWeek() == DayOfWeek.WEDNESDAY){
                        return client.getChannelById(Snowflake.of("681333001811918898"))
                                .cast(TextChannel.class)
                                .flatMap(textChannel -> textChannel.createMessage("Chain takes place every two weeks on Saturday. We start at 20:30 TCT. It's mandatory chain. Check on faction page or ask mentors and councils if this week is chain day."))
                                .then();
                    }

                    return Flux.empty();
                });
    }
}
