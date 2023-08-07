package willow.train.kuayue;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue DISABLE_DATAFIXER;

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        setupGeneralCategory(SERVER_BUILDER);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGeneralCategory(ForgeConfigSpec.Builder builder) {
        DISABLE_DATAFIXER = builder.comment("Disable kuayue datafixers. Do not enable this config if you world contains pre-Create 0.5.1 ky_standard_bogeys, because then they will be destroyed").define("disableDatafixer", false);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }
}
