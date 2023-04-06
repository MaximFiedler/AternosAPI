package maximde.aternosapi;

public class AternosServer {
    private String ip;
    private String title;
    private String owner;
    private String version;
    private String discordInvite;
    private long serverID;
    private minecraftEdition edition = minecraftEdition.Unknown;
    private String descrition;


    public enum minecraftEdition {
        Java(),
        Bedrock(),
        BedrockAndJava(),
        Unknown()
    }


    public String getIp() {
        return ip;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDiscordInvite() {
        return discordInvite;
    }

    public void setDiscordInvite(String discordInvite) {
        this.discordInvite = discordInvite;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public minecraftEdition getEdition() {
        return edition;
    }

    public void setEdition(minecraftEdition edition) {
        this.edition = edition;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public void printInformation() {
        if (ip == null || ip.isEmpty()) {
            p("Server is empty!");
            return;
        }
        String ip = handleNull(getIp());
        String title = handleNull(getTitle());
        String owner = handleNull(getOwner());
        String version = handleNull(getVersion());
        String discordInvite = handleNull(getDiscordInvite());
        String serverID = handleNull(getServerID()+"");
        String edition = handleNull(getEdition().name());
        String descrition = handleNull(getDescrition());

        p("------ATERNOS SERVER "+serverID+"------");
        p("IP: " + ip);
        p("TITLE: " + title);
        p("OWNER: " + owner);
        p("VERSION: " + version);
        p("DISCORD: " + discordInvite);
        p("EDITION: " + edition);
        p("DESCRIPTION: " + descrition);
        p("------ATERNOS SERVER "+serverID+"------");
    }

    private void p(String message) {
        System.out.println(message);
    }

    private String handleNull(String input) {
        if (input == null || input.trim().equals("")) {
            return "Unknown";
        }
        return input;
    }

}
