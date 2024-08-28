// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import financiermod.ui.SkinSelectScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import basemod.ReflectionHacks;

public class SkinSelectPatch {
    @SpirePatch(
    clz = CharacterSelectScreen.class,
    method = "render"
    )
    public static class SkinSelectPatch$RenderButtonPatch {
        public SkinSelectPatch$RenderButtonPatch() {
        }
        
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SkinSelectPatch.shouldDisplaySkinSelect()) {
                SkinSelectScreen.Inst.render(sb);
            }
        }
    }
    
    @SpirePatch(
    clz = CharacterSelectScreen.class,
    method = "update"
    )
    public static class SkinSelectPatch$UpdateButtonPatch {
        public SkinSelectPatch$UpdateButtonPatch() {
        }
        
        public static void Prefix(CharacterSelectScreen _inst) {
            if (SkinSelectPatch.shouldDisplaySkinSelect()) {
                SkinSelectScreen.Inst.update();
            }
        }
    }

    public static boolean shouldDisplaySkinSelect() {
        return ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected");
    }
    
    public SkinSelectPatch() {
    }
}
