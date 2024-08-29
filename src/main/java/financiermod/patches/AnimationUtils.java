package financiermod.patches;

import java.lang.reflect.Method;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import financiermod.ui.SkinSelectScreen;
import financiermod.ui.SkinSelectScreen.Skin;

public class AnimationUtils {
    public static Skin getSkin(AbstractPlayer.PlayerClass player) {
        SaveFile saveFile = getSaveFile(player);
        if (saveFile != null) {
            return loadSkinFromSaveFile(saveFile);
        } else if (!SkinSelectScreen.Inst.disabled) {
            return SkinSelectScreen.getSkin();
        }
        return null;
    }
    
    private static SaveFile getSaveFile(AbstractPlayer.PlayerClass player) {
        // Super hacky way of getting a save file without making it throw when it is not available
        try {
            String fileName = SaveAndContinue.getPlayerSavePath(player);            
            Method method = SaveAndContinue.class.getDeclaredMethod("loadSaveFile", String.class);
            method.setAccessible(true);
            SaveFile saveFile = (SaveFile)method.invoke(SaveAndContinue.class, fileName);
            return saveFile;
        } catch (Exception e) {
            return null;
        }
    }
    
    private static Skin loadSkinFromSaveFile(SaveFile saveFile) {
        // Manually get the skin from the save data because hook provided by BaseMod is run after the animation is replaced
        ModSaves.HashMapOfJsonElement modSaves = ModSaves.modSaves.get(saveFile);
        JsonElement saveDataJson = modSaves.get(SaveState.SAVE_ID);
        SaveState.SaveData saveData = new Gson().fromJson(saveDataJson, new SaveState().savedType());
        if (saveData != null && !saveData.disabled) {
            SkinSelectScreen.Inst.characterIndex = saveData.characterIndex;
            SkinSelectScreen.Inst.skinIndex = saveData.skinIndex;
            SkinSelectScreen.Inst.disabled = saveData.disabled;
            SkinSelectScreen.Inst.refresh();
            return SkinSelectScreen.getSkin(saveData.characterIndex, saveData.skinIndex);
        }
        return null;
    }
}
