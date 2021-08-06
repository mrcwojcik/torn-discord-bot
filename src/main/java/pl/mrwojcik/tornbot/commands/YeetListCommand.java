package pl.mrwojcik.tornbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import pl.mrwojcik.tornbot.entity.Member;
import pl.mrwojcik.tornbot.utils.RequestToApi;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class YeetListCommand implements Command {

    private final RequestToApi requestToApi;

    public YeetListCommand(RequestToApi requestToApi) {
        this.requestToApi = requestToApi;
    }

    @Override
    public String getName() {
        return "yeet";
    }

    @Override
    public Mono<Void> execute(List<String> args, MessageCreateEvent event) {
        List<Member> membersToYeet = requestToApi.getYeetList();
        discord4j.core.object.entity.Member member = event.getMember().orElse(null);

        if (membersToYeet.isEmpty()){
            return event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("No one to yeet. Only Turd_knocker and PhishFood as always. "))
                    .then();
        }

        StringBuilder yeetList = new StringBuilder();
        for (Member m : membersToYeet){
            yeetList.append(" " + m.getUsername());
        }

        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage("Yeet list based on number of inactive days: " + yeetList))
                .then();
    }

}
