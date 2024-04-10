package com.taskswift.main.model;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
public class Message {

    private JSONObject messageBody;

    private String to;

}
