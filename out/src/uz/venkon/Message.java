package uz.venkon;

/*
***
* Class for converting messages to json
***
*/
public class Message {

    public String plugin;
    public String name;
    public String [] arguments;

    public Message(String _plugin, String _name) {
        this.plugin = _plugin;
        this.name   = _name;
    }

    public Message(String _plugin, String _name, String _arg1) {
        this.plugin = _plugin;
        this.name   = _name;
        this.arguments = new String[1];
        this.arguments[0] = _arg1;
    }

    public Message(String _plugin, String _name, String _arg1, String _arg2) {
        this.plugin = _plugin;
        this.name   = _name;
        this.arguments = new String[2];
        this.arguments[0] = _arg1;
        this.arguments[1] = _arg2;
    }

    public Message(String _plugin, String _name, String _arg1, String _arg2, String _arg3) {
        this.plugin = _plugin;
        this.name   = _name;
        this.arguments = new String[3];
        this.arguments[0] = _arg1;
        this.arguments[1] = _arg2;
        this.arguments[2] = _arg3;
    }

    public Message(String _plugin, String _name, String _arg1, String _arg2, String _arg3, String _arg4) {
        this.plugin = _plugin;
        this.name   = _name;
        this.arguments = new String[4];
        this.arguments[0] = _arg1;
        this.arguments[1] = _arg2;
        this.arguments[2] = _arg3;
        this.arguments[3] = _arg4;
    }

    public Message(String _name, String _arg1, String _arg2, String _arg3, String _arg4, String _arg5, String _arg6) {
        this.name   = _name;
        this.arguments = new String[6];
        this.arguments[0] = _arg1;
        this.arguments[1] = _arg2;
        this.arguments[2] = _arg3;
        this.arguments[3] = _arg4;
        this.arguments[4] = _arg5;
        this.arguments[5] = _arg6;
    }

}
