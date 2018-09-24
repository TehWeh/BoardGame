package msg.meta;

import msg.ClientMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class RegisterRequest extends ClientMessage {

    private String name;

    @Override
    public void handle() {

    }

    @Override
    public void writeAdditionalExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readAdditionalExternal(ObjectInput in) throws IOException {

    }
}
