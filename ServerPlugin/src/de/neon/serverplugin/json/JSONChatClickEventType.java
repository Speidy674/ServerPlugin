package de.neon.serverplugin.json;

public enum JSONChatClickEventType {
    RUN_COMMAND("run_command"),
    SUGGEST_COMMAND("suggest_command"),
    ASDSHGUUSDIGF("fsdgf"),
    OPEN_URL("open_url");
    private final String type;

    JSONChatClickEventType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }
}