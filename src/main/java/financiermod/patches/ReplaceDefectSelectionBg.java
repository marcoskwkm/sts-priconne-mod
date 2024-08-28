// Source code is decompiled from a .class file using FernFlower decompiler.
package financiermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import financiermod.FinancierMod;

@SpirePatch(
   clz = CharacterOption.class,
   method = "updateHitbox"
)
public class ReplaceDefectSelectionBg {
   public ReplaceDefectSelectionBg() {
   }

   @SpireInsertPatch(
      rloc = 40,
      localvars = {"c"}
   )
   public static void ReplaceImage(CharacterOption __instance, AbstractPlayer c) {
      if (c instanceof Defect) {
         CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg = ImageMaster.loadImage(FinancierMod.imagePath("kokkoro/kokkoroBg.png"));
      }

   }
}
