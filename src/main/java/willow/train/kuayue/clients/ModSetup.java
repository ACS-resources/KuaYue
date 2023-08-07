package willow.train.kuayue.clients;

import willow.train.kuayue.init.*;

public class ModSetup {

    public static void register(){

        KYTrackMaterials.register();
        KYBogeyStyles.register();
        KYItems.register();
        KYBogeySizes.register();
        KYCreateBlock.register();
        KYCreateEntities.register();
        KYTags.register();
    }
}
