package vvitsp.model.util;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

import vvitsp.model.bc.appModule.CommonAMImpl;

public class AMUtil {

    private static String amDef = "vvitsp.model.bc.appModule.CommonAM";
    private static String config = "CommonAMLocal";

    public AMUtil() {
        super();
    }

    public static CommonAMImpl getCommonAM() {
        return (CommonAMImpl)Configuration.createRootApplicationModule(amDef,
                                                                       config);
    }

    public static void releaseAM(ApplicationModule am) {
        if (am != null)
            Configuration.releaseRootApplicationModule(am, false);

    }
}
