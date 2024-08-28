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
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DefectPatch {
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "damage"
    )
    public static class DamagePatch {
        public DamagePatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, DamageInfo info) {
            if (__instance instanceof Defect && info.owner != null && info.type != DamageType.THORNS && info.output - __instance.currentBlock > 0) {
                __instance.state.setAnimation(0, "Hit", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }
            
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "evokeOrb"
    )
    public static class DefectPatch$evokeOrbPatch {
        public DefectPatch$evokeOrbPatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance) {
            if (__instance instanceof Defect) {
                __instance.state.setAnimation(0, "Skill2", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }
            
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "playDeathAnimation"
    )
    public static class DefectPatch$PlayDeathAnimationPatch {
        public DefectPatch$PlayDeathAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
            if (__instance instanceof Defect) {
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
    public static class DefectPatch$useCardPatch {
        public DefectPatch$useCardPatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, AbstractCard c) {
            if (__instance instanceof Defect && c.type == CardType.POWER) {
                __instance.state.setAnimation(0, "Joy_short", false);
                __instance.state.addAnimation(0, "Joy_short_return", false, 0.0F);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }
            
        }
    }
    
    @SpirePatch(
    clz = AbstractCreature.class,
    method = "useFastAttackAnimation"
    )
    public static class DefectPatch$UseFastAttackAnimationPatch {
        public DefectPatch$UseFastAttackAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCreature __instance) {
            if (__instance instanceof Defect) {
                __instance.state.setAnimation(0, "Attack", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }
    
    @SpirePatch(
    clz = AbstractCreature.class,
    method = "useSlowAttackAnimation"
    )
    public static class DefectPatch$UseSlowAttackAnimationPatch {
        public DefectPatch$UseSlowAttackAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCreature __instance) {
            if (__instance instanceof Defect) {
                __instance.state.setAnimation(0, "Attack_skipQuest", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }
    
    public DefectPatch() {
    }
}
