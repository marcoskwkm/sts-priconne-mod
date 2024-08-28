// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import java.lang.reflect.Method;
import financiermod.ui.SkinSelectScreen;

@SpirePatch(
   clz = TheSilent.class,
   method = "<ctor>",
   paramtypez = {String.class}
)
public class ReplaceTheSilentAnimation {
   public static float animationScale = 2.0F;

   public ReplaceTheSilentAnimation() {
   }

   public static void Postfix(TheSilent __instance, String playerName) {
      try {
         Method initializeClass = AbstractPlayer.class.getDeclaredMethod("initializeClass", String.class, String.class, String.class, String.class, CharSelectInfo.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, EnergyManager.class);
         initializeClass.setAccessible(true);
         SkinSelectScreen.Skin skin = SkinSelectScreen.getSkin();
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
