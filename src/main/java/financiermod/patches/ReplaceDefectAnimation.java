// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import java.lang.reflect.Method;
import financiermod.ui.SkinSelectScreen.Skin;

@SpirePatch(
clz = Defect.class,
method = "<ctor>",
paramtypez = {String.class}
)
public class ReplaceDefectAnimation {
    public static float animationScale = 2.0F;
    
    public ReplaceDefectAnimation() {
    }
    
    public static void Postfix(Defect __instance, String playerName) {
        Skin skin = AnimationUtils.getSkin(AbstractPlayer.PlayerClass.DEFECT);
        if (skin == null) return;
        try {
            Method initializeClass = AbstractPlayer.class.getDeclaredMethod("initializeClass", String.class, String.class, String.class, String.class, CharSelectInfo.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, EnergyManager.class);
            initializeClass.setAccessible(true);
            initializeClass.invoke(__instance, null, skin.shoulder, skin.shoulder, null, __instance.getLoadout(), 0.0F, 0.0F, 200.0F, 220.0F, new EnergyManager(3));
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, Float.TYPE);
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(__instance, skin.charPath + ".atlas", skin.charPath + ".json", animationScale);
            AnimationState.TrackEntry e = __instance.state.setAnimation(0, "Idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            e.setTimeScale(1.2F);
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        
    }
}
