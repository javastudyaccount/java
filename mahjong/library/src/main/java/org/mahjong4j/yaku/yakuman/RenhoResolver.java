package org.mahjong4j.yaku.yakuman;

import org.mahjong4j.GeneralSituation;
import org.mahjong4j.PersonalSituation;

/**
 * @author yu1ro
 */
public class RenhoResolver implements YakumanResolver {
    private final GeneralSituation generalSituation;
    private final PersonalSituation personalSituation;

    public RenhoResolver(GeneralSituation generalSituation, PersonalSituation personalSituation) {
        this.generalSituation = generalSituation;
        this.personalSituation = personalSituation;
    }

    @Override
    public Yakuman getYakuman() {
        return Yakuman.RENHO;
    }

    @Override
    public boolean isMatch() {
        // avoid NullPointerException
        if (generalSituation == null || personalSituation == null) return false;
        return generalSituation.isFirstRound() && !personalSituation.isTsumo() && !personalSituation.isParent();
    }
}
