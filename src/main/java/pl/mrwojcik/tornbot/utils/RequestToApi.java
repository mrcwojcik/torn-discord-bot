package pl.mrwojcik.tornbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mrwojcik.tornbot.entity.Event;

import java.util.Arrays;
import java.util.List;

@Component
public class RequestToApi {

    private final ReadJsonToString readJsonToString;
    private final ObjectMapper objectMapper;

    public RequestToApi(ReadJsonToString readJsonToString, ObjectMapper objectMapper) {
        this.readJsonToString = readJsonToString;
        this.objectMapper = objectMapper;
    }

    public List<Event> getEventsList() {
        try {
            String eventList = readJsonToString.readJsonFromUrl("http://localhost:8081/api/events");
            List<Event> events = Arrays.asList(objectMapper.readValue(eventList, Event[].class));
            return events;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Event getClosestEvent(){
        try {
            String event = readJsonToString.readJsonFromUrl("http://localhost:8081/api/event/closest");
            Event event1 = objectMapper.readValue(event, Event.class);
            return event1;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
