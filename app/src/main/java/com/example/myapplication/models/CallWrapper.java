package com.example.myapplication.models;

import com.cometchat.pro.core.Call;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class CallWrapper implements IMessage {
    Call call;

    public CallWrapper(Call call) {
        this.call = call;
    }

    @Override
    public String getId() {
        return call.getMuid();
    }

    @Override
    public String getText() {
        return call.getSender().getName() + " call " + call.getDeliveredToMeAt();
    }

    @Override
    public IUser getUser() {
        return new UserWrapper( call.getSender());
    }

    @Override
    public Date getCreatedAt() {
        return new Date(call.getSentAt() * 1000);
    }
}
