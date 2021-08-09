package pl.mrwojcik.tornbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.mrwojcik.tornbot.entity.Event;
import pl.mrwojcik.tornbot.entity.Faction;
import pl.mrwojcik.tornbot.entity.Member;

import java.util.Arrays;
import java.util.List;

@Component
public class RequestToApi {

    private final ReadJsonToString readJsonToString;
    private final ObjectMapper objectMapper;
    private final String apiUrl;

    public RequestToApi(ReadJsonToString readJsonToString, ObjectMapper objectMapper, @Value("${apiUrl}") String apiUrl) {
        this.readJsonToString = readJsonToString;
        this.objectMapper = objectMapper;
        this.apiUrl = apiUrl;
    }

    public List<Event> getEventsList() {
        try {
            String eventList = readJsonToString.readJsonFromUrl(apiUrl + "/api/events");
            List<Event> events = Arrays.asList(objectMapper.readValue(eventList, Event[].class));
            return events;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Event getClosestEvent(){
        try {
            String event = readJsonToString.readJsonFromUrl(apiUrl + "api/event/closest");
            Event event1 = objectMapper.readValue(event, Event.class);
            return event1;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Member> getYeetList(){
        try {
            String yeetMemberListJson = readJsonToString.readJsonFromUrl(apiUrl + "/api/yeet");
            List<Member> yeetList = Arrays.asList(objectMapper.readValue(yeetMemberListJson, Member[].class));
            return yeetList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Faction getFactionInfo(){
        try {
            String factionJson = readJsonToString.readJsonFromUrl(apiUrl + "/api/faction");
            Faction faction = objectMapper.readValue(factionJson, Faction.class);
            return faction;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
