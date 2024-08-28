// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import basemod.ReflectionHacks;
import financiermod.FinancierMod;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

@SpirePatch(
   clz = CharacterSelectScreen.class,
   method = "initialize"
)
public class ReplaceDefectSelectionBtn {
   public ReplaceDefectSelectionBtn() {
   }

   public static void Postfix(CharacterSelectScreen __instance) {
      CharacterOption defect = (CharacterOption)__instance.options.get(2);
      ReflectionHacks.setPrivate(defect, CharacterOption.class, "buttonImg", ImageMaster.loadImage(FinancierMod.imagePath("kokkoro/button.png")));
   }
}
