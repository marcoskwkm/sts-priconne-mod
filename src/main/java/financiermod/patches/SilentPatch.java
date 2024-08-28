// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.TheSilent;

public class SilentPatch {
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "damage"
    )
    public static class damagePatch {
        public damagePatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance, DamageInfo info) {
            if (__instance instanceof TheSilent && info.owner != null && info.type != DamageType.THORNS && info.output - __instance.currentBlock > 0) {
                __instance.state.setAnimation(0, "Hit", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }
            
            return SpireReturn.Continue();
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "playDeathAnimation"
    )
    public static class PlayDeathAnimationPatch {
        public PlayDeathAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
            if (__instance instanceof TheSilent) {
                __instance.state.setAnimation(0, "Die", false);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "useCard"
    )
    public static class useCardPatch {
        public useCardPatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, AbstractCard c) {
            if (__instance instanceof TheSilent) {
                if (c.type == CardType.POWER) {
                    __instance.state.setAnimation(0, "Joy_short", false);
                    __instance.state.addAnimation(0, "Joy_short_return", false, 0.0F);
                    __instance.state.addAnimation(0, "Idle", true, 0.0F);
                } else if (c.type == CardType.ATTACK) {
                    if (c.damage <= 20 && c.cost < 2 && c.cardID != "Die Die Die" && c.cardID != "Die Die Die+" && c.cardID != "Grand Finale" && c.cardID != "Grand Finale+" && c.cardID != "Finisher" && c.cardID != "Finisher+") {
                        __instance.state.setAnimation(0, "Attack", false);
                        __instance.state.addAnimation(0, "Idle", true, 0.0F);
                    } else {
                        __instance.state.setAnimation(0, "Attack_skipQuest", false);
                        __instance.state.addAnimation(0, "Idle", true, 0.0F);
                    }
                }/* else if (c.type == CardType.SKILL && (c.cardID == "Acrobatics" || c.cardID == "Acrobatics+" || c.cardID == "Survivor" || c.cardID == "Survivor+" || c.cardID == "Calculated Gamble" || c.cardID == "Calculated Gamble+" || c.cardID == "Catalyst" || c.cardID == "Catalyst+")) {
                    __instance.state.setAnimation(0, "Skill1", false);
                    __instance.state.addAnimation(0, "Idle", true, 0.0F);
                }*/
            }
            
        }
    }
    
    
    public SilentPatch() {
    }
}
