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
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.AbstractCreature;

import financiermod.ui.SkinSelectScreen;

public class AnimationPatch {
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "damage"
    )
    public static class DamagePatch {
        public DamagePatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, DamageInfo info) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return;
            }

            if (info.owner != null && info.type != DamageType.THORNS && info.output - __instance.currentBlock > 0) {
                __instance.state.setAnimation(0, "Hit", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }            
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "evokeOrb"
    )
    public static class EvokeOrbPatch {
        public EvokeOrbPatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return;
            }
            
            __instance.state.setAnimation(0, "Skill2", false);
            __instance.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "heal"
    )
    public static class HealPatch {
        public HealPatch() {
        }
    
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return SpireReturn.Continue();
            }

            if (SkinSelectScreen.getCharacter().name == SkinSelectScreen.CharacterNames.pecorine) {
                __instance.state.setAnimation(0, "Skill1", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
                return SpireReturn.Continue();
            } else {
                return SpireReturn.Continue();
            }
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
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return SpireReturn.Continue();
            }
            
            __instance.state.setAnimation(0, "Die", false);
            return SpireReturn.Return();
        }
    }
    
    @SpirePatch(
    clz = AbstractPlayer.class,
    method = "useCard"
    )
    public static class UseCardPatch {
        public UseCardPatch() {
        }
        
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, AbstractCard c) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return;
            }

            if (c.type == CardType.POWER) {
                __instance.state.setAnimation(0, "Joy_short", false);
                __instance.state.addAnimation(0, "Joy_short_return", false, 0.0F);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            } else if (c.type == CardType.ATTACK) {
                if (c.damage <= 20 && c.cost < 2 && c.cardID != "Die Die Die" && c.cardID != "Die Die Die+" && c.cardID != "Grand Finale" && c.cardID != "Grand Finale+" && c.cardID != "Finisher" && c.cardID != "Finisher+") {
                    __instance.state.setAnimation(0, "Attack", false);
                    __instance.state.addAnimation(0, "Idle", true, 0.0F);
                } else if (SkinSelectScreen.getCharacter().name == SkinSelectScreen.CharacterNames.pecorine) {
                    __instance.state.setAnimation(0, "Skill2", false);
                    __instance.state.addAnimation(0, "Idle", true, 0.0F);
                } else {
                    __instance.state.setAnimation(0, "Attack_skipQuest", false);
                    __instance.state.addAnimation(0, "Idle", true, 0.0F);
                }
            } else if (SkinSelectScreen.getCharacter().name == SkinSelectScreen.CharacterNames.kyaryl && c.type == CardType.SKILL && (c.cardID == "Acrobatics" || c.cardID == "Acrobatics+" || c.cardID == "Survivor" || c.cardID == "Survivor+" || c.cardID == "Calculated Gamble" || c.cardID == "Calculated Gamble+" || c.cardID == "Catalyst" || c.cardID == "Catalyst+")) {
                __instance.state.setAnimation(0, "Skill1", false);
                __instance.state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
    }
    
    @SpirePatch(
    clz = AbstractCreature.class,
    method = "useFastAttackAnimation"
    )
    public static class UseFastAttackAnimationPatch {
        public UseFastAttackAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCreature __instance) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return SpireReturn.Continue();
            }
            
            __instance.state.setAnimation(0, "Attack", false);
            __instance.state.addAnimation(0, "Idle", true, 0.0F);
            return SpireReturn.Return();
        }
    }
    
    @SpirePatch(
    clz = AbstractCreature.class,
    method = "useSlowAttackAnimation"
    )
    public static class UseSlowAttackAnimationPatch {
        public UseSlowAttackAnimationPatch() {
        }
        
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractCreature __instance) {
            if (!AnimationPatch.shouldReplaceAnimation(__instance)) {
                return SpireReturn.Continue();
            }
            
            __instance.state.setAnimation(0, "Attack_skipQuest", false);
            __instance.state.addAnimation(0, "Idle", true, 0.0F);
            return SpireReturn.Return();
        }
    }
    
    private static boolean shouldReplaceAnimation(AbstractCreature __instance) {
        boolean isPlayer = __instance instanceof Ironclad || __instance instanceof TheSilent || __instance instanceof Defect || __instance instanceof Watcher;
        return isPlayer && !SkinSelectScreen.Inst.disabled;
    }
    
    public AnimationPatch() {}
}