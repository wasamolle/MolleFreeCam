package mollefreecam;

public final class Constants
{
    private Constants()
    {
        // nothing to do here...
    }

    public static final String COLOR_BLACK = "&0";
    public static final String COLOR_DARK_BLUE = "&1";
    public static final String COLOR_DARK_GREEN = "&2";
    public static final String COLOR_DARK_AQUA = "&3";
    public static final String COLOR_DARK_RED = "&4";
    public static final String COLOR_DARK_PURPLE = "&5";
    public static final String COLOR_GOLD = "&6";
    public static final String COLOR_GRAY = "&7";
    public static final String COLOR_DARK_GRAY = "&8";
    public static final String COLOR_BLUE = "&9";
    public static final String COLOR_GREEN = "&a";
    public static final String COLOR_AQUA = "&b";
    public static final String COLOR_RED = "&c";
    public static final String COLOR_LIGHT_PURPLE = "&d";
    public static final String COLOR_YELLOW = "&e";
    public static final String COLOR_WHITE = "&f";

    public static final String FORMAT_MAGIC = "&k";
    public static final String FORMAT_BOLD = "&l";
    public static final String FORMAT_STRIKE = "&m";
    public static final String FORMAT_UNDERLINE = "&n";
    public static final String FORMAT_ITALIC = "&o";
    public static final String FORMAT_RESET = "&r";

    public static final String CONSOLE_PREFIX = COLOR_DARK_PURPLE + "[MolleFreecam]" + COLOR_WHITE + " ";

    public static final String VERSION = "1.0";
    public static final String SOURCE = FORMAT_UNDERLINE + "https://github.com/wasamolle/MolleFreeCam" + FORMAT_RESET;
    public static final String COMMAND = "float";

    public static final String MSG_VERSION = "Loaded version is " + VERSION + ". Check " + SOURCE + COLOR_YELLOW + " for updates.";

    public static final String MSG_NO_PERMISSION = "You do not have the permission to use this command.";
    public static final String MSG_ON_COOLDOWN = "You have to wait %time% seconds before you can use this command again.";
    public static final String MSG_IN_FREECAM = "You are not allowed to use commands while in freecam mode.";
    public static final String MSG_ENTITY_DAMAGED = "Freecam mode was cancelled because your entity got damaged.";
    public static final String MSG_FREECAM_ENTER = "You entered freecam mode.";
    public static final String MSG_FREECAM_EXIT = "You left freecam mode.";

    public static final String TITLE_PERIOD = "[Freecam] Remaining: %time%s";
    public static final String TITLE_COOLDOWN = "[Freecam] Cooldown: %time%s";
    public static final String TITLE_DISTANCE = COLOR_RED + "[Freecam] Warning! Maximum distance reached.";

    public static final Float SOUND_VOLUME = 100.0f;
    public static final Float SOUND_PITCH = 2.0f;

    public static final String SOUND_FREECAM_ENTER = "entity.player.levelup";
    public static final String SOUND_FREECAM_EXIT = "entity.player.small_fall";
}